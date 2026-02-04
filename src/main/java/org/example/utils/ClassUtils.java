package org.example.utils;

public class ClassUtils {

    public static String resolveClassName(String name){

        String classNameWithNoSlashes = name.replaceAll("/",".");
        int lastSlash = classNameWithNoSlashes.lastIndexOf('/');
        if( lastSlash == -1)
            return classNameWithNoSlashes;
        else
            return classNameWithNoSlashes.substring(lastSlash + 1);

    }

    public static String resolvePackageName(String name){
        String packageNameWithNoSlashes = name.replaceAll("/",".");
        int lastSlash = packageNameWithNoSlashes.lastIndexOf('/');
        if (lastSlash == -1)
            return packageNameWithNoSlashes;
        else
            return packageNameWithNoSlashes.substring(0, lastSlash).replace('/', '.');
    }
}
