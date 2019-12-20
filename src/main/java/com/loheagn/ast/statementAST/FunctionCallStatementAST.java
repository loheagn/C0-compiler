package com.loheagn.ast.statementAST;

import com.loheagn.ast.expressionAST.ExpressionAST;
import com.loheagn.ast.loopAST.ForUpdateExpressionAST;
import com.loheagn.semanticAnalysis.*;
import com.loheagn.utils.CompileException;
import com.loheagn.utils.ExceptionString;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallStatementAST extends ForUpdateExpressionAST {
    private String identifier;
    private List<ExpressionAST> expressionASTList = new ArrayList<ExpressionAST>();

    public InstructionBlock generateInstructions() throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        Function function = Table.getFunction(identifier);
        if(function.getParameters().size()!=expressionASTList.size()) throw new CompileException(ExceptionString.FunctionParametersNumberNotMatch);
        for(int i = 0;i<expressionASTList.size();i++) {
            instructionBlock.addInstructionBlock(expressionASTList.get(i).generateInstructions());
            instructionBlock.addInstructionBlock(Blocks.castTopType(instructionBlock.getType(), function.getParameters().get(i).getType()));
        }
        instructionBlock.addInstruction(new Instruction(OperationType.call, Table.getFunctionIndex(identifier),null));
        Stack.push(function.getName().getType());
        instructionBlock.setType(function.getName().getType());
        return instructionBlock;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<ExpressionAST> getExpressionASTList() {
        return expressionASTList;
    }

    public void setExpressionASTList(List<ExpressionAST> expressionASTList) {
        this.expressionASTList = expressionASTList;
    }
}
