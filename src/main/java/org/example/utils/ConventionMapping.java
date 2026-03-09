package org.example.utils;

import org.example.model.Convention;

import java.util.List;

public class ConventionMapping {
    private List<Convention> conventions;
    private boolean allRulesMustApply;
    public List<Convention> getConventions() { return conventions; }
    public void setConventions(List<Convention> conventions) { this.conventions = conventions; }

    public boolean isAllRulesMustApply() {
        return allRulesMustApply;
    }

    public void setAllRulesMustApply(boolean allRulesMustApply) {
        this.allRulesMustApply = allRulesMustApply;
    }
}
