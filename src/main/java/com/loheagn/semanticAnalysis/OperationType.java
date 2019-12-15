package com.loheagn.semanticAnalysis;

public enum OperationType {
    ;
    private String value;

    OperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
