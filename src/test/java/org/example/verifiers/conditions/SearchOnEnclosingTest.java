package org.example.verifiers.conditions;

import org.example.model.Class;
import org.example.model.Method;
import org.example.model.Parameter;
import org.example.model.Rule;
import org.example.verifiers.ClassTypeConventionVerifier;
import org.example.verifiers.ElementNameConventionVerifier;
import org.example.verifiers.SuffixConventionVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SearchOnEnclosingTest {

    private SuffixConventionVerifier verifier;
    private ElementNameConventionVerifier elementNameConventionVerifier;
    @BeforeEach
    void setUp() {
        elementNameConventionVerifier = new ElementNameConventionVerifier();
        verifier = new SuffixConventionVerifier();
        Rule rule = new Rule();

        Parameter param = new Parameter();
        param.setName("suffix");
        param.setValue("MetaModel");
        rule.setParameters(List.of(param));
        rule.setSearchOnEnclosingElement(true);
        verifier.init(rule);

    }

    @Test
    void shouldReturnTrueWhenSearchingEnclosingElementTest() throws Exception {
        Class clazz = new Class();
        clazz.setName("classTypeMetaModel");
        Method method = new Method();
        method.setName("remove");
        method.setDeclaringClass(clazz);
        Rule rule = new Rule();

        Parameter param = new Parameter();
        param.setName("suffix");
        param.setValue("MetaModel");
        rule.setParameters(List.of(param));
        rule.setSearchOnEnclosingElement(true);
        verifier.init(rule);
        boolean result = verifier.verifyConvention(method);

        assertTrue(result);
    }
    @Test
    void shouldReturnFlaseWhenNotSearchingOnEnclosingTest() throws Exception {
        Class clazz = new Class();
        clazz.setName("classTypeMetaModel");
        Method method = new Method();
        method.setName("remove");
        Rule rule1 = new Rule();
        Parameter param1 = new Parameter();
        param1.setName("suffix");
        param1.setValue("MetaModel");
        rule1.setParameters(List.of(param1));
        rule1.setSearchOnEnclosingElement(false);
        verifier.init(rule1);
        boolean result = verifier.verifyConvention(method);
        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenEnclosingElementMatches() throws Exception {
        Class clazz = new Class();
        clazz.setName("Node");
        Method method = new Method();
        method.setName("remove");
        method.setDeclaringClass(clazz);
        Rule rule1 = new Rule();
        Parameter param1 = new Parameter();
        param1.setName("name");
        param1.setValue("Node");
        rule1.setParameters(List.of(param1));
        rule1.setSearchOnEnclosingElement(true);
        elementNameConventionVerifier.init(rule1);
        boolean result = elementNameConventionVerifier.verifyConvention(method);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenEnclosingElementMatches() throws Exception {

        Class clazz = new Class();
        clazz.setName("Node");
        Method method = new Method();
        method.setName("remove");
        method.setDeclaringClass(clazz);
        Rule rule1 = new Rule();
        Parameter param1 = new Parameter();
        param1.setName("name");
        param1.setValue("Node");
        rule1.setParameters(List.of(param1));
        rule1.setSearchOnEnclosingElement(false);
        elementNameConventionVerifier.init(rule1);
        boolean result = elementNameConventionVerifier.verifyConvention(method);
        assertFalse(result);
    }


}
