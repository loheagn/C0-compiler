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
}
