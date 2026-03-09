package org.example.verifiers;

import org.example.model.*;
import org.example.model.Class;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegularExpressionConventionVerifierTest {
    private RegularExpressionConventionVerifier verifier;


    Parameter paramMethod = new Parameter();
    Parameter paramField = new Parameter();
    Parameter paramClass = new Parameter();

    @BeforeEach
    void setUp(){
        paramMethod.setName("regex");
        paramMethod.setValue(".Data.");

        paramField.setName("regex");
        paramField.setValue(".Number.");

        paramClass.setName("regex");
        paramClass.setValue(".Processing.");
        verifier = new RegularExpressionConventionVerifier();

    }

    @Test
    void shouldReturnTrueWhenMethodHasRegex() throws Exception {
        Method method = new Method();
        method.setName("getDataFrom");
        Rule rule = new Rule();
        rule.setParameters(List.of(paramMethod));
        verifier.init(rule);
        boolean result = verifier.verifyConvention(method);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenMethodHasNotRegex() throws Exception {
        Method method = new Method();
        method.setName("setUser");
        Rule rule = new Rule();
        rule.setParameters(List.of(paramMethod));
        verifier.init(rule);
        boolean result = verifier.verifyConvention(method);

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenFieldHasRegex() throws Exception {
        Field field = new Field();
        field.setName("proessedNumberId");

        Rule rule = new Rule();
        rule.setParameters(List.of(paramField));
        verifier.init(rule);
        boolean result = verifier.verifyConvention(field);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenFieldDoNotHasRegex() throws Exception {
        Field field = new Field();
        field.setName("productRepository");
        Rule rule = new Rule();
        rule.setParameters(List.of(paramField));
        verifier.init(rule);
        boolean result = verifier.verifyConvention(field);

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenClassHasRegex() throws Exception {
        Class clazz = new Class();
        clazz.setName("UserProcessingService");
        Rule rule = new Rule();
        rule.setParameters(List.of(paramClass));
        verifier.init(rule);
        boolean result = verifier.verifyConvention(clazz);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenClassDoNotHasRegex() throws Exception {
        Class clazz = new Class();
        clazz.setName("ProductController");
        Rule rule = new Rule();
        rule.setParameters(List.of(paramClass));
        verifier.init(rule);
        boolean result = verifier.verifyConvention(clazz);

        assertFalse(result);
    }
}
