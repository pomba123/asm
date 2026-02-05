package org.example;

import org.example.model.Convention;
import org.example.utils.ConventionLoader;
import org.example.visitors.ConventionClassVisitor;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ASMInsertExample {

    /**
     * Run instrumentation on all classes under classesDir.
     *
     * @param projectDir        The Maven project base directory (where pom.xml is).
     * @param classesDir        The compiled output directory (usually target/classes).
     * @param userClassLoader   A classloader that can see user classes and dependencies.
     */
    public static void run(Path projectDir, Path classesDir, ClassLoader userClassLoader) throws Exception {
        // Load conventions.json
        Path conventionsPath = projectDir.resolve("conventions.json");
        List<Convention> conventions = ConventionLoader.loadConventions(conventionsPath.toString());
        // Add project classes to the loader
        URL classesUrl = classesDir.toUri().toURL();
        try (URLClassLoader classLoader = new URLClassLoader(new URL[]{classesUrl}, userClassLoader)) {

            Files.walk(classesDir)
                    .filter(p -> p.toString().endsWith(".class"))
                    .forEach(classFile -> {
                        try {
                            // Convert path to fully-qualified class name
                            String className = classesDir
                                    .relativize(classFile)
                                    .toString()
                                    .replace(File.separatorChar, '.')
                                    .replaceAll("\\.class$", "");

                            // Load the class using provided classloader

                            // Read class bytes
                            byte[] classBytes = Files.readAllBytes(classFile);
                            ClassReader classReader = new ClassReader(classBytes);
                            byte[] modifiedClass = classBytes;

                            for (Convention convention : conventions) {
                                ClassWriter classWriter = new ClassWriter(classReader,
                                        ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
                                ConventionClassVisitor visitor = new ConventionClassVisitor(classWriter, convention);
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