package org.example.verifiers;

import org.example.model.Rule;
import org.example.utils.ObjectUtils;

import java.io.IOException;
import java.util.List;

public class RegularExpressionConventionVerifier implements ConventionVerifier{

    private String regex;
    private String upperCaseRegex;

    @Override
    public boolean verifyConvention(Object element, List<Rule> rules) throws IOException {
        Rule regexRule = rules.get(0);
        regex = regexRule.getParameters().get(0).getValue();
        upperCaseRegex = regex;
        String firstLetStr = upperCaseRegex.substring(0, 1);
        String remLetStr = upperCaseRegex.substring(1);
        firstLetStr = firstLetStr.toUpperCase();
        upperCaseRegex = firstLetStr + remLetStr;
        String elementName = ObjectUtils.getName(element);
        if(elementName.matches(".*"+ regex +".*") || elementName.matches(".*"+ upperCaseRegex +".*")) {
            return true;
        }
        return false;
    }
}
