package com.loheagn.semanticAnalysis;

import com.loheagn.tokenizer.TokenType;
import com.loheagn.utils.CompileException;
import com.loheagn.utils.ExceptionString;

public class Blocks {

    public static InstructionBlock pushZero(){
        InstructionBlock instructionBlock = new InstructionBlock();
        instructionBlock.addInstruction(new Instruction(OperationType.iPush, 0, null));
        Stack.push(1);
        instructionBlock.setType(VariableType.INT);
        return instructionBlock;
    }

    public static InstructionBlock castTopType(VariableType srcType, VariableType dstType) {
        InstructionBlock instructionBlock = new InstructionBlock();
        if(srcType.equals(dstType)) {
            instructionBlock.setType(srcType);
        } else {
            if(dstType == VariableType.CHAR) {
                if(srcType == VariableType.INT){
                    instructionBlock.addInstruction(new Instruction(OperationType.i2c, null, null));
                    instructionBlock.setType(VariableType.CHAR);
                } else if(srcType == VariableType.DOUBLE) {
                    instructionBlock.addInstruction(new Instruction(OperationType.d2i, null,null));
                    Stack.pop(1);
                    instructionBlock.addInstruction(new Instruction(OperationType.i2c, null, null));
                    instructionBlock.setType(VariableType.CHAR);
                }
            } else if(dstType == VariableType.INT) {
                if(srcType == VariableType.DOUBLE) {
                    instructionBlock.addInstruction(new Instruction(OperationType.d2i, null, null));
                    Stack.pop(1);
                    instructionBlock.setType(VariableType.INT);
                } else if(srcType == VariableType.CHAR) {
                    instructionBlock.setType(VariableType.INT);
                }
            } else if(dstType == VariableType.DOUBLE) {
                if(srcType == VariableType.CHAR || srcType == VariableType.INT) {
                    instructionBlock.addInstruction(new Instruction(OperationType.i2d, null, null));
                    Stack.push(1);
                    instructionBlock.setType(VariableType.DOUBLE);
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
    public static InstructionBlock computeTwoExpressions(TokenType operator, VariableType type) throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        if(operator == TokenType.PLUS) {
            if(type == VariableType.DOUBLE) {
                instructionBlock.addInstruction(new Instruction(OperationType.dadd,null,null));
            } else {
                instructionBlock.addInstruction(new Instruction(OperationType.iadd,null, null));
            }
        } else if (operator == TokenType.MINUS) {
            if(type == VariableType.DOUBLE) {
                instructionBlock.addInstruction(new Instruction(OperationType.dsub,null,null));
            } else {
                instructionBlock.addInstruction(new Instruction(OperationType.isub,null, null));
            }
        } else if(operator == TokenType.MULTI) {
            if(type == VariableType.DOUBLE) {
                instructionBlock.addInstruction(new Instruction(OperationType.dmul,null,null));
            } else {
                instructionBlock.addInstruction(new Instruction(OperationType.imul,null, null));
            }
        } else if(operator == TokenType.DIV) {
            if(type == VariableType.DOUBLE) {
                instructionBlock.addInstruction(new Instruction(OperationType.ddiv,null,null));
            } else {
                instructionBlock.addInstruction(new Instruction(OperationType.idiv,null, null));
            }
        } else {
            throw new CompileException(ExceptionString.CanNotCompute);
        }
        if(type == VariableType.DOUBLE) Stack.pop(Stack.doubleOffset);
        else Stack.pop(Stack.intOffset);
        instructionBlock.setType(type);
        return instructionBlock;
    }

    /**
     * 计算两个表达式
     * 返回的指令块包括 对expression1的类型转换, 对expression2的计算, 对expression2的类型转换, 对这两个表达式的计算
     */
    public static InstructionBlock computeTwoExpressions(InstructionBlock expression1, InstructionBlock expression2, TokenType operator) {
        InstructionBlock instructionBlock = new InstructionBlock();
        // 首先选出类型转换的目标类型
        VariableType dstType = VariableType.getBiggerType(expression1.getType(), expression2.getType());
        // 把第一个表达式的结果进行类型转换
        instructionBlock.addInstructionBlock(Blocks.castTopType(expression1.getType(), dstType));
        // 第二个表达式
        instructionBlock.addInstructionBlock(expression2);
        instructionBlock.addInstructionBlock(Blocks.castTopType(expression2.getType(), dstType));
        // 计算两个表达式
        instructionBlock.addInstructionBlock(Blocks.computeTwoExpressions(operator, dstType));
        return instructionBlock;
    }

    public static InstructionBlock loadIdentifier(String name) throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        Variable variable = Table.getVariable(name);
        instructionBlock.addInstructionBlock(loadAddress(variable));
        if(variable.getType() == VariableType.DOUBLE) {
            instructionBlock.addInstruction(new Instruction(OperationType.dload, null, null));
            Stack.push(Stack.doubleOffset - Stack.intOffset);
        } else if(variable.getType() == VariableType.CHAR) {
            instructionBlock.addInstruction(new Instruction(OperationType.aload, null, null));
        } else {
            instructionBlock.addInstruction(new Instruction(OperationType.iload, null, null));
        }
        instructionBlock.setType(variable.getType());
        return instructionBlock;
    }

    /**
     * 根据标识符的名字,加载一个变量的地址
     * @param name  变量的名字
     * @return  加载这个地址所需要的指令
     */
    public static InstructionBlock loadAddress(String name) throws CompileException {
        Variable variable = Table.getVariable(name);
        return loadAddress(variable);
    }

    /**
     * 对上面那个方法的重载
     */
    public static InstructionBlock loadAddress(Variable variable) throws CompileException {
        int levelGap = 0;
        if(variable.getLevel() == 0) levelGap = 1;
        InstructionBlock instructionBlock = new InstructionBlock();
        instructionBlock.addInstruction(new Instruction(OperationType.loada, levelGap, variable.getOffset()));
        Stack.push(Stack.intOffset);
        return instructionBlock;
    }

    public static InstructionBlock storeVariable(VariableType variableType) throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        if(variableType == VariableType.DOUBLE) {
            instructionBlock.addInstruction(new Instruction(OperationType.dstore,null,null));
            Stack.pop(Stack.doubleOffset + Stack.intOffset);
        } else if(variableType == VariableType.CHAR) {
            instructionBlock.addInstruction(new Instruction(OperationType.aload, null,null));
            Stack.pop(Stack.intOffset);
        } else {
            instructionBlock.addInstruction(new Instruction(OperationType.iload, null,null));
            Stack.pop(Stack.intOffset);
        }
        instructionBlock.setType(VariableType.VOID);
        return instructionBlock;
    }

    public static InstructionBlock printString() throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        instructionBlock.addInstruction(new Instruction(OperationType.sprint, 0,0));
        Stack.pop(Stack.intOffset);
        return instructionBlock;
    }

