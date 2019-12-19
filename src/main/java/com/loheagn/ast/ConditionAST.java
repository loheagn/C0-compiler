package com.loheagn.ast;

import com.loheagn.ast.expressionAST.ExpressionAST;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.semanticAnalysis.RelationOperatorType;
import com.loheagn.utils.CompileException;

public class ConditionAST extends AST {
    private ExpressionAST expressionAST1;
    private ExpressionAST expressionAST2;
    private RelationOperatorType relationOperator;

    public ExpressionAST getExpressionAST1() {
        return expressionAST1;
    }

    public void setExpressionAST1(ExpressionAST expressionAST1) {
        this.expressionAST1 = expressionAST1;
    }

    public ExpressionAST getExpressionAST2() {
        return expressionAST2;
    }

    public void setExpressionAST2(ExpressionAST expressionAST2) {
        this.expressionAST2 = expressionAST2;
    }

    public RelationOperatorType getRelationOperator() {
        return relationOperator;
    }

    public void setRelationOperator(RelationOperatorType relationOperator) {
        this.relationOperator = relationOperator;
    }

    public InstructionBlock generateInstructions() throws CompileException {
        return null;
    }
}
