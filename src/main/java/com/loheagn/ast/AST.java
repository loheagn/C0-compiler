package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.utils.CompileException;

public abstract class AST {

    public abstract InstructionBlock generateInstructions() throws CompileException;
}
