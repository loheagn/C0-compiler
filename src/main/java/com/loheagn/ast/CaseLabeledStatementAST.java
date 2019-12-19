package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.utils.CompileException;

public class CaseLabeledStatementAST extends LabeledStatementAST {

    private int value;
    private StatementAST statementAST;

    public void setValue(int value) {
        this.value = value;
    }

    public void setStatementAST(StatementAST statementAST) {
        this.statementAST = statementAST;
    }

    public InstructionBlock generateInstructions() throws CompileException {
        return null;
    }
}
