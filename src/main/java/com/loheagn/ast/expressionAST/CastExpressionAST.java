package com.loheagn.ast.expressionAST;

import com.loheagn.semanticAnalysis.Blocks;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.semanticAnalysis.VariableType;
import com.loheagn.tokenizer.TokenType;
import com.loheagn.utils.CompileException;

import java.util.List;

public class CastExpressionAST extends ExpressionAST {
    private List<TokenType> typeSpecifiers;
    private UnaryExpressionAST unaryExpressionAST;


    public InstructionBlock generateInstructions() throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        InstructionBlock expressionBlock = unaryExpressionAST.generateInstructions();
        instructionBlock.addInstructionBlock(expressionBlock);
        for (TokenType tokenType : typeSpecifiers) {
            VariableType variableType = VariableType.getVariableType(tokenType.getValue());
            instructionBlock.addInstructionBlock(Blocks.castTopType(instructionBlock.getType(), variableType));
        }
        return instructionBlock;
    }

    public void setTypeSpecifiers(List<TokenType> typeSpecifiers) {
        this.typeSpecifiers = typeSpecifiers;
    }

    public List<TokenType> getTypeSpecifiers() {
        return typeSpecifiers;
    }

    public UnaryExpressionAST getUnaryExpressionAST() {
        return unaryExpressionAST;
    }

    public void setUnaryExpressionAST(UnaryExpressionAST unaryExpressionAST) {
        this.unaryExpressionAST = unaryExpressionAST;
    }

}
