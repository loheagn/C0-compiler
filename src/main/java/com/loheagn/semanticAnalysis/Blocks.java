package com.loheagn.semanticAnalysis;

import com.loheagn.tokenizer.TokenType;
import com.loheagn.utils.CompileException;
import com.loheagn.utils.ExceptionString;

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

    /**
     *
     * @param operator  操作符
     * @param type  要计算的两个值的类型
     * @return  返回的指令集
     */
    public static InstructionBlock computeTwoExpressions(TokenType operator, IdentifierType type) throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        if(operator == TokenType.PLUS) {
            if(type == IdentifierType.DOUBLE) {
                instructionBlock.addInstruction(new Instruction(OperationType.dadd,null,null));
            } else {
                instructionBlock.addInstruction(new Instruction(OperationType.iadd,null, null));
            }
        } else if (operator == TokenType.MINUS) {
            if(type == IdentifierType.DOUBLE) {
                instructionBlock.addInstruction(new Instruction(OperationType.dsub,null,null));
            } else {
                instructionBlock.addInstruction(new Instruction(OperationType.isub,null, null));
            }
        } else if(operator == TokenType.MULTI) {
            if(type == IdentifierType.DOUBLE) {
                instructionBlock.addInstruction(new Instruction(OperationType.dmul,null,null));
            } else {
                instructionBlock.addInstruction(new Instruction(OperationType.imul,null, null));
            }
        } else if(operator == TokenType.DIV) {
            if(type == IdentifierType.DOUBLE) {
                instructionBlock.addInstruction(new Instruction(OperationType.ddiv,null,null));
            } else {
                instructionBlock.addInstruction(new Instruction(OperationType.idiv,null, null));
            }
        } else {
            throw new CompileException(ExceptionString.CanNotCompute);
        }
        if(type == IdentifierType.DOUBLE) Stack.pop(Stack.doubleOffset);
        else Stack.pop(Stack.intOffset);
        instructionBlock.setType(type);
        return instructionBlock;
    }

    public static InstructionBlock loadIdentifier(String name){
        InstructionBlock instructionBlock = new InstructionBlock();
        Identifier identifier = Table.getVariable(name);
        int levelGap = 0;
        if(identifier.getLevel() == 0) levelGap = 1;
        instructionBlock.addInstruction(new Instruction(OperationType.loada, levelGap, identifier.getOffset()));
        Stack.push(Stack.intOffset);
        if(identifier.getType() == IdentifierType.DOUBLE) {
            instructionBlock.addInstruction(new Instruction(OperationType.dload, null, null));
            Stack.push(Stack.doubleOffset - Stack.intOffset);
        } else if(identifier.getType() == IdentifierType.CHAR) {
            instructionBlock.addInstruction(new Instruction(OperationType.aload, null, null));
        } else {
            instructionBlock.addInstruction(new Instruction(OperationType.iload, null, null));
        }
        instructionBlock.setType(identifier.getType());
        return instructionBlock;
    }
}
