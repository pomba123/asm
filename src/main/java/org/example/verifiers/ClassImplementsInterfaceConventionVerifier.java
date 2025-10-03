package org.example.verifiers;

import org.example.model.Parameter;
import org.example.model.Rule;
import org.example.model.iClass;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ClassImplementsInterfaceConventionVerifier implements ConventionVerifier{
    @Override
    public boolean verifyConvention(Object element, List<Rule> rules) throws IOException {
        Rule implementsInterface = rules.get(0);
        Parameter implementedInterface = implementsInterface.getParameters().get(0);
        String classOfInterface = implementedInterface.getValue();
        iClass clazz = (iClass) element;
        return Arrays.asList(clazz.getInterfaces()).contains(classOfInterface);
    }
}
