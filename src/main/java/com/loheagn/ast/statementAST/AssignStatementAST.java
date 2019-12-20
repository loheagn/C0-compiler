package com.loheagn.ast.statementAST;

import com.loheagn.ast.expressionAST.ExpressionAST;
import com.loheagn.ast.loopAST.ForUpdateExpressionAST;
import com.loheagn.semanticAnalysis.Blocks;
import com.loheagn.semanticAnalysis.Identifier;
import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.semanticAnalysis.Table;
import com.loheagn.utils.CompileException;
import com.loheagn.utils.ExceptionString;

public class AssignStatementAST extends ForUpdateExpressionAST {
    private String identifier;
    private ExpressionAST expressionAST;

    public InstructionBlock generateInstructions() throws CompileException {
        Identifier variable = Table.getVariable(identifier);
        if(variable.isConst()) throw new CompileException(ExceptionString.AssignToConst);
        InstructionBlock instructionBlock = Blocks.loadAddress(variable);
        instructionBlock.addInstructionBlock(expressionAST.generateInstructions());
        instructionBlock.addInstructionBlock(Blocks.castTopType(instructionBlock.getType(),variable.getType()));
        instructionBlock.addInstructionBlock(Blocks.storeVariable(variable.getType()));
        return instructionBlock;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ExpressionAST getExpressionAST() {
        return expressionAST;
    }

    public void setExpressionAST(ExpressionAST expressionAST) {
        this.expressionAST = expressionAST;
    }


}
