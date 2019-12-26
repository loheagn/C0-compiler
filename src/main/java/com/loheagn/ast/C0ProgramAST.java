package com.loheagn.ast;

import com.loheagn.semanticAnalysis.Function;
import com.loheagn.semanticAnalysis.Instruction;
import com.loheagn.semanticAnalysis.Table;
import com.loheagn.utils.CompileException;
import com.loheagn.utils.ExceptionString;
import com.loheagn.utils.NumberToBytes;

import java.util.ArrayList;
import java.util.List;

public class C0ProgramAST {

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
        for (VariableDeclarationAST variableDeclarationAST : variableDeclarationASTList) {
            List<Instruction> instructionList = variableDeclarationAST.generateInstructions().getInstructions();
            for (Instruction instruction : instructionList) {
                startInstructions.add(start_count + " " + instruction.toString());
                start_count++;
            }
        }

        // 生成一个个函数的具体的指令块
        List<String> functionInstructions = new ArrayList<>();
        for (FunctionAST functionAST : functionASTList) {
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

    public List<Byte> generateInstructionsBytes() throws CompileException {
        List<Instruction> startInstructions = new ArrayList<>();
        for (VariableDeclarationAST variableDeclarationAST : variableDeclarationASTList) {
            startInstructions.addAll(variableDeclarationAST.generateInstructions().getInstructions());
        }
        List<Byte> startBytes = new ArrayList<>(NumberToBytes.numberToBytes(startInstructions.size(), 2));
        for (Instruction instruction : startInstructions) {
            startBytes.addAll(instruction.toBytes());
        }

        List<Byte> functionBytes = new ArrayList<>(NumberToBytes.numberToBytes(functionASTList.size(), 2));
        boolean hasMain = false;
        for (FunctionAST functionAST : this.functionASTList) {
            List<Instruction> functionInstructions = functionAST.generateInstructions().getInstructions();
            Function function = Table.getFunction(functionAST.getName());
            functionBytes.addAll(NumberToBytes.numberToBytes(Table.getConstIndex(function.getName().getName()), 2));
            functionBytes.addAll(NumberToBytes.numberToBytes(function.getParametersLength(), 2));
            functionBytes.addAll(NumberToBytes.numberToBytes(0x0001, 2));
            functionBytes.addAll(NumberToBytes.numberToBytes(functionInstructions.size(), 2));
            for (Instruction instruction : functionInstructions) {
                functionBytes.addAll(instruction.toBytes());
            }
            if (functionAST.getName().equals("main")) hasMain = true;
        }
        if (!hasMain) throw new CompileException(ExceptionString.NoMain);

        List<Byte> constBytes = new ArrayList<>(NumberToBytes.numberToBytes(Table.getConstTableSize(), 2));
        constBytes.addAll(Table.generateConstTableBytes());

        int magicNumber = 0x43303a29;
        int versionNumber = 0x00000001;

        // magic number
        List<Byte> result = new ArrayList<>(NumberToBytes.numberToBytes(magicNumber, 4));
        // version
        result.addAll(NumberToBytes.numberToBytes(versionNumber, 4));
        // const table
        result.addAll(constBytes);
        // start instructions
        result.addAll(startBytes);
        // function instructions
        result.addAll(functionBytes);
        return result;
    }
}
