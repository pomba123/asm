package org.example.verifiers;

import org.example.model.Rule;
import org.example.utils.ObjectUtils;

import java.io.IOException;
import java.util.List;

public class ExactNameConventionVerifier implements ConventionVerifier{
    @Override
    public boolean verifyConvention(Object element, List<Rule> rules) throws IOException {
        Rule exactName = rules.get(0);
        String exactNameString = exactName.getParameters().get(0).getValue();
        String name = ObjectUtils.getName(element);
        return name.equals(exactNameString);
    }
}
