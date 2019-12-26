package com.loheagn.ast.statementAST;

import com.loheagn.ast.VariableDeclarationAST;
import com.loheagn.semanticAnalysis.Blocks;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.semanticAnalysis.Stack;
import com.loheagn.semanticAnalysis.Table;
import com.loheagn.utils.CompileException;

import java.util.ArrayList;
import java.util.List;

public class CompoundStatementAST extends StatementAST {

    private List<VariableDeclarationAST> variableDeclarationASTList = new ArrayList<VariableDeclarationAST>();
    private List<StatementAST> statementASTList = new ArrayList<StatementAST>();

    public InstructionBlock generateInstructions() throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        // new level
        if (!Stack.isFunctionCompoundStatement) {
            Stack.newLevel();
            Stack.isFunctionCompoundStatement = false;
        } else {
            Stack.isFunctionCompoundStatement = false;
        }
        for (VariableDeclarationAST variableDeclarationAST : variableDeclarationASTList) {
            instructionBlock.addInstructionBlock(variableDeclarationAST.generateInstructions());
        }
        for (StatementAST statementAST : statementASTList) {
            instructionBlock.addInstructionBlock(statementAST.generateInstructions());
        }
        // 清空本级作用域的变量表
        Table.popLocalVariables();
        // 释放栈上的内存
        for (VariableDeclarationAST variableDeclarationAST : variableDeclarationASTList) {
            instructionBlock.addInstructionBlock(Blocks.free(variableDeclarationAST.getType()));
        }
        Stack.minusLevel();
        return instructionBlock;
    }

    public void addVariableDeclarationAST(VariableDeclarationAST variableDeclarationAST) {
        this.variableDeclarationASTList.add(variableDeclarationAST);
    }

    public void addVariableDeclarationASTList(List<VariableDeclarationAST> variableDeclarationASTList) {
        this.variableDeclarationASTList.addAll(variableDeclarationASTList);
    }

    public void addStatementASTList(StatementAST statementAST) {
        this.statementASTList.add(statementAST);
    }
}
