package com.loheagn.ast.loopAST;

import com.loheagn.ast.StatementAST;
import com.loheagn.ast.conditionAST.ConditionAST;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.utils.CompileException;

public class DoLoopStatementAST extends LoopStatementAST {

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

    public InstructionBlock generateInstructions() throws CompileException {
        return null;
    }
}
