package org.example.utils;

import java.util.List;

public  class ConventionAnnotation {
    private String name;
    private List<Parameter> parameters;

    // Getters and setters


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Parameter> getParameters() { return parameters; }
    public void setParameters(List<Parameter> parameters) { this.parameters = parameters; }
}
