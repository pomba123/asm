package org.example;

import org.example.model.Convention;
import org.example.utils.ConventionLoader;
import org.example.visitors.ConventionVisitor;
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
        List<Convention> conventions = ConventionLoader.loadConventions("/home/user/Pictures/asm-main/src/main/java/org/example/conventions.json");
        Reflections reflections = new Reflections("org.example.classes", new SubTypesScanner(false));
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        for (Class<?> clazz : classes) {
            ClassReader classReader = new ClassReader(clazz.getCanonicalName());
            ClassWriter classWriter = null; byte[] modifiedClass = null;
            for (Convention convention : conventions) {
                classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
                ConventionVisitor visitor = new ConventionVisitor(classWriter, convention, clazz);
                classReader.accept(visitor, 0);
                modifiedClass = classWriter.toByteArray();
                classReader = new ClassReader(modifiedClass);
            }
            String path = "target/" + clazz.getPackageName().replace(".", "/");
            Path dir = Paths.get(path); Files.createDirectories(dir);
            try (FileOutputStream fos = new FileOutputStream(path + "/" + clazz.getSimpleName() + ".class")) {
                fos.write(modifiedClass);
            }
        }
    }
}