package org.example.verifiers.conditions;

import org.example.model.*;
import org.example.model.Class;
import org.example.verifiers.ClassTypeConventionVerifier;
import org.example.verifiers.ConventionVerifier;
import org.example.verifiers.ElementNameConventionVerifier;
import org.example.verifiers.SuffixConventionVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AllRulesMustApplyTest {

    private SuffixConventionVerifier suffixVerifier;
    private ElementNameConventionVerifier elementNameVerifier;
    Convention suffixAnElementName = new Convention();
    @BeforeEach
    void setUp() {

        suffixVerifier = new SuffixConventionVerifier();
        Rule suffix = new Rule();
        Parameter param = new Parameter();
        param.setName("suffix");
        param.setValue("MetaModel");
        suffix.setParameters(List.of(param));
        suffix.setSearchOnEnclosingElement(true);
        suffix.setImplementation("org.example.verifiers.SuffixConventionVerifier");

        suffixVerifier.init(suffix);

        elementNameVerifier = new ElementNameConventionVerifier();
        Rule elementName = new Rule();
        Parameter param1 = new Parameter();
        param1.setName("name");
        param1.setValue("<init>");
        elementName.setParameters(List.of(param1));
        elementName.setImplementation("org.example.verifiers.ElementNameConventionVerifier");
        elementNameVerifier.init(elementName);


        List<Rule> rules = new ArrayList<Rule>();
        rules.add(elementName);
        rules.add(suffix);
        suffixAnElementName.setRules(rules);
        suffixAnElementName.setAllRulesMustApply(true);

    }

    @Test
    void shouldReturnTrueWithAllConventionsTest() throws Exception {
        Class clazz = new org.example.model.Class();
        clazz.setName("ClassMetaModel");
        Method method = new Method();
        method.setName("<init>");
        method.setDeclaringClass(clazz);
        boolean allRulesMustApply = suffixAnElementName.isAllRulesMustApply();
        boolean result = true;
        for (Rule rule : suffixAnElementName.getRules()) {
            ConventionVerifier verifier =
                    (ConventionVerifier) java.lang.Class
                            .forName(rule.getImplementation())
                            .getDeclaredConstructor()
                            .newInstance();
            verifier.init(rule);
            if (!verifier.verifyConvention(method) && allRulesMustApply) {
                result=false;
            }
        }
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenNotAllConditionsMeetTest() throws Exception {
        Class clazz = new org.example.model.Class();
        clazz.setName("ClassMeta");
        Method method = new Method();
        method.setName("<init>");
        method.setDeclaringClass(clazz);
        boolean allRulesMustApply = suffixAnElementName.isAllRulesMustApply();
        boolean result = true;
        for (Rule rule : suffixAnElementName.getRules()) {
            ConventionVerifier verifier =
                    (ConventionVerifier) java.lang.Class
                            .forName(rule.getImplementation())
                            .getDeclaredConstructor()
                            .newInstance();
            verifier.init(rule);
            if (!verifier.verifyConvention(method) && allRulesMustApply) {
                result=false;
            }
        }
        assertFalse(result);
    }


}
