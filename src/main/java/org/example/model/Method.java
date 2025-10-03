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





}
