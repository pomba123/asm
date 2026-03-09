package org.example.verifiers.conditions;

import org.example.model.Class;
import org.example.model.Parameter;
import org.example.model.Rule;
import org.example.verifiers.ClassTypeConventionVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CanBeSubTypeTest {
    private ClassTypeConventionVerifier verifier;

    @BeforeEach
    void setUp() {
        verifier = new ClassTypeConventionVerifier();
        Rule rule = new Rule();

        Parameter param = new Parameter();
        param.setName("type");
        param.setValue("org.example.model.ASMElement");
        rule.setParameters(List.of(param));
        rule.setSubtype(true);
        verifier.init(rule);




    }

    @Test
    void shouldReturnTrueWhenSubTypeIsAllowedTest() throws Exception {
        Class clazz = new Class();
        clazz.setName("org.example.model.Field");
        clazz.setSuperName("org.example.model.ASMElement");

        boolean result = verifier.verifyConvention(clazz);

        assertTrue(result);
    }
    @Test
    void shouldReturnFlaseWhenSubTypeIsAllowedTest() throws Exception {
        Class clazz = new Class();
        clazz.setName("org.example.model.Field");
        clazz.setSuperName("org.example.model.ASMElement");
        Rule rule1 = new Rule();
        Parameter param1 = new Parameter();
        param1.setName("type");
        param1.setValue("org.example.model.ASMElement");
        rule1.setParameters(List.of(param1));
        rule1.setSubtype(false);
        verifier.init(rule1);
        boolean result = verifier.verifyConvention(clazz);
        assertFalse(result);
    }

}
