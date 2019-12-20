package com.loheagn.ast.conditionAST;

import com.loheagn.ast.AST;
import com.loheagn.ast.expressionAST.ExpressionAST;
import com.loheagn.semanticAnalysis.Blocks;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.semanticAnalysis.RelationOperatorType;
import com.loheagn.tokenizer.TokenType;
import com.loheagn.utils.CompileException;

public class ConditionAST extends AST {
    private ExpressionAST expressionAST1;
    private ExpressionAST expressionAST2;
    private RelationOperatorType relationOperator;

    public InstructionBlock generateInstructions() throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        InstructionBlock expression1 = expressionAST1.generateInstructions();
        InstructionBlock expression2 = expressionAST2.generateInstructions();
        instructionBlock.addInstructionBlock(expression1);
        instructionBlock.addInstructionBlock(Blocks.computeTwoExpressions(expression1, expression2, TokenType.MINUS));
        return instructionBlock;
    }

    public void setExpressionAST1(ExpressionAST expressionAST1) {
        this.expressionAST1 = expressionAST1;
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

}
