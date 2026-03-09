package org.example.verifiers;

import org.example.model.ASMElement;
import org.example.model.Parameter;
import org.example.model.Rule;
import org.example.utils.ASMElementUtils;

import java.io.IOException;
import java.util.List;

public class RegularExpressionConventionVerifier implements ConventionVerifier{
    private String regex;
    private String upperCaseRegex;
    @Override
    public void init(Rule rule) {
        List<Parameter> parameters = rule.getParameters();
        this.regex = parameters.get(0).getValue();
        upperCaseRegex = regex;
        String firstLetStr = upperCaseRegex.substring(0, 1);
        String remLetStr = upperCaseRegex.substring(1);
        firstLetStr = firstLetStr.toUpperCase();
        upperCaseRegex = firstLetStr + remLetStr;
    }

    @Override
    public boolean verifyConvention(ASMElement element) throws IOException {
        String name = ASMElementUtils.getName(element);
        if(name.matches(".*"+ regex +".*") || name.matches(".*"+ upperCaseRegex +".*")) {
            return true;
        }

        return false;
    }
}
