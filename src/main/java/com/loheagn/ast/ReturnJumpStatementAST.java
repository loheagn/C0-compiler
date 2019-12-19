package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.utils.CompileException;

public class ReturnJumpStatementAST extends JumpStatementAST {
    private ExpressionAST expressionAST;

    public ExpressionAST getExpressionAST() {
        return expressionAST;
    }

    public void setExpressionAST(ExpressionAST expressionAST) {
        this.expressionAST = expressionAST;
    }

    public InstructionBlock generateInstructions() throws CompileException {
        return null;
    }
}
