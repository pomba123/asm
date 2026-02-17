package org.example.verifiers;

import org.example.model.Class;
import org.example.model.Field;
import org.example.model.Method;
import org.example.model.Parameter;
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
        verifier.init(List.of(paramMethod));
        boolean result = verifier.verifyConvention(method);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenMethodHasNotRegex() throws Exception {
        Method method = new Method();
        method.setName("setUser");
        verifier.init(List.of(paramMethod));
        boolean result = verifier.verifyConvention(method);

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenFieldHasRegex() throws Exception {
        Field field = new Field();
        field.setName("proessedNumberId");

        verifier.init(List.of(paramField));
        boolean result = verifier.verifyConvention(field);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenFieldDoNotHasRegex() throws Exception {
        Field field = new Field();
        field.setName("productRepository");
        verifier.init(List.of(paramField));
        boolean result = verifier.verifyConvention(field);

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenClassHasRegex() throws Exception {
        Class clazz = new Class();
        clazz.setName("UserProcessingService");
        verifier.init(List.of(paramClass));
        boolean result = verifier.verifyConvention(clazz);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenClassDoNotHasRegex() throws Exception {
        Class clazz = new Class();
        clazz.setName("ProductController");
        verifier.init(List.of(paramClass));
        boolean result = verifier.verifyConvention(clazz);

        assertFalse(result);
    }
}
