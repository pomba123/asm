package org.example.model;

import org.objectweb.asm.Type;

public class Method {
    private Class<?>[] parameters;
    private String name;
    private Class<?>[] exceptions;
    private Class<?> returnType;
    private Class<?> declaringClass;
    public Method(String name, String descriptor,String[] exceptions,Class<?> declaringClass) throws ClassNotFoundException {
        Type returnType = Type.getReturnType(descriptor);
        Type[] parametersTypes = Type.getArgumentTypes(descriptor);
        this.name = name;
        this.returnType = getClassFor(returnType);
        this.parameters = new Class<?>[parametersTypes.length];
        if(exceptions!=null){
            this.exceptions = new Class<?>[exceptions.length];
            for(int i=0;i<exceptions.length;i++){
                exceptions[i] = exceptions[i].replace("/",".");
                this.exceptions[i] = Class.forName("java.io.IOException");
            }
        }

        Type[] argTypes = Type.getArgumentTypes(descriptor);
        for(int i=0;i<argTypes.length;i++){
            Class<?> clazz = getClassFor(argTypes[i]);
            this.parameters[i] = clazz;

        }



        this.declaringClass = declaringClass;


    }

    public Class<?> getDeclaringClass() {
        return declaringClass;
    }

    public void setDeclaringClass(Class<?> declaringClass) {
        this.declaringClass = declaringClass;
    }


    public Class<?>[] getParameters() {
        return parameters;
    }

    public void setParameters(Class<?>[] parameters) {
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?>[] getExceptions() {
        return exceptions;
    }

    public void setExceptions(Class<?>[] exceptions) {
        this.exceptions = exceptions;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }


    private Class<?> getClassFor(Type type) throws ClassNotFoundException {

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
            case Type.ARRAY:
            case Type.OBJECT:
                return Class.forName(type.getClassName());
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }

    }


}
