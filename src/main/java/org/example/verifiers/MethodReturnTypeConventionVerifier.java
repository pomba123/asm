package org.example.verifiers;

import org.example.model.ASMElement;
import org.example.model.Method;
import org.example.model.Parameter;
import org.example.model.Rule;
import org.example.utils.ObjectUtils;

import java.io.IOException;
import java.util.List;

public class MethodReturnTypeConventionVerifier implements ConventionVerifier{
    private Class<?> returnType;


    @Override
    public void init(Rule rule) {
        List<Parameter> parameters = rule.getParameters();
        try {
            returnType = ObjectUtils.resolveClass(parameters.get(0).getType());


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ;

    }

    @Override
    public boolean verifyConvention(ASMElement element) throws IOException {
        Method method = (Method) element;
        return returnType==method.getReturnType();
    }
}
