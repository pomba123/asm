package org.example.visitors;

import org.example.model.*;
import org.example.verifiers.ConventionVerifier;
import org.objectweb.asm.*;

import java.lang.reflect.Field;

import static org.objectweb.asm.Opcodes.ASM9;

public class ConventionVisitor extends ClassVisitor {

    private final Convention convention;
    private final Class<?> visitedClass;
    private final ClassLoader userClassLoader;

    public ConventionVisitor(ClassVisitor classVisitor, Convention convention, Class<?> clazz, ClassLoader userClassLoader) {
        super(ASM9, classVisitor);
        this.convention = convention;
        this.visitedClass = clazz;
        this.userClassLoader = userClassLoader;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        boolean insert = false;
        iClass clazz = new iClass(version,access,name,signature,superName,interfaces);
        if (convention.getConventionScope() == ConventionScope.CLASS) {
            try {
                Class<?> verifierClass = userClassLoader.loadClass(convention.getImplementation());
                ConventionVerifier verifier =
                        (ConventionVerifier) verifierClass.getDeclaredConstructor().newInstance();
                insert = verifier.verifyConvention(clazz, convention.getRules());
            } catch (Exception e) {
                throw new RuntimeException("Failed to load verifier " + convention.getImplementation(), e);
            }
        }
        if (insert) {
            AnnotationVisitor av = cv.visitAnnotation(convention.getAnnotation().getName(), true);
            av.visitEnd();
        }
    }

    @Override
    public void visitEnd() {

        super.visitEnd();
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        FieldVisitor fv = super.visitField(access, name, descriptor, signature, value);
        boolean insert = false;
        if (convention.getConventionScope() == ConventionScope.FIELD) {
            try {
                iField field = new iField(access,descriptor,name,signature,value);
                Class<?> verifierClass = userClassLoader.loadClass(convention.getImplementation());
                ConventionVerifier verifier =
                        (ConventionVerifier) verifierClass.getDeclaredConstructor().newInstance();
                insert = verifier.verifyConvention(field, convention.getRules());
            } catch (Exception e) {
                throw new RuntimeException("Failed to verify field " + name, e);
            }
        }
        if (insert) {
            return new FieldVisitor(ASM9, fv) {
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

        if (convention.getConventionScope() == ConventionScope.METHOD) {
            try {
                Method method = new Method(name, descriptor, exceptions, visitedClass);
                Class<?> verifierClass = userClassLoader.loadClass(convention.getImplementation());
                ConventionVerifier verifier =
                        (ConventionVerifier) verifierClass.getDeclaredConstructor().newInstance();
                insert = verifier.verifyConvention(method, convention.getRules());
            } catch (Exception e) {
                throw new RuntimeException("Failed to verify method " + name, e);
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
