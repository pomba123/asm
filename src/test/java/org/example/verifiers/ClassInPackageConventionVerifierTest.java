package org.example.verifiers;

import org.example.model.Class;
import org.example.model.Parameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ClassInPackageConventionVerifierTest {
    private ClassInPackageConventionVerifier verifier;

    @BeforeEach
    void setUp() {
        verifier = new ClassInPackageConventionVerifier();

        Parameter param = new Parameter();
        param.setName("package");
        param.setValue("org.example.package");

        verifier.init(List.of(param));
    }

    @Test
    void shouldReturnTrueWhenInPackage() throws Exception {
        Class clazz = new Class();
        clazz.setClassPackage("org.example.package");


        boolean result = verifier.verifyConvention(clazz);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenNotInPackage() throws Exception {
        Class clazz = new Class();
        clazz.setClassPackage("org.example.annotations");

        boolean result = verifier.verifyConvention(clazz);

        assertFalse(result);
    }


}

