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

    public static InstructionBlock loadIdentifier(String name) throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        Identifier identifier = Table.getVariable(name);
        instructionBlock.addInstructionBlock(loadAddress(identifier));
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

    /**
     * 根据标识符的名字,加载一个变量的地址
     * @param name  变量的名字
     * @return  加载这个地址所需要的指令
     */
    public static InstructionBlock loadAddress(String name) throws CompileException {
        Identifier identifier = Table.getVariable(name);
        return loadAddress(identifier);
    }

    /**
     * 对上面那个方法的重载
     */
    public static InstructionBlock loadAddress(Identifier identifier) throws CompileException {
        int levelGap = 0;
        if(identifier.getLevel() == 0) levelGap = 1;
        InstructionBlock instructionBlock = new InstructionBlock();
        instructionBlock.addInstruction(new Instruction(OperationType.loada, levelGap, identifier.getOffset()));
        Stack.push(Stack.intOffset);
        return instructionBlock;
    }

    public static InstructionBlock storeVariable(IdentifierType identifierType) throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        if(identifierType == IdentifierType.DOUBLE) {
            instructionBlock.addInstruction(new Instruction(OperationType.dstore,null,null));
            Stack.pop(Stack.doubleOffset + Stack.intOffset);
        } else if(identifierType == IdentifierType.CHAR) {
            instructionBlock.addInstruction(new Instruction(OperationType.aload, null,null));
            Stack.pop(Stack.intOffset);
        } else {
            instructionBlock.addInstruction(new Instruction(OperationType.iload, null,null));
            Stack.pop(Stack.intOffset);
        }
        instructionBlock.setType(IdentifierType.VOID);
        return instructionBlock;
    }
}
