package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.utils.CompileException;

public class ScanStatementAST extends StatementAST {
    private String variable;

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public InstructionBlock generateInstructions() throws CompileException {
        return null;
    }
}
