package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;

public class SemiStatementAST extends StatementAST {
    public InstructionBlock generateInstructions() {
        return new InstructionBlock();
    }
}
