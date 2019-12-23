package com.loheagn.ast.loopAST;

import com.loheagn.ast.conditionAST.ConditionAST;
import com.loheagn.ast.statementAST.StatementAST;
import com.loheagn.semanticAnalysis.Blocks;
import com.loheagn.semanticAnalysis.CodeStack;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.semanticAnalysis.Stack;
import com.loheagn.utils.CompileException;

public class WhileLoopStatementAST extends LoopStatementAST {
    private ConditionAST conditionAST;
    private StatementAST statementAST;

    public InstructionBlock generateInstructions() throws CompileException {
        int forOffset = CodeStack.offset;   // 也就是说,主体部分完成后,要跳转到这里重新执行条件语句判断执行
        InstructionBlock condition = null;
        InstructionBlock statement = null;
        if(conditionAST!=null) {
            condition = conditionAST.generateInstructions();
            Stack.pop(Stack.intOffset);
        }
        if(statementAST!=null) {
            CodeStack.offset ++;
            statement = statementAST.generateInstructions();
            CodeStack.offset --;
        }
        InstructionBlock instructionBlock = new InstructionBlock();
        if(condition!=null) {
            instructionBlock.addInstructionBlock(condition);
            instructionBlock.addInstructionBlock(Blocks.jumpNot(conditionAST.getRelationOperator(), CodeStack.offset + 2, instructionBlock.getType()));     // 这里加2是因为还有两条跳转指令没有生成
        } else {
            instructionBlock.addInstructionBlock(Blocks.nop());
        }
        if(statement!=null) {
            instructionBlock.addInstructionBlock(statement);
        }
        instructionBlock.addInstructionBlock(Blocks.jump(forOffset));
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
