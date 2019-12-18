package com.loheagn.ast;

import com.loheagn.semanticAnalysis.InstructionBlock;
import com.loheagn.semanticAnalysis.Parameter;
import com.loheagn.semanticAnalysis.IdentifierType;

import java.util.ArrayList;
import java.util.List;

public class FunctionAST extends AST {

    private IdentifierType functionType;
    private String name;
    private List<Parameter> parameters = new ArrayList<Parameter>();
    private CompoundStatementAST compoundStatementAST;

    //todo

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

    public InstructionBlock generateInstructions() {
        return null;
    }
}
