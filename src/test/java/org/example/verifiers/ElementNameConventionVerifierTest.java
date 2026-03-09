package org.example.verifiers;

import org.example.model.*;
import org.example.model.Class;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ElementNameConventionVerifierTest {
    Parameter paramMethod = new Parameter();
    Parameter paramField = new Parameter();
    Parameter paramClass = new Parameter();
    ElementNameConventionVerifier verifier;
    @BeforeEach
    void setUp(){
        paramMethod.setName("name");
        paramMethod.setValue("getUser");

        paramField.setName("name");
        paramField.setValue("user");

        paramClass.setName("name");
        paramClass.setValue("UserController");
        verifier = new ElementNameConventionVerifier();

    }


    @Test
    void shouldReturnTrueWhenMethodPrefixMatches() throws Exception {
        Method method = new Method();
        method.setName("getUser");
        Rule rule = new Rule();
        rule.setParameters(List.of(paramMethod));
        verifier.init(rule);
        boolean result = verifier.verifyConvention(method);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenMethodPrefixDoNotMatches() throws Exception {
        Method method = new Method();
        method.setName("setUser");
        Rule rule = new Rule();
        rule.setParameters(List.of(paramMethod));
        verifier.init(rule);
        boolean result = verifier.verifyConvention(method);

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenFieldPrefixMatches() throws Exception {
        Field field = new Field();
        field.setName("user");

        Rule rule = new Rule();
        rule.setParameters(List.of(paramField));
        verifier.init(rule);
        boolean result = verifier.verifyConvention(field);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenFieldPrefixDoNotMatches() throws Exception {
        Field field = new Field();
        field.setName("userRepository");
        Rule rule = new Rule();
        rule.setParameters(List.of(paramField));
        verifier.init(rule);
        boolean result = verifier.verifyConvention(field);

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenClassPrefixMatches() throws Exception {
        Class clazz = new Class();
        clazz.setName("UserController");
        Rule rule = new Rule();
        rule.setParameters(List.of(paramClass));
        verifier.init(rule);
        boolean result = verifier.verifyConvention(clazz);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenClassPrefixDoNotMatches() throws Exception {
        Class clazz = new Class();
        clazz.setName("ProductController");
        Rule rule = new Rule();
        rule.setParameters(List.of(paramClass));
        verifier.init(rule);
        boolean result = verifier.verifyConvention(clazz);

        assertFalse(result);
    }

}
