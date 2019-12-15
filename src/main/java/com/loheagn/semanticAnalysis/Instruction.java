package com.loheagn.semanticAnalysis;

public class Instruction {
    private OperationType operation;
    private Integer num1;
    private Integer num2;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder().append(operation.getValue());
        if (num1 != null) builder.append(" ").append(num1);
        if (num2 != null) builder.append(", ").append(num2);
        return builder.toString();
    }
}
