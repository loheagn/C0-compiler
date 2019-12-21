package com.loheagn.ast;

import com.loheagn.semanticAnalysis.Instruction;
import com.loheagn.semanticAnalysis.Table;
import com.loheagn.utils.CompileException;

import java.util.ArrayList;
import java.util.List;

public class C0ProgramAST{

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

    public List<String> generateInstructions() throws CompileException {
        // 先输出常量表
        List<String> instructions = new ArrayList<String>(Table.generateConstTable());
        // 然后是start指令
        instructions.add(".start:");
        int start_count = 0;
        for(VariableDeclarationAST variableDeclarationAST : variableDeclarationASTList) {
            List<Instruction> instructionList = variableDeclarationAST.generateInstructions().getInstructions();
            for(Instruction instruction : instructionList) {
                instructions.add(start_count + " " + instruction.toString());
                start_count ++;
            }
        }
        // 然后输出函数表
        instructions.addAll(Table.generateFunctionTable());
        // 接着输出一个个函数的具体的指令块
        for(FunctionAST functionAST : functionASTList){
            instructions.addAll(functionAST.instructionsToStringList());
        }
        return instructions;
    }
}
