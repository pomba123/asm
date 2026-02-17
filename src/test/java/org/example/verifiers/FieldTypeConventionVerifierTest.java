package org.example.verifiers;

import org.example.model.Field;
import org.example.model.Parameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FieldTypeConventionVerifierTest {
    private FieldTypeConventionVerifier verifier;

    @BeforeEach
    void setUp() {
        verifier = new FieldTypeConventionVerifier();

        Parameter param = new Parameter();
        param.setName("hasCode");
        param.setValue("false");
        param.setType("boolean");

        verifier.init(List.of(param));
    }

    @Test
    void shouldReturnTrueWhenFieldOfType() throws Exception {
        Field field = new Field();
        field.setType("boolean");


        boolean result = verifier.verifyConvention(field);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenFieldNotOfType() throws Exception {
        Field field = new Field();
        field.setType("int");


        boolean result = verifier.verifyConvention(field);

        assertFalse(result);
    }
}
