package com.loheagn.semanticAnalysis;

public enum VariableType {
    INT("int"), DOUBLE("double"), CHAR("char"), VOID("void"), NOVarType("");
    private String name;

    VariableType(String name) {
        this.name = name;
    }

    public static  VariableType getVariableType(String variableName) {
        for(VariableType variableType : VariableType.values()) {
            if(variableType.name.equals(variableName)) return variableType;
        }
        return NOVarType;
    }
}
