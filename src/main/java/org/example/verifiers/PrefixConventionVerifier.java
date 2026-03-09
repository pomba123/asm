package org.example.verifiers;

import org.example.model.ASMElement;
import org.example.model.Parameter;
import org.example.utils.ASMElementUtils;
import org.example.utils.ObjectUtils;
import org.example.model.Rule;

import java.io.IOException;
import java.util.List;

public class PrefixConventionVerifier implements  ConventionVerifier{

    private String prefix;
    public void init(Rule rule){
        List<Parameter> parameters = rule.getParameters();
        prefix = parameters.get(0).getValue().toLowerCase();
    }
    @Override
    public boolean verifyConvention(ASMElement object) throws IOException {
        String objectName = ASMElementUtils.getName(object).toLowerCase();
        return objectName.toLowerCase().startsWith(prefix);
    }
}
