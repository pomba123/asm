package org.example.verifiers;

import org.example.utils.ObjectUtils;
import org.example.model.Rule;

import java.io.IOException;
import java.util.List;

public class PrefixConventionVerifier implements  ConventionVerifier{
    @Override
    public boolean verifyConvention(Object object, List<Rule> rules) throws IOException {
        boolean hasPrefixOnEnclosing = false;
        String objectName = ObjectUtils.getName(object);
        String prefix = rules.get(0).getParameters().get(0).getValue();
        boolean searchOnEnclosing = rules.get(0).isSearchOnEnclosingElement();
        boolean hasPrefix = objectName.startsWith(prefix);
        if(searchOnEnclosing){
            Class clazz = ObjectUtils.getDeclaringClass(object);
            String objectClassName = ObjectUtils.getName(clazz);
            hasPrefixOnEnclosing = objectClassName.startsWith(prefix);
        }
        return hasPrefix || hasPrefixOnEnclosing;

    }


}
