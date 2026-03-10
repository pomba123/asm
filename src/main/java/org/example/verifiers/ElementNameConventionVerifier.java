package org.example.verifiers;

import org.example.model.*;
import org.example.utils.ASMElementUtils;

import java.io.IOException;
import java.util.List;

public class ElementNameConventionVerifier implements ConventionVerifier{
    private String name;
    private boolean serachOnEnclosing;
    @Override
    public void init(Rule rule) {
        List<Parameter> parameters = rule.getParameters();
        name = parameters.get(0).getValue().toLowerCase();
        serachOnEnclosing = rule.isSearchOnEnclosingElement();
    }

    @Override
    public boolean verifyConvention(ASMElement element) throws IOException {
        String elementName = ASMElementUtils.getName(element);
        if(serachOnEnclosing){
            if(element instanceof Method){
                Method method = (Method) element;
                String methodDeclaringclassName = method.getDeclaringClass().getName().toLowerCase();
                return name.equals(methodDeclaringclassName);
            }else if (element instanceof Field){
                Field field = (Field) element;
                String fieldDeclaringclassName = field.getDeclaringClazz().getName().toLowerCase();
                return name.equals(fieldDeclaringclassName);
            }
        }
        return elementName.toLowerCase().equals(name);
    }
}
