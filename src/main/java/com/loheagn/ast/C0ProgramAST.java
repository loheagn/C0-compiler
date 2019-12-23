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

    public List<String> generateInstructionsString() throws CompileException {
        // 然后是start指令
        List<String> startInstructions = new ArrayList<>();
        startInstructions.add(".start:");
        int start_count = 0;
        for(VariableDeclarationAST variableDeclarationAST : variableDeclarationASTList) {
            List<Instruction> instructionList = variableDeclarationAST.generateInstructions().getInstructions();
            for(Instruction instruction : instructionList) {
                startInstructions.add(start_count + " " + instruction.toString());
                start_count ++;
            }
        }

        // 生成一个个函数的具体的指令块
        List<String> functionInstructions = new ArrayList<>();
        for(FunctionAST functionAST : functionASTList){
            functionInstructions.addAll(functionAST.instructionsToStringList());
        }
        // 然后输出函数表
        List<String> functionTableList = new ArrayList<>(Table.generateFunctionTable());

        List<String> instructions = new ArrayList<>(Table.generateConstTable());
        instructions.addAll(startInstructions);
        instructions.addAll(functionTableList);
        instructions.addAll(functionInstructions);
        return instructions;
    }

    public List<Byte[]> generateInstructionsBytes() throws CompileException {
    return null;
    }
}
