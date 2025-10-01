package org.example;

import org.example.model.Convention;
import org.example.utils.ConventionLoader;
import org.example.visitors.ConventionVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ASMInsertExample {

    public static void main(String[] args) throws Exception {
        // args[0] = project base dir (pom.xml)
        // args[1] = compiled classes directory (target/classes)
        String projectDir = args.length > 0 ? args[0] : System.getProperty("user.dir");
        String classesDir = args.length > 1 ? args[1] : Path.of(projectDir, "target", "classes").toString();

        // Load conventions.json
        Path conventionsPath = Path.of(projectDir, "conventions.json");
        List<Convention> conventions = ConventionLoader.loadConventions(conventionsPath.toString());
        System.out.println(classesDir+" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        // Walk all .class files in target/classes
        URL classesUrl = Path.of(classesDir).toUri().toURL();
        try (URLClassLoader classLoader = new URLClassLoader(new URL[]{classesUrl}, ASMInsertExample.class.getClassLoader())) {

            // Walk all .class files
            Files.walk(Path.of(classesDir))
                    .filter(p -> p.toString().endsWith(".class"))
                    .forEach(classFile -> {
                        try {
                            // Convert path to class name
                            String className = Path.of(classesDir).relativize(classFile)
                                    .toString()
                                    .replace(System.getProperty("file.separator"), ".")
                                    .replaceAll("\\.class$", "");

                            // Load the class using URLClassLoader
                            Class<?> clazz = Class.forName(className, false, classLoader);

                            // Read class bytes
                            byte[] classBytes = Files.readAllBytes(classFile);
                            ClassReader classReader = new ClassReader(classBytes);
                            byte[] modifiedClass = classBytes;

                            for (Convention convention : conventions) {
                                ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
                                ConventionVisitor visitor = new ConventionVisitor(classWriter, convention, clazz);
                                classReader.accept(visitor, 0);
                                modifiedClass = classWriter.toByteArray();
                                classReader = new ClassReader(modifiedClass);
                            }

                            // Overwrite class file
                            Files.write(classFile, modifiedClass);
                            System.out.println("Instrumented: " + className);

                        } catch (Exception e) {
                            throw new RuntimeException("Failed to process " + classFile, e);
                        }
                    });
        }
    }
}
