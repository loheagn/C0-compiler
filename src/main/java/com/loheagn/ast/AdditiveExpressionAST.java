package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.tokenizer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class AdditiveExpressionAST extends ExpressionAST {
    private List<TokenType> operators = new ArrayList<TokenType>();
    private List<MultiplicativeExpressionAST> multiplicativeExpressionASTList = new ArrayList<MultiplicativeExpressionAST>();

    public void addOperator(TokenType tokenType) {
        operators.add(tokenType);
    }

    public void addMultiplicativeExpressionAST(MultiplicativeExpressionAST multiplicativeExpressionAST) {
        multiplicativeExpressionASTList.add(multiplicativeExpressionAST);
    }

    public List<TokenType> getOperators() {
        return operators;
    }

    public void setOperators(List<TokenType> operators) {
        this.operators = operators;
    }

    public List<MultiplicativeExpressionAST> getMultiplicativeExpressionASTList() {
        return multiplicativeExpressionASTList;
    }

    public void setMultiplicativeExpressionASTList(List<MultiplicativeExpressionAST> multiplicativeExpressionASTList) {
        this.multiplicativeExpressionASTList = multiplicativeExpressionASTList;
    }

    public InstructionBlock generateInstructions() {
        return null;
    }
}
