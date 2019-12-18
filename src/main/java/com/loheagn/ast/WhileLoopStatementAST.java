package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;

public class WhileLoopStatementAST extends LoopStatementAST {
    private ConditionAST conditionAST;
    private StatementAST statementAST;

    public ConditionAST getConditionAST() {
        return conditionAST;
    }

    public void setConditionAST(ConditionAST conditionAST) {
        this.conditionAST = conditionAST;
    }

    public StatementAST getStatementAST() {
        return statementAST;
    }

    public void setStatementAST(StatementAST statementAST) {
        this.statementAST = statementAST;
    }

    public InstructionBlock generateInstructions() {
        return null;
    }
}
