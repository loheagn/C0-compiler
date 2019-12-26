package com.loheagn.ast.statementAST;

import com.loheagn.semanticAnalysis.Blocks;
import com.loheagn.semanticAnalysis.Variable;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.semanticAnalysis.Table;
import com.loheagn.utils.CompileException;
import com.loheagn.utils.ExceptionString;

public class ScanStatementAST extends StatementAST {
    private String variable;

    public InstructionBlock generateInstructions() throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        Variable variable = Table.getVariable(this.variable);
        if (variable.isConst()) throw new CompileException(ExceptionString.AssignToConst);
        instructionBlock.addInstructionBlock(Blocks.loadAddress(variable));
        instructionBlock.addInstructionBlock(Blocks.scanValue(variable.getType()));
        instructionBlock.addInstructionBlock(Blocks.storeVariable(variable.getType()));
        return instructionBlock;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }
}
