package com.loheagn.ast.expressionAST;

import com.loheagn.semanticAnalysis.Blocks;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.tokenizer.TokenType;
import com.loheagn.utils.CompileException;

import java.util.ArrayList;
import java.util.List;

public class MultiplicativeExpressionAST extends ExpressionAST {
    private List<TokenType> multiplicativeOperators = new ArrayList<TokenType>();
    private List<CastExpressionAST> castExpressionASTList = new ArrayList<CastExpressionAST>();

    public InstructionBlock generateInstructions() throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        if(castExpressionASTList == null || castExpressionASTList.size()<=0) return instructionBlock;
        InstructionBlock expression1 = castExpressionASTList.get(0).generateInstructions();
        instructionBlock.addInstructionBlock(expression1);
        InstructionBlock expression2;
        for(int i = 0;i<this.multiplicativeOperators.size();i++) {
            expression2 = castExpressionASTList.get(i+1).generateInstructions();
            instructionBlock.addInstructionBlock(Blocks.computeTwoExpressions(expression1, expression2, multiplicativeOperators.get(i)));
            // 准备下一次循环
            expression1 = expression2;
        }
        return instructionBlock;
    }

    public void addMultiplicativeOperator(TokenType operator) {
        this.multiplicativeOperators.add(operator);
    }

    public void addCastExpressionAST(CastExpressionAST castExpressionAST) {
        this.castExpressionASTList.add(castExpressionAST);
    }

    public List<TokenType> getMultiplicativeOperators() {
        return multiplicativeOperators;
    }

    public void setMultiplicativeOperators(List<TokenType> multiplicativeOperators) {
        this.multiplicativeOperators = multiplicativeOperators;
    }

    public List<CastExpressionAST> getCastExpressionASTList() {
        return castExpressionASTList;
    }

    public void setCastExpressionASTList(List<CastExpressionAST> castExpressionASTList) {
        this.castExpressionASTList = castExpressionASTList;
    }
}
