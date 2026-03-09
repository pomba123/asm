package org.example.model;

import org.objectweb.asm.Type;
import java.lang.Class;
import java.util.Collections;

public class Method  extends ASMElement{
    private String[] parameters;
    private String name;
    private Class<?>[] exceptions;
    private Class<?> returnType;
    private org.example.model.Class declaringClass;
    public Method(String name, String descriptor,String[] exceptions,org.example.model.Class declaringClass)  {
        Type returnType = Type.getReturnType(descriptor);
        Type[] parametersTypes = Type.getArgumentTypes(descriptor);
        this.name = name;
        this.returnType = getClassFor(returnType);
        this.parameters = new String[parametersTypes.length];
        if(exceptions!=null){
            this.exceptions = new Class<?>[exceptions.length];
            for(int i=0;i<exceptions.length;i++){
                exceptions[i] = exceptions[i].replace("/",".");
                try {
                    this.exceptions[i] = Class.forName("java.io.IOException");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        Type[] argTypes = Type.getArgumentTypes(descriptor);
        for(int i=0;i<argTypes.length;i++){

            this.parameters[i] = argTypes[i].getClassName();

        }



        this.declaringClass = declaringClass;


    }

    public Method() {

    }

    public org.example.model.Class getDeclaringClass() {
        return declaringClass;
    }

    public void setDeclaringClass(org.example.model.Class declaringClass) {
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


    private Class<?> getClassFor(Type type)  {

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
                try{
                    return Class.forName(type.getClassName());
                }catch (ClassNotFoundException ex){
                    ex.printStackTrace();
            }
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }

    }


}
