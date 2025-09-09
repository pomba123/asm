package org.example.verifiers;

import org.example.utils.ObjectUtils;
import org.example.utils.Parameter;
import org.example.utils.Rule;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class SuffixConventionVerifier implements ConventionVerifier{
    @Override
    public boolean verifyConvention(Object object, List<Rule> rules) throws IOException {
        boolean hasSuffixOnEnclosing = false;
        String objectName = ObjectUtils.getName(object);
        String suffix = rules.get(0).getParameters().get(0).getValue();
        boolean searchOnEnclosing = rules.get(0).isSearchOnEnclosingElement();
        boolean hasSuffix = objectName.endsWith(suffix);
        if(searchOnEnclosing){
            Class clazz = ObjectUtils.getDeclaringClass(object);
            String objectClassName = ObjectUtils.getName(clazz);
            hasSuffixOnEnclosing = objectClassName.endsWith(suffix);
        }
        return hasSuffix || hasSuffixOnEnclosing;
    }
}
