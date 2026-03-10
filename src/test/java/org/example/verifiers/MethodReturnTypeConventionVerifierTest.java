package org.example.verifiers;

import org.example.model.Field;
import org.example.model.Method;
import org.example.model.Parameter;
import org.example.model.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MethodReturnTypeConventionVerifierTest {
    private MethodReturnTypeConventionVerifier verifier;

    @BeforeEach
    void setUp() {
        verifier = new MethodReturnTypeConventionVerifier();
        Rule rule = new Rule();
        Parameter param = new Parameter();
        param.setName("returnType");
        param.setType("double");
        rule.setParameters(List.of(param));
        verifier.init(rule);
    }

    @Test
    void shouldReturnTrueWhenReturnTypeMatches() throws Exception {
        Method method = new Method();
        method.setReturnType("double");


        boolean result = verifier.verifyConvention(method);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenReturnTypeDoNotMatches() throws Exception {
        Method method = new Method();
        method.setReturnType("float");


        boolean result = verifier.verifyConvention(method);

        assertFalse(result);
    }
}
