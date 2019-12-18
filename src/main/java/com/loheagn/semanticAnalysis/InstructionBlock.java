package com.loheagn.semanticAnalysis;

import java.util.ArrayList;
import java.util.List;

public class InstructionBlock {
    private List<Instruction> instructions;
    private int length;
    private Number value;   // 该指令块返回的值

    public InstructionBlock(){
        instructions = new ArrayList<Instruction>();
        length = 0;
    }

    public void addInstruction(Instruction instruction) {
        this.instructions.add(instruction);
        length += instruction.getOperation().getLength();
    }
}
