package com.loheagn.semanticAnalysis;

import java.util.ArrayList;
import java.util.List;

public class InstructionBlock {
    private List<Instruction> instructions;
    private int length;
    private VariableType type;   // 这个指令块执行结束后,栈顶的值的类型

    public InstructionBlock(){
        instructions = new ArrayList<Instruction>();
        length = 0;
    }

    public void addInstructionBlock(InstructionBlock instructionBlock){
        instructions.addAll(instructionBlock.getInstructions());
        length += instructionBlock.getLength();
        type = instructionBlock.getType();
    }

    public void addInstruction(Instruction instruction) {
        this.instructions.add(instruction);
        length += instruction.getOperation().getLength();
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public VariableType getType() {
        return type;
    }

    public void setType(VariableType type) {
        this.type = type;
    }
}
