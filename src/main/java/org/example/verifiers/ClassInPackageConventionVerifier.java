package org.example.verifiers;

import org.example.model.ASMElement;
import org.example.model.Parameter;
import org.example.utils.ASMElementUtils;

import java.io.IOException;
import java.util.List;

public class ClassInPackageConventionVerifier implements ConventionVerifier{
    private String packageName;
    @Override
    public void init(List<Parameter> parameters) {
        this.packageName=parameters.get(0).getValue();

    }

    @Override
    public boolean verifyConvention(ASMElement element) throws IOException {
        String elementPackageName = ASMElementUtils.getPackageName(element);
        return elementPackageName.equals(packageName);
    }
}
