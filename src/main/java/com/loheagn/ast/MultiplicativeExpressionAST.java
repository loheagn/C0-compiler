package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.tokenizer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class MultiplicativeExpressionAST extends ExpressionAST {
    private List<TokenType> multiplicativeOperators = new ArrayList<TokenType>();
    private List<CastExpressionAST> castExpressionASTList = new ArrayList<CastExpressionAST>();

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

    public InstructionBlock generateInstructions() {
        return null;
    }
}
