package com.loheagn.ast.conditionAST;

import com.loheagn.ast.statementAST.StatementAST;
import com.loheagn.semanticAnalysis.Blocks;
import com.loheagn.semanticAnalysis.CodeStack;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.semanticAnalysis.Stack;
import com.loheagn.utils.CompileException;

public class IfConditionStatementAST extends ConditionStatementAST {
    private ConditionAST conditionAST;
    private StatementAST ifStatementAST;
    private StatementAST elseStatementAST;

    public InstructionBlock generateInstructions() throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        instructionBlock.addInstructionBlock(conditionAST.generateInstructions());
        Stack.pop(instructionBlock.getType());
        CodeStack.offset++;
        InstructionBlock ifStatementBlock = ifStatementAST.generateInstructions();
        CodeStack.offset--;
        instructionBlock.addInstructionBlock(Blocks.jumpNot(conditionAST.getRelationOperator(), CodeStack.offset + 2, instructionBlock.getType()));
        instructionBlock.addInstructionBlock(ifStatementBlock);
        if (elseStatementAST != null) {
            CodeStack.offset++;
            InstructionBlock elseBlock = elseStatementAST.generateInstructions();
            CodeStack.offset--;
            instructionBlock.addInstructionBlock(Blocks.jump(CodeStack.offset + 1));
            instructionBlock.addInstructionBlock(elseBlock);
        } else {
            instructionBlock.addInstructionBlock(Blocks.nop());
        }
        return instructionBlock;
    }

    public ConditionAST getConditionAST() {
        return conditionAST;
    }

    public void setConditionAST(ConditionAST conditionAST) {
        this.conditionAST = conditionAST;
    }

    public StatementAST getIfStatementAST() {
        return ifStatementAST;
    }

    public void setIfStatementAST(StatementAST ifStatementAST) {
        this.ifStatementAST = ifStatementAST;
    }

    public StatementAST getElseStatementAST() {
        return elseStatementAST;
    }

    public void setElseStatementAST(StatementAST elseStatementAST) {
        this.elseStatementAST = elseStatementAST;
    }

}
