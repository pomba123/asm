package org.example.verifiers;

import org.example.model.*;
import org.example.utils.ASMElementUtils;
import org.example.utils.ObjectUtils;

import java.io.IOException;
import java.util.List;

public class SuffixConventionVerifier implements ConventionVerifier{
    private String suffix;
    private boolean searchOnEnclosing;
    public void init(Rule rule){
        List<Parameter> parameters = rule.getParameters();
        suffix = parameters.get(0).getValue().toLowerCase();
        searchOnEnclosing = rule.isSearchOnEnclosingElement();
    }
    @Override
    public boolean verifyConvention(ASMElement object) throws IOException {

        String objectName = ASMElementUtils.getName(object).toLowerCase();
        if(searchOnEnclosing){
            if(object instanceof Method){
                Method method = (Method) object;
                String name = method.getDeclaringClass().getName();
                return name.toLowerCase().endsWith(suffix);
            }else if (object instanceof Field){
                Field field = (Field) object;
                String name = field.getDeclaringClazz().getName();
                return name.toLowerCase().endsWith(suffix);
            }
        }
        return objectName.toLowerCase().endsWith(suffix);
    }
}
