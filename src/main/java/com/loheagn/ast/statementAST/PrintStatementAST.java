package com.loheagn.ast.statementAST;

import com.loheagn.ast.expressionAST.ExpressionAST;
import com.loheagn.semanticAnalysis.*;
import com.loheagn.tokenizer.TokenType;
import com.loheagn.utils.CompileException;

import java.util.ArrayList;
import java.util.List;

public class PrintStatementAST extends StatementAST {
    private List<Object> printList = new ArrayList<Object>();

    public InstructionBlock generateInstructions() throws CompileException {
        InstructionBlock instructionBlock = new InstructionBlock();
        for(Object object:printList){
            if(object instanceof ExpressionAST) {
                instructionBlock.addInstructionBlock(((ExpressionAST) object).generateInstructions());
                instructionBlock.addInstructionBlock(Blocks.printValue(instructionBlock.getType()));
            } else {
                instructionBlock.addInstruction(new Instruction(OperationType.loadc, Table.addConst(new ConstIdentifier((String) object, TokenType.STRING)), null));
                Stack.push(Stack.intOffset);
                instructionBlock.addInstructionBlock(Blocks.printString());
            }
        }
        return instructionBlock;
    }

    public void addPrintUnit(Object o) {
        printList.add(o);
    }

    public List<Object> getPrintList() {
        return printList;
    }

    public void setPrintList(List<Object> printList) {
        this.printList = printList;
    }

}
