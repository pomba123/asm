package org.example.verifiers;

import org.example.model.*;
import org.example.model.Class;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PrefixConventionVerifierTest {
    private PrefixConventionVerifier verifier;


        Parameter paramMethod = new Parameter();
        Parameter paramField = new Parameter();
        Parameter paramClass = new Parameter();

        @BeforeEach
        void setUp(){
            paramMethod.setName("prefix");
            paramMethod.setValue("get");

            paramField.setName("prefix");
            paramField.setValue("user");

            paramClass.setName("prefix");
            paramClass.setValue("Payment");
            verifier = new PrefixConventionVerifier();

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
        field.setName("userRepository");

        Rule rule = new Rule();
        rule.setParameters(List.of(paramField));
        verifier.init(rule);
        boolean result = verifier.verifyConvention(field);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenFieldPrefixDoNotMatches() throws Exception {
        Field field = new Field();
        field.setName("productRepository");
        Rule rule = new Rule();
        rule.setParameters(List.of(paramField));
        verifier.init(rule);
        boolean result = verifier.verifyConvention(field);

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenClassPrefixMatches() throws Exception {
        Class clazz = new Class();
        clazz.setName("PaymentController");
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
        verifier = new PrefixConventionVerifier();
        Rule rule = new Rule();
        rule.setParameters(List.of(paramClass));
        verifier.init(rule);
        boolean result = verifier.verifyConvention(clazz);

        assertFalse(result);
    }

}
