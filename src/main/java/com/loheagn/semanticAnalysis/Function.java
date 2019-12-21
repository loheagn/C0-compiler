package com.loheagn.semanticAnalysis;

import java.util.ArrayList;
import java.util.List;

public class Function {
    private Parameter name;
    private List<Parameter> parameters = new ArrayList<Parameter>();

    public void addParameter(Parameter parameter){
        this.parameters.add(parameter);
    }

    public Parameter getName() {
        return name;
    }

    public void setName(Parameter name) {
        this.name = name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        int parameters_count = 0;
        for(Parameter parameter : parameters) {
            if(parameter.getVariableType() == VariableType.DOUBLE) parameters_count += Stack.doubleOffset;
            else parameters_count += Stack.intOffset;
        }
        return parameters_count + " 1";
    }
}
