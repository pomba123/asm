package org.example.utils;

import org.example.model.Method;
import org.example.model.Field;


public class ObjectUtils {


    public static Class<?> resolveClass(String className) throws ClassNotFoundException {

        return switch (className) {
            case "boolean" -> boolean.class;
            case "byte" -> byte.class;
            case "short" -> short.class;
            case "int" -> int.class;
            case "long" -> long.class;
            case "float" -> float.class;
            case "double" -> double.class;
            case "char" -> char.class;
            case "void" -> void.class;
            default -> java.lang.Class.forName(className);
        };
    }

    public static Object resolveValue(String value, String type){

        return switch (type) {

            case "int" -> Integer.parseInt(value.toString());
            case "long" -> Long.parseLong(value.toString());
            case "boolean" -> Boolean.parseBoolean(value.toString());
            case "double" -> Double.parseDouble(value.toString());
            case "float" -> Float.parseFloat(value.toString());
            case "short" -> Short.parseShort(value.toString());
            case "byte" -> Byte.parseByte(value.toString());
            case "char" -> value.toString().charAt(0);

            case "String" -> value.toString();

            default -> throw new IllegalArgumentException("Unsupported type: " + type);
        };
    }
}
