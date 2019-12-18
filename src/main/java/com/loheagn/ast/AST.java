package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;

public abstract class AST {

    public abstract InstructionBlock generateInstructions();
}
