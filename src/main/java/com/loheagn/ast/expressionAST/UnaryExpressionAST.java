package com.loheagn.ast.expressionAST;

import com.loheagn.ast.statementAST.FunctionCallStatementAST;
import com.loheagn.semanticAnalysis.*;
import com.loheagn.tokenizer.TokenType;
import com.loheagn.utils.CompileException;
import com.loheagn.utils.ExceptionString;

public class UnaryExpressionAST extends ExpressionAST {
    private TokenType operator;
    private Object primaryExpression;

    public TokenType getOperator() {
        return operator;
    }

    public void setOperator(TokenType operator) {
        this.operator = operator;
    }

    public Object getPrimaryExpression() {
        return primaryExpression;
    }

    public void setPrimaryExpression(Object primaryExpression) {
        this.primaryExpression = primaryExpression;
    }

    public InstructionBlock generateInstructions() throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        InstructionBlock expression = new InstructionBlock();
        if(primaryExpression instanceof ExpressionAST) {
            expression = ((ExpressionAST) primaryExpression).generateInstructions();
        } else if(primaryExpression instanceof String) {
            expression = Blocks.loadIdentifier((String)primaryExpression);
        } else if(primaryExpression instanceof Integer) {
            expression.addInstruction(new Instruction(OperationType.ipush, (Integer)primaryExpression, null));
            Stack.push(Stack.intOffset);
            expression.setType(IdentifierType.INT);
        } else if(primaryExpression instanceof Character) {
            expression.addInstruction(new Instruction(OperationType.ipush, new Integer((Character)primaryExpression),null));
            expression.addInstruction(new Instruction(OperationType.i2c,null, null));
            Stack.push(Stack.intOffset);
            expression.setType(IdentifierType.CHAR);
        } else if(primaryExpression instanceof Double) {
            int index = Table.addConst(new ConstIdentifier(primaryExpression,TokenType.DOUBLE));
            expression.addInstruction(new Instruction(OperationType.loadc, index, null));
            Stack.push(Stack.doubleOffset);
            expression.setType(IdentifierType.DOUBLE);
        } else{
            assert primaryExpression instanceof FunctionCallStatementAST;
            expression = ((FunctionCallStatementAST) primaryExpression).generateInstructions();
            if(expression.getType() == IdentifierType.VOID) throw new CompileException(ExceptionString.ComputeVoid);
        }
        instructionBlock.addInstructionBlock(expression);
        instructionBlock.setType(expression.getType());
        if(operator == TokenType.MINUS) {
            if(instructionBlock.getType() == IdentifierType.DOUBLE) instructionBlock.addInstruction(new Instruction(OperationType.dneg,null,null));
            else instructionBlock.addInstruction(new Instruction(OperationType.ineg, null,null));
        }
        return instructionBlock;
    }
}
