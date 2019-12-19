package com.loheagn.semanticAnalysis;

public enum OperationType {
    iPush("ipush",5), i2c("i2c", 1), i2d("i2d", 1), d2i("d2i", 1), iadd("iadd",1), dadd("dadd", 1), isub("isub", 1), dsub("dsub", 1), imul("imul", 1), dmul("dmul", 1), idiv("idiv", 1), ddiv("ddiv",1), ineg("ineg", 1), dneg("dneg", 1);
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
