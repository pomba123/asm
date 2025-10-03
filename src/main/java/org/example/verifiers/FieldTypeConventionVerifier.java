package org.example.verifiers;

import org.example.model.Rule;
import org.example.model.iField;

import java.io.IOException;
import java.util.List;

public class FieldTypeConventionVerifier implements ConventionVerifier{
    @Override
    public boolean verifyConvention(Object element, List<Rule> rules) throws IOException {
        Rule fieldType = rules.get(0);
        String fieldTypeClass = fieldType.getParameters().get(0).getValue();
        iField field = (iField) element;
        return field.getType().equals(fieldTypeClass);
    }
}
