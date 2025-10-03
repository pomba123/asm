package org.example.verifiers;

import org.example.model.Method;
import org.example.model.Rule;

import java.io.IOException;
import java.util.List;

public class MethodReturnTypeConventionVerifier implements ConventionVerifier{
    @Override
    public boolean verifyConvention(Object element, List<Rule> rules) throws IOException {
        Rule methodReturnType = rules.get(0);
        String returnType = methodReturnType.getParameters().get(0).getValue();
        Method method = (Method) element;
        String returnTypeClass = method.getReturnType();
        return returnType.equals(returnTypeClass);
    }
}
