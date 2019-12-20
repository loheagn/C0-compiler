package com.loheagn.ast.loopAST;

import com.loheagn.ast.conditionAST.ConditionAST;
import com.loheagn.ast.statementAST.StatementAST;
import com.loheagn.semanticAnalysis.Blocks;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.utils.CompileException;

public class WhileLoopStatementAST extends LoopStatementAST {
    private ConditionAST conditionAST;
    private StatementAST statementAST;

    public InstructionBlock generateInstructions() throws CompileException {
        InstructionBlock condition = conditionAST.generateInstructions();
        InstructionBlock statement = statementAST.generateInstructions();
        InstructionBlock instructionBlock = new InstructionBlock();
        instructionBlock.addInstructionBlock(condition);
        instructionBlock.addInstructionBlock(Blocks.jumpNot(conditionAST.getRelationOperator(),statement.getInstructions().size()));
        instructionBlock.addInstructionBlock(statement);
        instructionBlock.addInstructionBlock(Blocks.jumpYes(conditionAST.getRelationOperator(), 0-statement.getInstructions().size()));
        return instructionBlock;
    }

    public ConditionAST getConditionAST() {
        return conditionAST;
    }

    public void setConditionAST(ConditionAST conditionAST) {
        this.conditionAST = conditionAST;
    }

    public StatementAST getStatementAST() {
        return statementAST;
    }

    public void setStatementAST(StatementAST statementAST) {
        this.statementAST = statementAST;
    }

}
