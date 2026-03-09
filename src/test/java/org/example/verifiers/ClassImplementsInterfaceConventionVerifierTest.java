package org.example.verifiers;


import org.example.model.Class;

import org.example.model.Parameter;

import org.example.model.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ClassImplementsInterfaceConventionVerifierTest {

    private ClassImplementsInterfaceConventionVerifier verifier;

    @BeforeEach
    void setUp() {
        verifier = new ClassImplementsInterfaceConventionVerifier();

        Parameter param = new Parameter();
        param.setName("interface");
        param.setValue("java/io/Serializable");
        Rule rule = new Rule();
        rule.setParameters(List.of(param));
        verifier.init(rule);
    }

    @Test
    void shouldReturnTrueWhenInterfaceIsImplemented() throws Exception {
        Class clazz = new Class();
        clazz.setInterfaces(List.of("java/io/Serializable","java/lang/Runnable"));


        boolean result = verifier.verifyConvention(clazz);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenInterfaceIsNotImplemented() throws Exception {
        Class clazz = new Class();
        clazz.setInterfaces(List.of("java/lang/Runnable"));

        boolean result = verifier.verifyConvention(clazz);

        assertFalse(result);
    }

    @Test
    void shouldReturnFalseWhenNoInterfacesAreImplemented() throws Exception {
        Class clazz = new Class();
        clazz.setInterfaces(List.of());

        boolean result = verifier.verifyConvention(clazz);

        assertFalse(result);
    }
}

