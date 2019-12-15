package com.loheagn.ast;

import com.loheagn.semanticAnalysis.Instruction;

import java.util.ArrayList;
import java.util.List;

public class SemiStatementAST extends StatementAST {
    public List<Instruction> generateInstructions() {
        return new ArrayList<Instruction>();
    }
}
