package com.loheagn.semanticAnalysis;

public class Parameter {

    private boolean isConst;
    private VariableType variableType;
    private String name;

    public Parameter (VariableType type, String name) {
        this.variableType = type;
        this.name = name;
    }

    public boolean isConst() {
        return isConst;
    }

    public void setConst(boolean aConst) {
        isConst = aConst;
    }

    public VariableType getVariableType() {
        return variableType;
    }

    public void setVariableType(VariableType variableType) {
        this.variableType = variableType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
