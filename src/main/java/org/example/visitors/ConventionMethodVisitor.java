package org.example.visitors;


import org.example.model.Class;
import org.objectweb.asm.*;
import org.example.model.*;
import org.example.utils.ASMElementUtils;
import org.example.utils.ObjectUtils;
import org.example.verifiers.ConventionVerifier;
import static org.objectweb.asm.Opcodes.ASM9;

public class ConventionMethodVisitor extends MethodVisitor {

    private final Convention convention;

    private final String methodName;
    private final String methodDescriptor;
    private final String[] exceptions;
    private Class declaringClass;
    private boolean isAnnotationAlreadyPresent;

    public ConventionMethodVisitor(
            MethodVisitor mv,
            Convention convention,
            String methodName,
            String methodDescriptor,
            String[] exceptions,
            Class declaringClass) {

        super(ASM9, mv);
        this.convention = convention;
        this.methodName = methodName;
        this.methodDescriptor = methodDescriptor;
        this.exceptions = exceptions;
        this.declaringClass = declaringClass;
    }


    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {

        if (convention.getConventionScope() == ConventionScope.METHOD) {
            String targetDescriptor =
                    ASMElementUtils.toDescriptor(convention.getAnnotation().getName());

            if (descriptor.equals(targetDescriptor)) {
                isAnnotationAlreadyPresent = true;
            }
        }

        return super.visitAnnotation(descriptor, visible);
    }


    @Override
    public void visitEnd() {

        if (convention.getConventionScope() == ConventionScope.METHOD
                && !isAnnotationAlreadyPresent) {

            boolean insert = verifyConvention();

            if (insert) {
                addAnnotation();
            }
        }

        super.visitEnd();
    }

    private boolean verifyConvention() {
        System.out.println(methodName);
        try {
            Method method = new Method(
                    methodName,
                    methodDescriptor,
                    exceptions,
                    declaringClass
            );
            boolean allRulesMustApply = convention.isAllRulesMustApply();

            for (Rule rule : convention.getRules()) {
                ConventionVerifier verifier =
                        (ConventionVerifier) java.lang.Class
                                .forName(rule.getImplementation())
                                .getDeclaredConstructor()
                                .newInstance();

                verifier.init(rule);

                if (!verifier.verifyConvention(method) && allRulesMustApply) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Method convention verification failed", e);
        }
    }

    private void addAnnotation() {
        String descriptor =
                ASMElementUtils.toDescriptor(convention.getAnnotation().getName());

        AnnotationVisitor av = mv.visitAnnotation(descriptor, true);

        for (Parameter param : convention.getAnnotation().getParameters()) {
            Object value =
                    ObjectUtils.resolveValue(param.getValue(), param.getType());
            av.visit(param.getName(), value);
        }

        av.visitEnd();
    }
}

