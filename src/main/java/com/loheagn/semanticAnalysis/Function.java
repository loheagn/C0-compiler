package com.loheagn.semanticAnalysis;

import java.util.ArrayList;
import java.util.List;

public class Function {
    private Identifier name;
    private List<Identifier> parameters = new ArrayList<Identifier>();

    public void addParameter(Identifier parameter){
        parameter.setLevel(1);
        parameter.setOffset(parameters.size());
        this.parameters.add(parameter);
    }

    public Identifier getName() {
        return name;
    }

    public void setName(Identifier name) {
        this.name = name;
    }

    public List<Identifier> getParameters() {
        return parameters;
    }

    public void setParameters(List<Identifier> parameters) {
        this.parameters = parameters;
    }
}
