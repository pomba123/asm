package org.example.verifiers;

import org.example.model.ASMElement;
import org.example.model.Method;
import org.example.model.Parameter;
import org.example.model.Rule;

import java.io.IOException;
import java.util.*;

public class MethodExactParamListConventionVerifier implements ConventionVerifier{
    private List<String> paramTypes = new ArrayList<String>();
    @Override
    public void init(Rule rule) {
        List<Parameter> parameters = rule.getParameters();
        for(Parameter param : parameters){
            paramTypes.add(param.getValue());
        }
        Collections.sort(paramTypes);
    }

    @Override
    public boolean verifyConvention(ASMElement element) throws IOException {
        String[] params =((Method) element).getParameters();
        Arrays.sort(((Method) element).getParameters());
        List<String> methodParams = List.of(params);
        return methodParams.equals(paramTypes);


    }
}
