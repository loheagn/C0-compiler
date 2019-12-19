package com.loheagn.ast;

import com.loheagn.ast.expressionAST.ExpressionAST;
import com.loheagn.semanticAnalysis.*;
import com.loheagn.utils.CompileException;
import com.loheagn.utils.ExceptionString;

/**
 * 声明变量
 */
public class VariableDeclarationAST extends AST {

    private boolean isConst = false;

    private IdentifierType type;

    private String identifier;

    private ExpressionAST expressionAST;

    public InstructionBlock generateInstructions() throws CompileException {
        if(this.type == IdentifierType.VOID) throw new CompileException(ExceptionString.VoidVariable);
        if(isConst && expressionAST == null) throw new CompileException(ExceptionString.ConstVariableNeedValue);
        InstructionBlock instructionBlock = new InstructionBlock();
        // 计算表达式的值,最终给的结果肯定是放在栈顶上
        InstructionBlock expressionInstructionBlock;
        if(expressionAST!=null)
            expressionInstructionBlock = expressionAST.generateInstructions();
        else {
            expressionInstructionBlock = Blocks.pushZero();
        }
        InstructionBlock castInstructionBlock = Blocks.castTopType(expressionInstructionBlock.getType(), this.type);
        // 填变量表
        int offset = Stack.getOffset();
        if(this.type == IdentifierType.DOUBLE) offset -= Stack.doubleOffset;
        else offset -= Stack.intOffset;
        Table.addVariable(new Identifier(this.type, this.identifier, offset));
        // 填充当前的指令块
        instructionBlock.addInstructionBlock(expressionInstructionBlock);
        instructionBlock.addInstructionBlock(castInstructionBlock);
        return instructionBlock;
    }

    public void setConst() {
        this.isConst = true;
    }

    public void setExpressionAST(ExpressionAST expressionAST) {
        this.expressionAST = expressionAST;
    }

    public boolean isConst() {
        return isConst;
    }

    public void setConst(boolean aConst) {
        isConst = aConst;
    }

    public IdentifierType getType() {
        return type;
    }

    public void setType(IdentifierType type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ExpressionAST getExpressionAST() {
        return expressionAST;
    }

}