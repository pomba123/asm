package org.example.model;

import org.example.utils.ClassUtils;

public class Class extends ASMElement {
    private int version;
    private int access;
    private String name;
    private String classPackage;
    private String signature;
    private String superName;
    private String[] interfaces;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    public String getClassPackage() {
        return classPackage;
    }

    public void setClassPackage(String classPackage) {
        this.classPackage = classPackage;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSuperName() {
        return superName;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }

    public String[] getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(String[] interfaces) {
        this.interfaces = interfaces;
    }

    public Class(int version, int access, String name, String signature, String superName, String[] interfaces){

        this.version = version;
        this.access = access;
        this.name= ClassUtils.resolveClassName(name);
        this.classPackage= ClassUtils.resolvePackageName(name);
        this.signature = signature;
        this.superName = superName;
        this.interfaces = interfaces;

    }
}
