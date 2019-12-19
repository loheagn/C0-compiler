package com.loheagn.semanticAnalysis;

public class Blocks {

    public static InstructionBlock pushZero(){
        InstructionBlock instructionBlock = new InstructionBlock();
        instructionBlock.addInstruction(new Instruction(OperationType.iPush, 0, null));
        Stack.push(1);
        instructionBlock.setType(IdentifierType.INT);
        return instructionBlock;
    }

    public static InstructionBlock castTopType(IdentifierType srcType, IdentifierType dstType) {
        InstructionBlock instructionBlock = new InstructionBlock();
        if(srcType.equals(dstType)) {
            instructionBlock.setType(srcType);
        } else {
            if(dstType == IdentifierType.CHAR) {
                if(srcType == IdentifierType.INT){
                    instructionBlock.addInstruction(new Instruction(OperationType.i2c, null, null));
                    instructionBlock.setType(IdentifierType.CHAR);
                } else if(srcType == IdentifierType.DOUBLE) {
                    instructionBlock.addInstruction(new Instruction(OperationType.d2i, null,null));
                    Stack.pop(1);
                    instructionBlock.addInstruction(new Instruction(OperationType.i2c, null, null));
                    instructionBlock.setType(IdentifierType.CHAR);
                }
            } else if(dstType == IdentifierType.INT) {
                if(srcType == IdentifierType.DOUBLE) {
                    instructionBlock.addInstruction(new Instruction(OperationType.d2i, null, null));
                    Stack.pop(1);
                    instructionBlock.setType(IdentifierType.INT);
                } else if(srcType == IdentifierType.CHAR) {
                    instructionBlock.setType(IdentifierType.INT);
                }
            } else if(dstType == IdentifierType.DOUBLE) {
                if(srcType == IdentifierType.CHAR || srcType == IdentifierType.INT) {
                    instructionBlock.addInstruction(new Instruction(OperationType.i2d, null, null));
                    Stack.push(1);
                    instructionBlock.setType(IdentifierType.DOUBLE);
                }
            }
        }
        return instructionBlock;
    }
}
