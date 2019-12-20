package com.loheagn.ast.conditionAST;

import com.loheagn.ast.statementAST.StatementAST;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.utils.CompileException;

public class IfConditionStatementAST extends ConditionStatementAST {
    private ConditionAST conditionAST;
    private StatementAST ifStatementAST;
    private StatementAST elseStatementAST;

    public ConditionAST getConditionAST() {
        return conditionAST;
    }

    public void setConditionAST(ConditionAST conditionAST) {
        this.conditionAST = conditionAST;
    }

    public StatementAST getIfStatementAST() {
        return ifStatementAST;
    }

    public void setIfStatementAST(StatementAST ifStatementAST) {
        this.ifStatementAST = ifStatementAST;
    }

    public StatementAST getElseStatementAST() {
        return elseStatementAST;
    }

    public void setElseStatementAST(StatementAST elseStatementAST) {
        this.elseStatementAST = elseStatementAST;
    }

    public InstructionBlock generateInstructions() throws CompileException {
        return null;
    }
}
