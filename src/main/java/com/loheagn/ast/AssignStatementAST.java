package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;

public class AssignStatementAST extends ForUpdateExpressionAST {
    private String identifier;
    private ExpressionAST expressionAST;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ExpressionAST getExpressionAST() {
        return expressionAST;
    }

    public void setExpressionAST(ExpressionAST expressionAST) {
        this.expressionAST = expressionAST;
    }

    public InstructionBlock generateInstructions() {
        return null;
    }
}
