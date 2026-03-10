package org.example.model;

import edu.emory.mathcs.backport.java.util.Arrays;
import org.objectweb.asm.Type;
import java.lang.Class;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Method  extends ASMElement{
    private String[] parameters;
    private String name;
    private List<String> exceptions;
    private String returnType;
    private org.example.model.Class declaringClass;
    public Method(String name, String descriptor,String[] exceptions,org.example.model.Class declaringClass)  {

        Type returnType = Type.getReturnType(descriptor);

        Type[] parametersTypes = Type.getArgumentTypes(descriptor);

        this.name = name;
        this.returnType = returnType.getClassName();

        this.parameters = new String[parametersTypes.length];
        if(exceptions!=null){
            this.exceptions = new ArrayList<String>();
            this.exceptions.addAll(Arrays.asList(exceptions));
        }

        Type[] argTypes = Type.getArgumentTypes(descriptor);
        for(int i=0;i<argTypes.length;i++){
            System.out.println(argTypes[i]);
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

    public List<String> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<String> exceptions) {
        this.exceptions = exceptions;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
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
