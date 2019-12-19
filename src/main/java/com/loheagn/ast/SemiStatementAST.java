package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.utils.CompileException;

public class SemiStatementAST extends StatementAST {
    public InstructionBlock generateInstructions() throws CompileException {
        return new InstructionBlock();
    }
}
