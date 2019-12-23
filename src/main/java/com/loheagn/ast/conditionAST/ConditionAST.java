package com.loheagn.ast.conditionAST;

import com.loheagn.ast.AST;
import com.loheagn.ast.expressionAST.ExpressionAST;
import com.loheagn.semanticAnalysis.Blocks;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.semanticAnalysis.RelationOperatorType;
import com.loheagn.semanticAnalysis.VariableType;
import com.loheagn.utils.CompileException;

public class ConditionAST extends AST {
    private ExpressionAST expressionAST1;
    private ExpressionAST expressionAST2;
    private RelationOperatorType relationOperator;

    public InstructionBlock generateInstructions() throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        InstructionBlock expression1 = expressionAST1.generateInstructions();
        instructionBlock.addInstructionBlock(expression1);
        InstructionBlock expression2 = null;
        if(expressionAST2!=null){
            expression2 = expressionAST2.generateInstructions();
        } else {
            expression2 = Blocks.pushZero();
            relationOperator = RelationOperatorType.NOT_EQUAL;
        }
        // 首先选出类型转换的目标类型
        VariableType dstType = VariableType.getBiggerType(expression1.getType(), expression2.getType());
        // 把第一个表达式的结果进行类型转换
        instructionBlock.addInstructionBlock(Blocks.castTopType(expression1.getType(), dstType));
        // 第二个表达式
        instructionBlock.addInstructionBlock(expression2);
        instructionBlock.addInstructionBlock(Blocks.castTopType(expression2.getType(), dstType));
        instructionBlock.addInstructionBlock(Blocks.cmp(instructionBlock.getType()));
        instructionBlock.addInstructionBlock(Blocks.castTopType(instructionBlock.getType(), VariableType.INT));
        return instructionBlock;
    }

    public void setExpressionAST1(ExpressionAST expressionAST1) {
        this.expressionAST1 = expressionAST1;
    }

    public void setExpressionAST2(ExpressionAST expressionAST2) {
        this.expressionAST2 = expressionAST2;
    }

    public RelationOperatorType getRelationOperator() {
        return relationOperator;
    }

    public void setRelationOperator(RelationOperatorType relationOperator) {
        this.relationOperator = relationOperator;
    }

}
