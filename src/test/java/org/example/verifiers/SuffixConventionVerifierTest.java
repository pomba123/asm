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

public class SuffixConventionVerifierTest {
    Parameter paramMethod = new Parameter();
    Parameter paramField = new Parameter();
    Parameter paramClass = new Parameter();
    SuffixConventionVerifier verifier;
    @BeforeEach
    void setUp(){
        paramMethod.setName("suffix");
        paramMethod.setValue("Email");

        paramField.setName("suffix");
        paramField.setValue("Id");

        paramClass.setName("suffix");
        paramClass.setValue("DAO");
        verifier = new SuffixConventionVerifier();

    }


    @Test
    void shouldReturnTrueWhenMethodSuffixMatches() throws Exception {
        Method method = new Method();
        method.setName("sendEmail");
        verifier.init(List.of(paramMethod));
        boolean result = verifier.verifyConvention(method);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenMethodSuffixDoNotMatches() throws Exception {
        Method method = new Method();
        method.setName("sendMessage");
        verifier.init(List.of(paramMethod));
        boolean result = verifier.verifyConvention(method);

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenFieldSuffixMatches() throws Exception {
        Field field = new Field();
        field.setName("userId");

        verifier.init(List.of(paramField));
        boolean result = verifier.verifyConvention(field);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenFieldSuffixDoNotMatches() throws Exception {
        Field field = new Field();
        field.setName("userRepository");
        verifier.init(List.of(paramField));
        boolean result = verifier.verifyConvention(field);

        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenClassSuffixMatches() throws Exception {
        Class clazz = new Class();
        clazz.setName("UserDAO");
        verifier.init(List.of(paramClass));
        boolean result = verifier.verifyConvention(clazz);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenClassSuffixDoNotMatches() throws Exception {
        Class clazz = new Class();
        clazz.setName("UserController");
        verifier.init(List.of(paramClass));
        boolean result = verifier.verifyConvention(clazz);

        assertFalse(result);
    }
}
