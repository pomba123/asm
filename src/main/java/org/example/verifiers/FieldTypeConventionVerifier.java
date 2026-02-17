package org.example.verifiers;

import org.example.model.ASMElement;
import org.example.model.Field;
import org.example.model.Parameter;

import java.io.IOException;
import java.util.List;

public class FieldTypeConventionVerifier implements ConventionVerifier{
    private String fieldType;
    @Override
    public void init(List<Parameter> parameters) {
        fieldType = parameters.get(0).getType();
    }

    @Override
    public boolean verifyConvention(ASMElement element) throws IOException {
        String elementType = ((Field) element).getType();
        return fieldType.equals(elementType);
    }
}
