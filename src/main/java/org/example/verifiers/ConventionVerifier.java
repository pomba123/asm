package org.example.verifiers;

import org.example.model.ASMElement;
import org.example.model.Convention;
import org.example.model.Parameter;
import org.example.model.Rule;

import java.io.IOException;
import java.util.List;

public interface ConventionVerifier {
    public void init(Rule rule);
    public boolean verifyConvention(ASMElement element) throws IOException;

}
