package org.example.model;

import org.objectweb.asm.Type;

public class iField {
    int access;
    String name;
    String type;
    String signature;
    Object value;

    public iField(int access, String descriptor, String name, String signature, Object value) {
        this.access = access;
        this.type= Type.getType(descriptor).getClassName();
        this.name = name;
        this.signature = signature;
        this.value = value;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
