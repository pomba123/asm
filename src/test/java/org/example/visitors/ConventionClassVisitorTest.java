package org.example.visitors;

import annotations.AnnotationWithParametersForClass;
import classes.ClassWithConventions;
import org.example.model.Convention;
import org.example.utils.ConventionLoader;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.objectweb.asm.Opcodes.ASM9;

public class ConventionClassVisitorTest {
   private String path = "src/test/java/jsonFiles/classConventions.json".replace("/",File.separator);
  @Test
  void MustHaveAnnotationWithParametersOnClass() throws Exception {

      InputStream in = ClassWithConventions.class .getClassLoader()
              .getResourceAsStream(
                      "classes/ClassWithConventions.class".replace("/", File.separator));

      ClassReader reader = new ClassReader(in);
      ClassWriter writer =
              new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
      List<Convention> conventions = ConventionLoader.loadConventions(path);
      for(Convention convention : conventions){
          reader.accept(
                  new ConventionClassVisitor(writer, convention),
                  0
          );
          byte[] modifiedBytes = writer.toByteArray();
          assertClassHasAnnotation(
                  modifiedBytes,
                  Type.getDescriptor(AnnotationWithParametersForClass.class)
          );


      }
  }

    private void assertClassHasAnnotation(byte[] bytecode, String expectedDescriptor) {

        AtomicBoolean found = new AtomicBoolean(false);

        ClassReader reader = new ClassReader(bytecode);
        reader.accept(new ClassVisitor(ASM9) {

            @Override
            public AnnotationVisitor visitAnnotation(
                    String descriptor,
                    boolean visible) {

                if (descriptor.equals(expectedDescriptor)) {
                    found.set(true);
                }
                return super.visitAnnotation(descriptor, visible);
            }
        }, 0);

        assertTrue(found.get(),
                "Expected annotation not found: " + expectedDescriptor);
    }
}
