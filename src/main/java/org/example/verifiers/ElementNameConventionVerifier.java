package org.example.verifiers;

import org.example.model.ASMElement;
import org.example.model.Parameter;
import org.example.model.Rule;
import org.example.utils.ASMElementUtils;

import java.io.IOException;
import java.util.List;

public class ElementNameConventionVerifier implements ConventionVerifier{
    private String name;
    @Override
    public void init(Rule rule) {
        List<Parameter> parameters = rule.getParameters();
        name = parameters.get(0).getValue();
    }

    @Override
    public boolean verifyConvention(ASMElement element) throws IOException {
        String elementName = ASMElementUtils.getName(element);
        return elementName.equals(name);
    }
}
