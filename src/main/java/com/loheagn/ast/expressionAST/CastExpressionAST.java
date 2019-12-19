package com.loheagn.ast.expressionAST;

import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.tokenizer.TokenType;
import com.loheagn.utils.CompileException;

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

    public InstructionBlock generateInstructions() throws CompileException {
        return null;
    }
}
