package org.example.model;

import org.objectweb.asm.Type;

public class Method {
    private String[] parameters;
    private String name;
    private String[] exceptions;
    private String returnType;
    private Class<?> declaringClass;
    public Method(String name, String descriptor,String[] exceptions,Class<?> declaringClass) throws ClassNotFoundException {
        Type returnType = Type.getReturnType(descriptor);
        Type[] parametersTypes = Type.getArgumentTypes(descriptor);
        this.name = name;
        this.declaringClass = declaringClass;
        this.returnType = returnType.getClassName();
        this.parameters = new String[parametersTypes.length];
        Type[] argTypes = Type.getArgumentTypes(descriptor);
        for(int i=0;i<argTypes.length;i++){
            this.parameters[i] = argTypes[i].getClassName();
            System.out.println(this.parameters[i]);

        }
        if(exceptions!=null){
            this.exceptions = new String[exceptions.length];
            for(int i=0;i<exceptions.length;i++){
                exceptions[i] = exceptions[i].replace("/",".");
                this.exceptions[i] = exceptions[i];
            }
        }


    }

    public Class<?> getDeclaringClass() {
        return declaringClass;
    }

    public void setDeclaringClass(Class<?> declaringClass) {
        this.declaringClass = declaringClass;
    }


    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getExceptions() {
        return exceptions;
    }

    public void setExceptions(String[] exceptions) {
        this.exceptions = exceptions;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }


    private Class<?> getClassFor(Type type) throws ClassNotFoundException {
        String className = type.getClassName().replace("/",".");

        switch (type.getSort()) {
            case Type.VOID:    return void.class;
            case Type.BOOLEAN: return boolean.class;
            case Type.CHAR:    return char.class;
            case Type.BYTE:    return byte.class;
            case Type.SHORT:   return short.class;
            case Type.INT:     return int.class;
            case Type.FLOAT:   return float.class;
            case Type.LONG:    return long.class;
            case Type.DOUBLE:  return double.class;
            case Type.ARRAY:    System.out.println(type.getDescriptor()+" is array");return Class.forName(type.getDescriptor().replace("/","."));
            case Type.OBJECT:
                ClassLoader appLoader = Thread.currentThread().getContextClassLoader();
                Class<?> clazz = Class.forName(className, true, appLoader);
                System.out.println(clazz.getCanonicalName()+" on new class loader");
                return Class.forName(className);
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }

    }


}
