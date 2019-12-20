package com.loheagn.ast.loopAST;

import com.loheagn.ast.statementAST.AssignStatementAST;
import com.loheagn.ast.statementAST.StatementAST;
import com.loheagn.ast.conditionAST.ConditionAST;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.utils.CompileException;

import java.util.ArrayList;
import java.util.List;

public class ForLoopStatementAST extends LoopStatementAST {
    List<AssignStatementAST> assignStatementASTList = new ArrayList<AssignStatementAST>();
    ConditionAST conditionAST = new ConditionAST();
    List<ForUpdateExpressionAST> forUpdateExpressionASTS = new ArrayList<ForUpdateExpressionAST>();
    StatementAST statementAST;

    public List<AssignStatementAST> getAssignStatementASTList() {
        return assignStatementASTList;
    }

    public void setAssignStatementASTList(List<AssignStatementAST> assignStatementASTList) {
        this.assignStatementASTList = assignStatementASTList;
    }

    public ConditionAST getConditionAST() {
        return conditionAST;
    }

    public void setConditionAST(ConditionAST conditionAST) {
        this.conditionAST = conditionAST;
    }

    public List<ForUpdateExpressionAST> getForUpdateExpressionASTS() {
        return forUpdateExpressionASTS;
    }

    public void setForUpdateExpressionASTS(List<ForUpdateExpressionAST> forUpdateExpressionASTS) {
        this.forUpdateExpressionASTS = forUpdateExpressionASTS;
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
