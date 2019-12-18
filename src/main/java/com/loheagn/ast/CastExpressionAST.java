package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.tokenizer.TokenType;

public class CastExpressionAST extends ExpressionAST {
    private TokenType typeSpecifiers;
    private UnaryExpressionAST unaryExpressionAST;

    public TokenType getTypeSpecifiers() {
        return typeSpecifiers;
    }

    public void setTypeSpecifiers(TokenType typeSpecifiers) {
        this.typeSpecifiers = typeSpecifiers;
    }

    public UnaryExpressionAST getUnaryExpressionAST() {
        return unaryExpressionAST;
    }

    public void setUnaryExpressionAST(UnaryExpressionAST unaryExpressionAST) {
        this.unaryExpressionAST = unaryExpressionAST;
    }

    public InstructionBlock generateInstructions() {
        return null;
    }
}
