package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;

import java.util.ArrayList;
import java.util.List;

public class PrintStatementAST extends StatementAST {
    private List<Object> printList = new ArrayList<Object>();

    public void addPrintUnit(Object o) {
        printList.add(o);
    }

    public List<Object> getPrintList() {
        return printList;
    }

    public void setPrintList(List<Object> printList) {
        this.printList = printList;
    }

    public InstructionBlock generateInstructions() {
        return null;
    }
}
