package org.example.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ObjectUtils {

    public static String getName(Object object){
        if(object instanceof Class)
            return ((Class<?>) object).getSimpleName();
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
