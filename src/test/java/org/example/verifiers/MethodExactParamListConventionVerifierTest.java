package org.example.verifiers;

import org.example.model.Method;
import org.example.model.Parameter;
import org.example.model.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MethodExactParamListConventionVerifierTest {
    private MethodExactParamListConventionVerifier verifier;

    @BeforeEach
    void setUp() {
        verifier = new MethodExactParamListConventionVerifier();
        List<Parameter> parameters = new ArrayList<>();
        Parameter param1 = new Parameter();
        param1.setName("param1");
        param1.setValue("double");
        parameters.add(param1);
        Parameter param2 = new Parameter();
        param2.setName("param2");
        param2.setValue("int");
        parameters.add(param2);
        Parameter param3 = new Parameter();
        param3.setName("param3");
        param3.setValue("String");
        parameters.add(param3);
        Rule rule =new Rule();
        rule.setParameters(parameters);
        verifier.init(rule);
    }

    @Test
    void shouldReturnTrueWhenListTypesMatches() throws Exception {
        Method method = new Method();
        String[] list = {"double","String","int"};

        method.setParameters(list);
        boolean result = verifier.verifyConvention(method);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenListTypesMatches() throws Exception {
        Method method = new Method();
        String[] list = {"String","int"};

        method.setParameters(list);
        boolean result = verifier.verifyConvention(method);

        assertFalse(result);
    }

}
