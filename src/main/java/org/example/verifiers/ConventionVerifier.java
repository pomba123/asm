package org.example.verifiers;

import org.example.model.ASMElement;
import org.example.model.Parameter;
import java.io.IOException;
import java.util.List;

public interface ConventionVerifier {
    public void init(List<Parameter> parameters);
    public boolean verifyConvention(ASMElement element) throws IOException;

}
