package com.loheagn.ast.statementAST;

import com.loheagn.semanticAnalysis.Blocks;
import com.loheagn.semanticAnalysis.Identifier;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.semanticAnalysis.Table;
import com.loheagn.utils.CompileException;
import com.loheagn.utils.ExceptionString;

public class ScanStatementAST extends StatementAST {
    private String variable;

    public InstructionBlock generateInstructions() throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        Identifier identifier = Table.getVariable(variable);
        if(identifier.isConst()) throw new CompileException(ExceptionString.AssignToConst);
        instructionBlock.addInstructionBlock(Blocks.loadAddress(identifier));
        instructionBlock.addInstructionBlock(Blocks.scanValue(identifier.getType()));
        instructionBlock.addInstructionBlock(Blocks.storeVariable(identifier.getType()));
        return instructionBlock;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }
}
