package org.example;

import org.example.utils.Convention;
import org.example.utils.ConventionLoader;
import org.example.verifiers.ConventionVerifier;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class ASMInsertExample {

    public static void main(String args[]) throws Exception {
        //String path = args[0];
        List<Convention> conventions = ConventionLoader.loadConventions("/home/user/IdeaProjects/asmtest2/src/main/java/org/example/conventions.json");
//        Reflections reflections = new Reflections(new ConfigurationBuilder()
//                .setUrls(ClasspathHelper.forPackage("org.example.classes"))
//                .setScanners(new SubTypesScanner(false))
//        );
        Reflections reflections = new Reflections("org.example.classes", new SubTypesScanner(false));
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);

        for (Class<?> clazz : classes) {
            // Read the *original* bytecode once
            ClassReader classReader = new ClassReader(clazz.getCanonicalName());
            ClassWriter classWriter = null;
            byte[] modifiedClass = null;

            for (Convention convention : conventions) {
                classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
                ConventionVisitor visitor = new ConventionVisitor(classWriter, convention, clazz);
                classReader.accept(visitor, 0);
                modifiedClass = classWriter.toByteArray();

                // For the next convention, use the already-modified bytes
                classReader = new ClassReader(modifiedClass);
            }

            // After all conventions applied → write once
            String path = "target/" + clazz.getPackageName().replace(".", "/");
            Path dir = Paths.get(path);
            Files.createDirectories(dir);

            try (FileOutputStream fos = new FileOutputStream(path + "/" + clazz.getSimpleName() + ".class")) {
                fos.write(modifiedClass);
            }
        }
    }
}
