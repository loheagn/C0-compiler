package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.utils.CompileException;

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

    public InstructionBlock generateInstructions() throws CompileException {
        return null;
    }
}
