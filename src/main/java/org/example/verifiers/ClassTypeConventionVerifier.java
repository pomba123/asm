package org.example.verifiers;

import org.example.model.ASMElement;
import org.example.model.Parameter;
import org.example.model.Class;
import org.example.model.Rule;

import java.io.IOException;
import java.util.List;

public class ClassTypeConventionVerifier implements ConventionVerifier{
    private String classType;
    private boolean canBeSubtype = false;
    @Override
    public void init(Rule rule) {
        List<Parameter> parameters = rule.getParameters();
        String type = parameters.get(0).getValue();
        classType = type;
        canBeSubtype = rule.isSubtype();
    }

    @Override
    public boolean verifyConvention(ASMElement element) throws IOException {
        Class clazz = (Class) element;
        String clazztype = clazz.getName();
        if(canBeSubtype)
            return classType.equals(clazz.getSuperName());
        else
            return classType.equals(clazztype);

    }

}
