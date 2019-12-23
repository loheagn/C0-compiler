package com.loheagn.ast;

import com.loheagn.ast.statementAST.CompoundStatementAST;
import com.loheagn.semanticAnalysis.*;
import com.loheagn.tokenizer.TokenType;
import com.loheagn.utils.CompileException;

import java.util.ArrayList;
import java.util.List;

public class FunctionAST extends AST {

    private VariableType functionType;
    private String name;
    private List<Parameter> parameters = new ArrayList<Parameter>();
    private CompoundStatementAST compoundStatementAST;

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public void setFunctionType(VariableType variableType) {
        this.functionType = variableType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompoundStatementAST(CompoundStatementAST compoundStatementAST) {
        this.compoundStatementAST = compoundStatementAST;
    }

    public InstructionBlock generateInstructions() throws CompileException {
        Stack.isFunctionCompoundStatement = true;
        // 新的栈帧开始
        Stack.newStack();
        // 设置当前函数的返回类型
        CodeStack.functionType = functionType;
        // 代码段清空
        CodeStack.offset = 0;
        // 填函数表
        Function function = new Function();
        function.setName(new Parameter(functionType, name));
        for(Parameter parameter : parameters){
            function.addParameter(new Parameter(parameter.getVariableType(), parameter.getName()));
        }
        Table.addFunction(function);
        // 填常量表
        Table.addConst(new ConstIdentifier(name, TokenType.STRING));
        // 填变量表
        // 新的变量level层级
        Stack.newLevel();
        int offset = 0; // 参数相对于BP指针的偏移
        for(Parameter parameter:parameters){
            Variable variable = new Variable(parameter.getVariableType(), parameter.getName(),offset,1);
            if(parameter.getVariableType()== VariableType.DOUBLE) offset += Stack.doubleOffset;
            else offset += Stack.intOffset;
            Stack.push(parameter.getVariableType());
            if(parameter.isConst()) variable.setConst(true);
            else variable.setConst(false);
            Table.addVariable(variable);
        }
        InstructionBlock instructionBlock = new InstructionBlock();
        instructionBlock.addInstructionBlock(compoundStatementAST.generateInstructions());
        instructionBlock.addInstructionBlock(Blocks.returnBlockZero(function.getName().getVariableType()));
        return instructionBlock;
    }

    public List<String> instructionsToStringList() {
        List<String> result = new ArrayList<String>();
        result.add(name + ":");
        List<Instruction> instructionList = this.generateInstructions().getInstructions();
        for(int i = 0;i<instructionList.size();i++) {
            result.add("" + i + " " + instructionList.get(i).toString());
        }
        return result;
    }
}
