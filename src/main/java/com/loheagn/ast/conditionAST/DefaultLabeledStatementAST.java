package com.loheagn.ast.conditionAST;

import com.loheagn.ast.statementAST.StatementAST;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.utils.CompileException;

public class DefaultLabeledStatementAST extends LabeledStatementAST {

    private StatementAST statementAST;

    public void setStatementAST(StatementAST statementAST){
        this.statementAST = statementAST;
    }

    public InstructionBlock generateInstructions() throws CompileException {
        return null;
    }
}
