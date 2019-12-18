package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;

import java.util.ArrayList;
import java.util.List;

public class CompoundStatementAST extends StatementAST {

    private List<VariableDeclarationAST> variableDeclarationASTList = new ArrayList<VariableDeclarationAST>();
    private List<StatementAST> statementASTList = new ArrayList<StatementAST>();

    public void addVariableDeclarationAST(VariableDeclarationAST variableDeclarationAST){
        this.variableDeclarationASTList.add(variableDeclarationAST);
    }

    public void addStatementASTList(StatementAST statementAST) {
        this.statementASTList.add(statementAST);
    }

    public InstructionBlock generateInstructions() {
        return null;
    }
}
