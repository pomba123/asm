package org.example.utils;

import org.example.model.Method;
import org.example.model.iClass;

import java.lang.reflect.Field;


public class ObjectUtils {

    public static String getName(Object object){
        if(object instanceof iClass)
            return ((iClass) object).getName();
        else if(object instanceof Method)
            return ((Method) object).getName();
        else
            return ((Field) object).getName();
    }

    public static Class getDeclaringClass(Object object){
            if(object instanceof Method){
                return ((Method) object).getDeclaringClass();
            }else if (object instanceof Field){
                return ((Field) object).getDeclaringClass();
            }else
                return ((Class) object).getDeclaringClass();
    }



}
