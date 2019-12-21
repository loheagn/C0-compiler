package com.loheagn.semanticAnalysis;

public enum OperationType {
    iPush("ipush",4,0), i2c("i2c", 0,0), i2d("i2d", 0,0), d2i("d2i", 0,0), iadd("iadd",0,0), dadd("dadd", 0,0), isub("isub", 0,0), dsub("dsub", 0,0), imul("imul", 0,0), dmul("dmul", 0,0), idiv("idiv", 0,0), ddiv("ddiv",0,0), ineg("ineg", 0,0), dneg("dneg", 0,0), loada("loada", 2,4), loadc("loadc", 2,0), iload("iload",0,0),dload("dload",0,0), aload("aload", 0,0),ipush("ipush",4,0), dstore("dstore", 0,0), astore("astore", 0,0), istore("istore",0,0), call("call", 2,0), iprint("iprint",0,0), cprint("cprint", 0,0), dprint("dprint",0,0),sprint("sprint",0,0), iscan("iscan", 0,0), dscan("dscan",0,0), cscan("cscan",0,0),je("je",2,0),jne("jne",2,0), jl("jl", 2,0),jge("jge", 2,0), jg("jg",2,0), jle("jle",2,0), jmp("jmp",2,0),iret("iret",0,0),dret("dret",0,0), cret("cret",0,0), ret("ret",0,0);
    private String value;
    private int length1;
    private int length2;

    OperationType(String value, int length1, int length2) {
        this.value = value;
        this.length1 = length1;
        this.length2 = length2;
    }

    public String getValue() {
        return value;
    }

    public int getLength() {
        return length1 + length2 + 1;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
