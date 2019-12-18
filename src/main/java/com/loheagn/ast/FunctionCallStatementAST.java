package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;

import java.util.ArrayList;
import java.util.List;

public class FunctionCallStatementAST extends ForUpdateExpressionAST {
    private String identifier;
    private List<ExpressionAST> expressionASTList = new ArrayList<ExpressionAST>();

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

    public InstructionBlock generateInstructions() {
        return null;
    }
}
