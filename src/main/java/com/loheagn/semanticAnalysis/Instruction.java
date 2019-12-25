package com.loheagn.semanticAnalysis;

import java.util.ArrayList;
import java.util.List;

import com.loheagn.utils.NumberToBytes;

public class Instruction {
    private OperationType operation;
    private Integer num1;
    private Integer num2;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder().append(operation.getValue());
        if (num1 != null)
            builder.append(" ").append(num1);
        if (num2 != null)
            builder.append(", ").append(num2);
        return builder.toString();
    }

    /**
     * 为了防止生成指令的时候少些操作数,这里要求必须给出num1和num2的值,如果没有,则要显式给出null
     */
    public Instruction(OperationType operation, Integer num1, Integer num2) {
        CodeStack.offset++;
        this.operation = operation;
        this.num1 = num1;
        this.num2 = num2;
    }

    public List<Byte> toBytes() {
        List<Byte> result = new ArrayList<>();
        result.add(operation.getByteValue());
        if (num1 != null)
            result.addAll(NumberToBytes.numberToBytes(num1, operation.getLength1()));
        if (num2 != null)
            result.addAll(NumberToBytes.numberToBytes(num2, operation.getLength2()));
        return result;
    }

    public int getLength() {
        return operation.getLength();
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
