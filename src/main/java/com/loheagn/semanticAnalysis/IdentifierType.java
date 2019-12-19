package com.loheagn.semanticAnalysis;

public enum IdentifierType {
    INT("int"), DOUBLE("double"), CHAR("char"), VOID("void"), NOVarType("");
    private String name;

    IdentifierType(String name) {
        this.name = name;
    }

    public static IdentifierType getVariableType(String variableName) {
        for(IdentifierType identifierType : IdentifierType.values()) {
            if(identifierType.name.equals(variableName)) return identifierType;
        }
        return NOVarType;
    }

    public static IdentifierType getBiggerType(IdentifierType type1, IdentifierType type2){
        if(type1 == IdentifierType.CHAR) return type2;
        else if(type1 == IdentifierType.INT) {
            if(type2 == IdentifierType.DOUBLE) return IdentifierType.DOUBLE;
            else return type1;
        } else return IdentifierType.DOUBLE;
    }
}
