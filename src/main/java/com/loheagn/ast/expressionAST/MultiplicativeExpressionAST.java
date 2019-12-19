package com.loheagn.ast.expressionAST;

import com.loheagn.semanticAnalysis.Blocks;
import com.loheagn.semanticAnalysis.IdentifierType;
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
            // 为了简便,我们对操作符左右两个表达式的结果都进行类型转换
            // 首先选出类型转换的目标类型
            IdentifierType dstType = IdentifierType.getBiggerType(expression1.getType(), expression2.getType());
            // 把第一个表达式的结果进行类型转换
            instructionBlock.addInstructionBlock(Blocks.castTopType(expression1.getType(), dstType));
            // 第二个表达式
            instructionBlock.addInstructionBlock(expression2);
            instructionBlock.addInstructionBlock(Blocks.castTopType(expression2.getType(), dstType));
            // 计算两个表达式
            instructionBlock.addInstructionBlock(Blocks.computeTwoExpressions(multiplicativeOperators.get(i), dstType));
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
