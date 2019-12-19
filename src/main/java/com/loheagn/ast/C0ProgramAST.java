package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.utils.CompileException;

import java.util.ArrayList;
import java.util.List;

public class C0ProgramAST extends AST {

    private List<VariableDeclarationAST> variableDeclarationASTList = new ArrayList<VariableDeclarationAST>();
    private List<FunctionAST> functionASTList = new ArrayList<FunctionAST>();

    public void addVariableDeclarationAST(VariableDeclarationAST variableDeclarationAST) {
        this.variableDeclarationASTList.add(variableDeclarationAST);
    }

    public void addVariableDeclarationASTList(List<VariableDeclarationAST> variableDeclarationASTList) {
        this.variableDeclarationASTList.addAll(variableDeclarationASTList);
    }

    public void addFunctionAST(FunctionAST functionAST) {
        this.functionASTList.add(functionAST);
    }

    public InstructionBlock generateInstructions() throws CompileException {
        return null;
    }
}
