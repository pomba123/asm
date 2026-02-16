package org.example.verifiers;

import org.example.model.ASMElement;
import org.example.model.Class;
import org.example.model.Parameter;

import java.io.IOException;
import java.util.List;

public class ClassImplementsInterfaceConventionVerifier implements ConventionVerifier{
    private String implementedInterface;
    @Override
    public void init(List<Parameter> parameters) {
        this.implementedInterface = parameters.get(0).getValue();
    }

    @Override
    public boolean verifyConvention(ASMElement element) throws IOException {
        List<String> interfaces = ((Class) element).getInterfaces();
        return interfaces.contains(implementedInterface);
    }
}
