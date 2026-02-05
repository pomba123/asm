package org.example.visitors;

import org.example.utils.ASMElementUtils;
import org.example.utils.ObjectUtils;
import org.example.verifiers.ConventionVerifier;
import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.ASM9;
import org.example.model.*;

import java.lang.Class;

public class ConventionFieldVisitor extends FieldVisitor  {

    private final Convention convention;

    private String signature;
    private  String fieldName;

    private  String fieldDescriptor;
    private  Object fieldValue;
    private int access;
    private org.example.model.Class declaringClass;
    private boolean fieldAnnotationAlreadyPresent;

    public ConventionFieldVisitor(
            FieldVisitor fv,
            Convention convention,
            int access,
            String fieldName,
            String fieldDescriptor,
            Object fieldValue, org.example.model.Class declaringClass) {

        super(ASM9, fv);
        this.access = access;
        this.convention = convention;
        this.fieldName = fieldName;
        this.fieldDescriptor = fieldDescriptor;
        this.fieldValue = fieldValue;
        this.declaringClass= declaringClass;
    }

    /* -------------------------------------------------------
     * Detect existing FIELD annotations
     * ------------------------------------------------------- */
    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {

        if (convention.getConventionScope() == ConventionScope.FIELD) {
            String targetDescriptor =
                    ASMElementUtils.toDescriptor(convention.getAnnotation().getName());

            if (descriptor.equals(targetDescriptor)) {
                fieldAnnotationAlreadyPresent = true;
            }
        }

        return super.visitAnnotation(descriptor, visible);
    }

    /* -------------------------------------------------------
     * Inject FIELD annotation at visitEnd
     * ------------------------------------------------------- */
    @Override
    public void visitEnd() {

        if (convention.getConventionScope() == ConventionScope.FIELD
                && !fieldAnnotationAlreadyPresent) {

            boolean insert = verifyConvention();

            if (insert) {
                addAnnotation();
            }
        }

        super.visitEnd();
    }


    private boolean verifyConvention() {
        try {
            Field field = new Field(access,
                    fieldName,
                    fieldDescriptor,
                    signature,
                    fieldValue,
                    declaringClass
            );

            for (Rule rule : convention.getRules()) {
                ConventionVerifier verifier =
                        (ConventionVerifier) Class
                                .forName(rule.getImplementation())
                                .getDeclaredConstructor()
                                .newInstance();

                verifier.init(rule.getParameters());

                if (!verifier.verifyConvention(field)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Field convention verification failed", e);
        }
    }


    private void addAnnotation() {
        String descriptor =
                ASMElementUtils.toDescriptor(convention.getAnnotation().getName());

        AnnotationVisitor av = fv.visitAnnotation(descriptor, true);

        for (Parameter param : convention.getAnnotation().getParameters()) {
            Object value =
                    ObjectUtils.resolveValue(param.getValue(), param.getType());
            av.visit(param.getName(), value);
        }

        av.visitEnd();
    }
}
