package org.example;

import org.example.utils.Convention;
import org.example.verifiers.ConventionVerifier;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.ASM9;

public class ConventionVisitor extends ClassVisitor {

    private Convention convention;
    private Class visitedClass;

    public ConventionVisitor(ClassVisitor classVisitor,Convention convention,Class clazz) {
        super(ASM9, classVisitor);
        this.convention = convention;
        this.visitedClass = clazz;
    }



    @Override
    public void visitEnd(){
        boolean insert = false;

        if(convention.getConventionScope() == ConventionScope.CLASS){
            try {
                ConventionVerifier verifier = (ConventionVerifier) Class.forName(convention.getImplementation()).getDeclaredConstructor().newInstance();;
                insert = verifier.verifyConvention(visitedClass,convention.getRules());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (insert) {
            AnnotationVisitor av = cv.visitAnnotation(convention.getAnnotation().getName(), true);
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
                Field field = visitedClass.getDeclaredField(name);
                ConventionVerifier verifier = (ConventionVerifier) Class.forName(convention.getImplementation()).getDeclaredConstructor().newInstance();;
                insert = verifier.verifyConvention(field,convention.getRules());

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
                Method method = visitedClass.getDeclaredMethod(name);
                ConventionVerifier verifier = (ConventionVerifier) Class.forName(convention.getImplementation()).getDeclaredConstructor().newInstance();;
                insert = verifier.verifyConvention(method,convention.getRules());

            } catch (Exception e) {

            }
        }
        if (insert) {
            return new MethodVisitor(ASM9, mv) {
                @Override
                public void visitCode() {
                    AnnotationVisitor av = visitAnnotation(convention.getAnnotation().getName(), true);
                    av.visitEnd();
                    super.visitCode();
                }
            };
        }
        return mv;
    }
}
