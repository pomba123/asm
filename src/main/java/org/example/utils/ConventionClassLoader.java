package org.example.utils;

import java.nio.file.Files;
import java.nio.file.Path;

public class ConventionClassLoader extends ClassLoader {
    private final Path dir;

    public ConventionClassLoader(Path dir) {
        this.dir = dir;
    }
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        // Avoid trying to reload java.* classes
        if (name.startsWith("java.")) {
            return super.loadClass(name, resolve);
        }

        try {
            return findClass(name);
        } catch (ClassNotFoundException e) {
            return super.loadClass(name, resolve);
        }
    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("on findClass ============ "+name);
        try {
            Path classFile = dir.resolve(name.replace('.', '/') + ".class");

            byte[] bytes = Files.readAllBytes(classFile);
            return defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
            throw new ClassNotFoundException("Could not load " + name, e);
        }
    }
}
