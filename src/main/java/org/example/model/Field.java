package org.example.model;

import org.objectweb.asm.Type;

public class Field  extends ASMElement{
    private int access;
    private String name;
    private String descriptor;
    private String signature;
    private String type;
    private Object value;
    private Class declaringClazz;
    public Field(int access, String name, String descriptor, String signature, Object value,Class declaringClass){
        this.access = access;
        this.name = name;
        this.descriptor = descriptor;
        this.signature = signature;
        this.value = value;
        this.declaringClazz = declaringClass;
        this.type = Type.getType(descriptor).getClassName();

    }
    public Field(){}
    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Class getDeclaringClazz() {
        return declaringClazz;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDeclaringClazz(Class declaringClazz) {
        this.declaringClazz = declaringClazz;
    }
}
