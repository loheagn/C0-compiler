package com.loheagn.ast;

import com.loheagn.ast.expressionAST.ExpressionAST;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.utils.CompileException;

import java.util.ArrayList;
import java.util.List;

public class SwitchConditionStatementAST extends ConditionStatementAST {
    private ExpressionAST expressionAST;
    private List<LabeledStatementAST> labeledStatementASTList = new ArrayList<LabeledStatementAST>();

    public void addLabeledStatementAST(LabeledStatementAST labeledStatementAST){
        this.labeledStatementASTList.add(labeledStatementAST);
    }

    public void setExpressionAST(ExpressionAST expressionAST) {
        this.expressionAST = expressionAST;
    }

    public InstructionBlock generateInstructions() throws CompileException {
        return null;
    }
}
