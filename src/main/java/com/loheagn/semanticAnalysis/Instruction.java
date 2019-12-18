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

    public OperationType getOperation() {
        return operation;
    }

    public void setOperation(OperationType operation) {
        this.operation = operation;
    }

    public Integer getNum1() {
        return num1;
    }

    public void setNum1(Integer num1) {
        this.num1 = num1;
    }

    public Integer getNum2() {
        return num2;
    }

    public void setNum2(Integer num2) {
        this.num2 = num2;
    }
}
