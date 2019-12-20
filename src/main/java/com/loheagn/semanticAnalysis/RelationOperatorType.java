package com.loheagn.semanticAnalysis;

import com.loheagn.utils.CompileException;
import com.loheagn.utils.ExceptionString;

public enum RelationOperatorType {
    LESS("<"), GREATER(">"), LE("<="), GE(">="), NOT_EQUAL("!="), EQUAL("==");
    private String name;

    RelationOperatorType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static RelationOperatorType getRelationOperatorType(String name) throws CompileException {
        for (RelationOperatorType relationOperatorType : RelationOperatorType.values()) {
            if (relationOperatorType.getName().equals(name)) return relationOperatorType;
        }
        throw new CompileException(ExceptionString.RelationOperatorError);
    }
}
