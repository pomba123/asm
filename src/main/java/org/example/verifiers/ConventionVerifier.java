package org.example.verifiers;

import org.example.model.Rule;

import java.io.IOException;
import java.util.List;

public interface ConventionVerifier {

    public boolean verifyConvention(Object element, List<Rule> rules) throws IOException;

}
