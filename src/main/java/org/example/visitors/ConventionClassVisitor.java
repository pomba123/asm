package org.example.visitors;

import edu.emory.mathcs.backport.java.util.Arrays;
import org.example.model.*;
import org.example.utils.ASMElementUtils;
import org.example.utils.ObjectUtils;
import org.example.verifiers.ConventionVerifier;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.example.model.Class;


import static org.objectweb.asm.Opcodes.ASM9;

public class ConventionClassVisitor extends ClassVisitor {

    private final Convention convention;
    private Class visitedClass;
    private String className;
    private boolean classAnnotationAlreadyPresent;

    public ConventionClassVisitor(ClassVisitor cv, Convention convention) {
        super(ASM9, cv);
        this.convention = convention;
    }

    @Override
    public void visit(
            int version,
            int access,
            String name,
            String signature,
            String superName,
            String[] interfaces) {

        visitedClass = new Class(version,access,name,signature,superName, Arrays.asList(interfaces));
        super.visit(version, access, name, signature, superName, interfaces);
    }

    /* -------------------------------------------------------
     * Detect existing CLASS annotations
     * ------------------------------------------------------- */
    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {

        if (convention.getConventionScope() == ConventionScope.CLASS) {
            String targetDescriptor =
                    ASMElementUtils.toDescriptor(convention.getAnnotation().getName());

            if (descriptor.equals(targetDescriptor)) {
                classAnnotationAlreadyPresent = true;
            }
        }

        return super.visitAnnotation(descriptor, visible);
    }

    /* -------------------------------------------------------
     * Inject CLASS annotation at visitEnd
     * ------------------------------------------------------- */
    @Override
    public void visitEnd() {

        if (convention.getConventionScope() == ConventionScope.CLASS
                && !classAnnotationAlreadyPresent) {

            boolean insert = verifyConvention();

            if (insert) {
                addAnnotation();
            }
        }

        super.visitEnd();
    }

    private boolean verifyConvention() {

        try {
            boolean allRulesMustApply = convention.isAllRulesMustApply();
            for (Rule rule : convention.getRules()) {
                ConventionVerifier verifier =
                        (ConventionVerifier) java.lang.Class
                                .forName(rule.getImplementation())
                                .getDeclaredConstructor()
                                .newInstance();

                verifier.init(rule);
                if (!verifier.verifyConvention(visitedClass) && allRulesMustApply) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Class convention verification failed", e);
        }
    }

    private void addAnnotation() {
        String descriptor =
                ASMElementUtils.toDescriptor(convention.getAnnotation().getName());

        AnnotationVisitor av = cv.visitAnnotation(descriptor, true);

        for (Parameter param : convention.getAnnotation().getParameters()) {
            Object value =
                    ObjectUtils.resolveValue(param.getValue(), param.getType());
            av.visit(param.getName(), value);
        }

        av.visitEnd();
    }

    /* -------------------------------------------------------
     * Delegate methods to MethodVisitor
     * ------------------------------------------------------- */
    @Override
    public MethodVisitor visitMethod(
            int access,
            String name,
            String descriptor,
            String signature,
            String[] exceptions) {

        MethodVisitor mv =
                super.visitMethod(access, name, descriptor, signature, exceptions);

        return new ConventionMethodVisitor(
                mv,
                convention,
                name,
                descriptor,
                exceptions,
                visitedClass
        );
    }

    @Override
    public FieldVisitor visitField(
            int access,
            String name,
            String descriptor,
            String signature,
            Object value) {

        FieldVisitor fv =
                super.visitField(access, name, descriptor, signature, value);

        return new ConventionFieldVisitor(
                fv,
                convention,
                access,
                name,
                descriptor,
                value,
                visitedClass
        );
    }
}
