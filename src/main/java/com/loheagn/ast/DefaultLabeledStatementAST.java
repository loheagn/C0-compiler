package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;

public class DefaultLabeledStatementAST extends LabeledStatementAST {

    private StatementAST statementAST;

    public void setStatementAST(StatementAST statementAST){
        this.statementAST = statementAST;
    }

    public InstructionBlock generateInstructions() {
        return null;
    }
}
