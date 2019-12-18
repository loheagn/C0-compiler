package com.loheagn.semanticAnalysis;

public enum OperationType {
    ;
    private String value;
    private int length;

    OperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int getLength() {
        return length;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
