package com.loheagn.ast.jumpAST;

import com.loheagn.ast.expressionAST.ExpressionAST;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.utils.CompileException;

public class ReturnJumpStatementAST extends JumpStatementAST {
    private ExpressionAST expressionAST;

    public InstructionBlock generateInstructions() throws CompileException {
        return expressionAST.generateInstructions();
    }

    public ExpressionAST getExpressionAST() {
        return expressionAST;
    }

    public void setExpressionAST(ExpressionAST expressionAST) {
        this.expressionAST = expressionAST;
    }

}
