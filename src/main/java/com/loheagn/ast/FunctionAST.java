package com.loheagn.ast;

import com.loheagn.semanticAnalysis.Instruction;
import com.loheagn.semanticAnalysis.Parameter;
import com.loheagn.semanticAnalysis.VariableType;

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

    public List<Instruction> generateInstructions() {
        return null;
    }
}