    public static InstructionBlock printValue(VariableType variableType) throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        if(variableType == VariableType.DOUBLE) {
            instructionBlock.addInstruction(new Instruction(OperationType.dprint, 0,0));
            Stack.pop(Stack.doubleOffset);
        } else if(variableType == VariableType.CHAR) {
            instructionBlock.addInstruction(new Instruction(OperationType.cprint, 0,0));
            Stack.pop(Stack.intOffset);
        } else if(variableType == VariableType.INT) {
            instructionBlock.addInstruction(new Instruction(OperationType.iprint, 0,0));
            Stack.pop(Stack.intOffset);
        } else throw new CompileException(ExceptionString.Print);
        return instructionBlock;
    }

    public static InstructionBlock scanValue(VariableType variableType) throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        if(variableType == VariableType.DOUBLE) {
            instructionBlock.addInstruction(new Instruction(OperationType.dscan, 0,0));
            Stack.push(Stack.doubleOffset);
        } else if(variableType == VariableType.CHAR) {
            instructionBlock.addInstruction(new Instruction(OperationType.cscan, 0,0));
            Stack.push(Stack.intOffset);
        } else if(variableType == VariableType.INT) {
            instructionBlock.addInstruction(new Instruction(OperationType.iscan, 0,0));
            Stack.push(Stack.intOffset);
        } else throw new CompileException(ExceptionString.Scan);
        instructionBlock.setType(variableType);
        return instructionBlock;
    }

    public static InstructionBlock jumpNot(RelationOperatorType operatorType, int offset) {
        InstructionBlock instructionBlock = new InstructionBlock();
        OperationType operationType;
        switch (operatorType) {
            case EQUAL:
                operationType = OperationType.jne;
                break;
            case NOT_EQUAL:
                operationType = OperationType.je;
                break;
            case LE:
                operationType = OperationType.jg;
                break;
            case GREATER:
                operationType = OperationType.jle;
                break;
            case GE:
                operationType = OperationType.jl;
                break;
            default:
                operationType = OperationType.jge;
                break;
        }
        instructionBlock.addInstruction(new Instruction(operationType, offset,null));
        Stack.pop(Stack.intOffset);
        return instructionBlock;
    }

    public static InstructionBlock jumpYes(RelationOperatorType operatorType, int offset) {
        InstructionBlock instructionBlock = new InstructionBlock();
        OperationType operationType;
        switch (operatorType) {
            case EQUAL:
                operationType = OperationType.je;
                break;
            case NOT_EQUAL:
                operationType = OperationType.jne;
                break;
            case LE:
                operationType = OperationType.jle;
                break;
            case GREATER:
                operationType = OperationType.jg;
                break;
            case GE:
                operationType = OperationType.jge;
                break;
            default:
                operationType = OperationType.jl;
                break;
        }
        instructionBlock.addInstruction(new Instruction(operationType, offset,null));
        Stack.pop(Stack.intOffset);
        return instructionBlock;
    }

    public static InstructionBlock jump(int offset) {
        InstructionBlock instructionBlock = new InstructionBlock();
        instructionBlock.addInstruction(new Instruction(OperationType.jmp,offset,null));
        return instructionBlock;
    }

    public static InstructionBlock returnBlock(VariableType type) {
        InstructionBlock instructionBlock = new InstructionBlock();
        switch (type) {
            case VOID:
                instructionBlock.addInstruction(new Instruction(OperationType.ret,0,0));
                break;
            case CHAR:
                instructionBlock.addInstruction(new Instruction(OperationType.cret, 0,0));
                break;
            case DOUBLE:
                instructionBlock.addInstruction(new Instruction(OperationType.dret,0,0));
                break;
            case INT:
                instructionBlock.addInstruction(new Instruction(OperationType.iret,0,0));
                break;
        }
        return instructionBlock;
    }
}
