package org.example.utils;

import org.example.model.ASMElement;
import org.example.model.Class;
import org.example.model.Field;
import org.example.model.Method;

public class ASMElementUtils {

    public static String getName(ASMElement element){

        if(element instanceof Class) {
            return ((Class) element).getName();
        }
        else if(element instanceof Method)
            return ((Method) element).getName();
        return ((Field) element).getName();
    }

    public static String toDescriptor(String conventionName){
        return "L" + conventionName.replace('.', '/') + ";";
    }
}

