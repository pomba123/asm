package org.example.model;

import java.util.List;
public class Convention {

    private String name;
    private List<Rule> rules;
    private ConventionAnnotation annotation;
    private ConventionScope conventionScope;
    private String implementation;

    private boolean allRulesMustApply;
    // Getters and setters
    public String getName() {
        return name;
    }

    public String getImplementation() {
        return implementation;
    }

    public void setImplementation(String implementation) {
        this.implementation = implementation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConventionScope getConventionScope() {
        return conventionScope;
    }

    public void setConventionScope(ConventionScope conventionScope) {
        this.conventionScope = conventionScope;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public ConventionAnnotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(ConventionAnnotation annotation) {
        this.annotation = annotation;
    }



    public boolean isAllRulesMustApply() {
        return allRulesMustApply;
    }

    public void setAllRulesMustApply(boolean allRulesMustApply) {
        this.allRulesMustApply = allRulesMustApply;
    }
}