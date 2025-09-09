package org.example.verifiers;

import org.example.ConventionScope;
import org.example.utils.ConventionAnnotation;
import org.example.utils.Rule;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public interface ConventionVerifier {

    public boolean verifyConvention(Object element, List<Rule> rules) throws IOException;

}
