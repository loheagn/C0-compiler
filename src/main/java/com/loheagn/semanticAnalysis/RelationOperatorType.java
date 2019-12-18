package com.loheagn.semanticAnalysis;

public enum RelationOperatorType {
    NO(""), LESS("<"), GREATER(">"), LE("<="), GE(">="), NOT_EQUAL("!="), EQUAL("==");
    private String name;

    RelationOperatorType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static RelationOperatorType getRelationOperatorType(String name) {
        for (RelationOperatorType relationOperatorType : RelationOperatorType.values()) {
            if (relationOperatorType.getName().equals(name)) return relationOperatorType;
        }
        return NO;
    }
}
