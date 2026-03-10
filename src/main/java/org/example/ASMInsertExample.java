package org.example;

import org.example.model.Convention;
import org.example.utils.ConventionLoader;
import org.example.visitors.ConventionClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * ASM transformer entrypoint.
 */
public class ASMInsertExample {

    /**
     * Run instrumentation on all classes under classesDir.
     *
     * @param projectDir The Maven project base directory (where pom.xml is).
     * @param classesDir The compiled output directory (usually target/classes).
     */
    public static void run(Path projectDir, Path classesDir) throws Exception {
        // Load conventions.json
        Path conventionsPath = projectDir.resolve("conventions.json");
        List<Convention> conventions = ConventionLoader.loadConventions(conventionsPath.toString());

        File outputFile = new File("asm_output.txt");
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {

            writer.println("Instrumented classes:");

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

                            writer.println(className);

                            // Read class bytes
                            byte[] classBytes = Files.readAllBytes(classFile);
                            ClassReader classReader = new ClassReader(classBytes);
                            byte[] modifiedClass = classBytes;

                            // Apply all conventions
                            for (Convention convention : conventions) {
                                ClassWriter classWriter = new ClassWriter(classReader,
                                        0);
                                ConventionClassVisitor visitor = new ConventionClassVisitor(classWriter, convention);
                                classReader.accept(visitor, 0);
                                modifiedClass = classWriter.toByteArray();
                                classReader = new ClassReader(modifiedClass);
                            }

                            // Overwrite class file
                            Files.write(classFile, modifiedClass);

                        } catch (Exception e) {
                            throw new RuntimeException("Failed to process " + classFile, e);
                        }
                    });
        }
    }
}