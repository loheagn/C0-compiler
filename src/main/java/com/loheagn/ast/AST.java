package com.loheagn.ast;

import com.loheagn.semanticAnalysis.Instruction;

import java.util.List;

public abstract class AST {

    public abstract List<Instruction> generateInstructions();
}
