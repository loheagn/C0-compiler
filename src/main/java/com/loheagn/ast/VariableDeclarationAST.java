package com.loheagn.ast;

import com.loheagn.semanticAnalysis.Instruction;

import java.util.ArrayList;
import java.util.List;

public class VariableDeclarationAST extends AST {

    private boolean isConst = false;

    private List<String> identifiers = new ArrayList<String>();

    private ExpressionAST expressionAST = new ExpressionAST();

    public void addIdentifier(String identifier) {
        this.identifiers.add(identifier);
    }

    public void setConst() {
        this.isConst = true;
    }

    public void setExpressionAST(ExpressionAST expressionAST) {
        this.expressionAST = expressionAST;
    }

    public List<Instruction> generateInstructions() {
        return null;
    }
}
