package com.loheagn.ast.expressionAST;

import com.loheagn.semanticAnalysis.Blocks;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.tokenizer.TokenType;
import com.loheagn.utils.CompileException;

import java.util.ArrayList;
import java.util.List;

public class AdditiveExpressionAST extends ExpressionAST {
    private List<TokenType> operators = new ArrayList<TokenType>();
    private List<MultiplicativeExpressionAST> multiplicativeExpressionASTList = new ArrayList<MultiplicativeExpressionAST>();

    public InstructionBlock generateInstructions() throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        if(multiplicativeExpressionASTList == null || multiplicativeExpressionASTList.size()<=0) return instructionBlock;
       InstructionBlock expression1 = multiplicativeExpressionASTList.get(0).generateInstructions();
       instructionBlock.addInstructionBlock(expression1);
       InstructionBlock expression2;
       for(int i = 0;i<this.operators.size();i++) {
           expression2 = multiplicativeExpressionASTList.get(i+1).generateInstructions();
           // 为了简便,我们对操作符左右两个表达式的结果都进行类型转换
           instructionBlock.addInstructionBlock(Blocks.computeTwoExpressions(expression1, expression2, operators.get(i)));
           // 准备下一次循环
           expression1 = expression2;
       }
        return instructionBlock;
    }

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
}
