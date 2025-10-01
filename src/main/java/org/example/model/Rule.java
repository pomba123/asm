package org.example.model;

import java.util.List;

public  class Rule {
    private String name;  // Renamed from "rule"
    private List<Parameter> parameters;
    private boolean searchOnEnclosingElement;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


    public List<Parameter> getParameters() { return parameters; }
    public void setParameters(List<Parameter> parameters) { this.parameters = parameters; }

    public boolean isSearchOnEnclosingElement() {
        return searchOnEnclosingElement;
    }

    public void setSearchOnEnclosingElement(boolean searchOnEnclosingElement) {
        this.searchOnEnclosingElement = searchOnEnclosingElement;
    }
}
