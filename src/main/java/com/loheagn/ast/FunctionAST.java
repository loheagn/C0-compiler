package com.loheagn.ast;

import com.loheagn.semanticAnalysis.*;
import com.loheagn.tokenizer.TokenType;
import com.loheagn.utils.CompileException;

import java.util.ArrayList;
import java.util.List;

public class FunctionAST extends AST {

    private IdentifierType functionType;
    private String name;
    private List<Parameter> parameters = new ArrayList<Parameter>();
    private CompoundStatementAST compoundStatementAST;

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public void setFunctionType(IdentifierType identifierType) {
        this.functionType = identifierType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompoundStatementAST(CompoundStatementAST compoundStatementAST) {
        this.compoundStatementAST = compoundStatementAST;
    }

    public InstructionBlock generateInstructions() throws CompileException {
        // 填函数表
        Function function = new Function();
        function.setName(new Identifier(functionType, name));
        for(Parameter parameter : parameters){
            function.addParameter(new Identifier(parameter.getIdentifierType(), parameter.getName()));
        }
        Table.addFunction(function);
        // 填常量表
        Table.addConst(new ConstIdentifier(name, TokenType.STRING));
        // 填变量表
        int offset = 0; // 参数相对于BP指针的偏移
        for(Parameter parameter:parameters){
            Identifier identifier = new Identifier(parameter.getIdentifierType(), parameter.getName());
            if(parameter.isConst()) identifier.setConst(true);
            else identifier.setConst(false);
            if(identifier.getType()==IdentifierType.DOUBLE) offset += 2;
            else offset += 1;
            identifier.setOffset(offset);
            Table.addVariable(identifier);
        }
        InstructionBlock instructionBlock = new InstructionBlock();
        instructionBlock.addInstructionBlock(compoundStatementAST.generateInstructions());
        // 清除变量表
        Table.popLocalVariables();
        Stack.minusLevel(); // 变量层级降低一级
        return instructionBlock;
    }
}
