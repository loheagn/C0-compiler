package com.loheagn.semanticAnalysis;

public enum OperationType {
    iPush("ipush",5), i2c("i2c", 1), i2d("i2d", 1), d2i("d2i", 1);
    private String value;
    private int length;

    OperationType(String value, int length) {
        this.value = value;
        this.length = length;
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
