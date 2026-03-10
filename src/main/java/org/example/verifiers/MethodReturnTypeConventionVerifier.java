package org.example.verifiers;

import org.example.model.ASMElement;
import org.example.model.Method;
import org.example.model.Parameter;
import org.example.model.Rule;
import org.example.utils.ObjectUtils;

import java.io.IOException;
import java.util.List;

public class MethodReturnTypeConventionVerifier implements ConventionVerifier{
    private String returnType;


    @Override
    public void init(Rule rule) {
        List<Parameter> parameters = rule.getParameters();
        returnType = parameters.get(0).getType();
    }

    @Override
    public boolean verifyConvention(ASMElement element) throws IOException {
        Method method = (Method) element;
        return returnType.equals(method.getReturnType());
    }
}
