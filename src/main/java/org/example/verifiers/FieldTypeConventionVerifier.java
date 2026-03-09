package org.example.verifiers;

import org.example.model.ASMElement;
import org.example.model.Field;
import org.example.model.Parameter;
import org.example.model.Rule;

import java.io.IOException;
import java.util.List;

public class FieldTypeConventionVerifier implements ConventionVerifier{
    private String fieldType;
    private boolean canBeSubtype;
    @Override
    public void init(Rule rule) {
        List<Parameter> parameters = rule.getParameters();
        fieldType = parameters.get(0).getType();
        canBeSubtype = rule.isSubtype();
    }

    @Override
    public boolean verifyConvention(ASMElement element) throws IOException {
        String elementType = ((Field) element).getType();
        return fieldType.equals(elementType);
    }
}
