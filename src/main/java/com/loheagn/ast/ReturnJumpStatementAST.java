package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;

public class ReturnJumpStatementAST extends JumpStatementAST {
    private ExpressionAST expressionAST;

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
