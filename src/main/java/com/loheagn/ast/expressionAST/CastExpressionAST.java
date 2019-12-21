package com.loheagn.ast.expressionAST;

import com.loheagn.semanticAnalysis.Blocks;
import com.loheagn.semanticAnalysis.VariableType;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.tokenizer.TokenType;
import com.loheagn.utils.CompileException;

public class CastExpressionAST extends ExpressionAST {
    private TokenType typeSpecifiers;
    private UnaryExpressionAST unaryExpressionAST;


    public InstructionBlock generateInstructions() throws CompileException {
        VariableType type = VariableType.getVariableType(this.typeSpecifiers.getValue());
        InstructionBlock instructionBlock = new InstructionBlock();
        InstructionBlock expressionBlock = unaryExpressionAST.generateInstructions();
        instructionBlock.addInstructionBlock(Blocks.castTopType(expressionBlock.getType(), type));
        instructionBlock.setType(type);
        return instructionBlock;
    }

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

}
