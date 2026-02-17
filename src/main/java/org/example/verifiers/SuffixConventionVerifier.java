package org.example.verifiers;

import org.example.model.ASMElement;
import org.example.model.Method;
import org.example.model.Parameter;
import org.example.utils.ASMElementUtils;
import org.example.utils.ObjectUtils;
import org.example.model.Rule;

import java.io.IOException;
import java.util.List;

public class SuffixConventionVerifier implements ConventionVerifier{
    private String suffix;

    public void init(List<Parameter> parameters){

        suffix = parameters.get(0).getValue().toLowerCase();

    }
    @Override
    public boolean verifyConvention(ASMElement object) throws IOException {

        String objectName = ASMElementUtils.getName(object).toLowerCase();
        return objectName.endsWith(suffix);
    }
}
