package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.tokenizer.TokenType;

public class UnaryExpressionAST extends ExpressionAST {
    TokenType operator;
    Object primaryExpression;

    public TokenType getOperator() {
        return operator;
    }

    public void setOperator(TokenType operator) {
        this.operator = operator;
    }

    public Object getPrimaryExpression() {
        return primaryExpression;
    }

    public void setPrimaryExpression(Object primaryExpression) {
        this.primaryExpression = primaryExpression;
    }

    public InstructionBlock generateInstructions() {
        return null;
    }
}
