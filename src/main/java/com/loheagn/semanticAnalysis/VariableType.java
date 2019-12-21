package com.loheagn.semanticAnalysis;

public enum VariableType {
    INT("int"), DOUBLE("double"), CHAR("char"), VOID("void"), NOVarType("");
    private String name;

    VariableType(String name) {
        this.name = name;
    }

    public static VariableType getVariableType(String variableName) {
        for(VariableType variableType : VariableType.values()) {
            if(variableType.name.equals(variableName)) return variableType;
        }
        return NOVarType;
    }

    public static VariableType getBiggerType(VariableType type1, VariableType type2){
        if(type1 == VariableType.CHAR) return type2;
        else if(type1 == VariableType.INT) {
            if(type2 == VariableType.DOUBLE) return VariableType.DOUBLE;
            else return type1;
        } else return VariableType.DOUBLE;
    }
}
