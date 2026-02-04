package org.example.visitors;

import org.example.model.*;
import org.example.utils.ASMElementUtils;
import org.example.utils.ObjectUtils;
import org.example.verifiers.ConventionVerifier;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.example.model.Class;
import org.example.model.Field;


import static org.objectweb.asm.Opcodes.ASM9;

public class ConventionVisitor extends ClassVisitor {

    private Convention convention;
    private Class visitedClass;
    private String targetAnnotationDescriptor;
    private boolean annotationAlreadyPresent = false;
    public ConventionVisitor(ClassVisitor classVisitor, Convention convention) {
        super(ASM9, classVisitor);
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

        visitedClass = new Class(version,access,name,signature,superName,interfaces);
        this.targetAnnotationDescriptor =
                ASMElementUtils.toDescriptor(
                        convention.getAnnotation().getName()
                );
        super.visit(version, access, name, signature, superName, interfaces);
    }
    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (descriptor.equals(targetAnnotationDescriptor)) {
            annotationAlreadyPresent = true;
        }

        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public void visitEnd(){
        boolean insert = false;

        if(convention.getConventionScope() == ConventionScope.CLASS){
            try {
                for(Rule rule : convention.getRules()){
                    ConventionVerifier verifier = (ConventionVerifier) java.lang.Class.forName(rule.getImplementation()).getDeclaredConstructor().newInstance();;
                    verifier.init(rule.getParameters());
                    insert = verifier.verifyConvention(visitedClass);

                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (insert && !annotationAlreadyPresent) {
            String conventionName = convention.getAnnotation().getName();
            String descriptorToAnnotationName = ASMElementUtils.toDescriptor(conventionName);
            AnnotationVisitor av = cv.visitAnnotation(descriptorToAnnotationName, true);

            for (Parameter param : convention.getAnnotation().getParameters()) {
                Object typedValue = ObjectUtils.resolveValue(
                        param.getValue(),
                        param.getType()
                );

                av.visit(param.getName(), typedValue);
            }
            av.visitEnd();
        }
        super.visitEnd();
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        FieldVisitor fv = super.visitField(access, name, descriptor, signature, value);
        boolean insert = false;
        if(convention.getConventionScope() == ConventionScope.FIELD){
            try {
                Field field = new Field(access,name,descriptor,signature,value,visitedClass);
                for(Rule rule : convention.getRules()){
                    ConventionVerifier verifier = (ConventionVerifier) java.lang.Class.forName(rule.getImplementation()).getDeclaredConstructor().newInstance();;
                    verifier.init(rule.getParameters());
                    insert = verifier.verifyConvention(field);

                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (insert) {
            return new FieldVisitor(ASM9, fv) {
                @Override
                public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                    return super.visitAnnotation(convention.getAnnotation().getName(), true);
                }

                @Override
                public void visitEnd() {
                    AnnotationVisitor av = fv.visitAnnotation(convention.getAnnotation().getName(), true);
                    av.visitEnd();
                    super.visitEnd();
                }
            };
        }
        return fv;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        boolean insert = false;

        if(convention.getConventionScope() == ConventionScope.METHOD){
            try {
                Method method = new Method(name,descriptor,exceptions,visitedClass);

                for(Rule rule : convention.getRules()){

                    ConventionVerifier verifier = (ConventionVerifier) java.lang.Class.forName(rule.getImplementation()).getDeclaredConstructor().newInstance();;
                    verifier.init(rule.getParameters());
                    insert = verifier.verifyConvention(method);

                }

            } catch (Exception e) {

            }
        }
        if (insert) {
            return new MethodVisitor(ASM9, mv) {
                @Override
                public void visitCode() {
                    AnnotationVisitor av = visitAnnotation(convention.getAnnotation().getName(), true);
                    for (Parameter param : convention.getAnnotation().getParameters()) {
                        Object typedValue = ObjectUtils.resolveValue(
                                param.getValue(),
                                param.getType()
                        );

                        av.visit(param.getName(), typedValue);
                    }
                    av.visitEnd();
                    super.visitCode();
                }
            };
        }
        return mv;
    }
}
