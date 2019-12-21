package com.loheagn.ast.jumpAST;

import com.loheagn.ast.expressionAST.ExpressionAST;
import com.loheagn.semanticAnalysis.Blocks;
import com.loheagn.semanticAnalysis.CodeStack;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.semanticAnalysis.VariableType;
import com.loheagn.utils.CompileException;
import com.loheagn.utils.ExceptionString;

public class ReturnJumpStatementAST extends JumpStatementAST {
    private ExpressionAST expressionAST;

    public InstructionBlock generateInstructions() throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        if(expressionAST!=null) {
            if(CodeStack.functionType == VariableType.VOID) throw new CompileException(ExceptionString.ReturnTypeError);
            instructionBlock.addInstructionBlock(expressionAST.generateInstructions());
            instructionBlock.addInstructionBlock(Blocks.castTopType(instructionBlock.getType(),CodeStack.functionType));
        } else {
            if(CodeStack.functionType!=VariableType.VOID) throw new CompileException(ExceptionString.ReturnTypeError);
        }
        instructionBlock.addInstructionBlock(Blocks.returnBlock(CodeStack.functionType));
        return expressionAST.generateInstructions();
    }

    public ExpressionAST getExpressionAST() {
        return expressionAST;
    }

    public void setExpressionAST(ExpressionAST expressionAST) {
        this.expressionAST = expressionAST;
    }

}
