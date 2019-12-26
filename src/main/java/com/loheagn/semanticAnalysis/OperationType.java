package com.loheagn.semanticAnalysis;

public enum OperationType {
    iPush("ipush", 4, 0, 0x02), i2c("i2c", 0, 0, 0x62), i2d("i2d", 0, 0, 0x60), d2i("d2i", 0, 0, 0x61), iadd("iadd", 0, 0, 0x30), dadd("dadd", 0, 0, 0x31), isub("isub", 0, 0, 0x34), dsub("dsub", 0, 0, 0x35), imul("imul", 0, 0, 0x38), dmul("dmul", 0, 0, 0x39), idiv("idiv", 0, 0, 0x3c), ddiv("ddiv", 0, 0, 0x3d), ineg("ineg", 0, 0, 0x40), dneg("dneg", 0, 0, 0x41), loada("loada", 2, 4, 0x0a), loadc("loadc", 2, 0, 0x09), iload("iload", 0, 0, 0x10), dload("dload", 0, 0, 0x11), aload("aload", 0, 0, 0x12), ipush("ipush", 4, 0, 0x02), dstore("dstore", 0, 0, 0x21), astore("astore", 0, 0, 0x22), istore("istore", 0, 0, 0x20), call("call", 2, 0, 0x80), iprint("iprint", 0, 0, 0xa0), cprint("cprint", 0, 0, 0xa2), dprint("dprint", 0, 0, 0xa1), sprint("sprint", 0, 0, 0xa3), printl("printl", 0, 0, 0xaf), iscan("iscan", 0, 0, 0xb0), dscan("dscan", 0, 0, 0xb1), cscan("cscan", 0, 0, 0xb2), je("je", 2, 0, 0x71), jne("jne", 2, 0, 0x72), jl("jl", 2, 0, 0x73), jge("jge", 2, 0, 0x74), jg("jg", 2, 0, 0x75), jle("jle", 2, 0, 0x76), jmp("jmp", 2, 0, 0x70), iret("iret", 0, 0, 0x89), dret("dret", 0, 0, 0x8a), aret("cret", 0, 0, 0x8b), ret("ret", 0, 0, 0x88), nop("nop", 0, 0, 0x00), icmp("icmp", 0, 0, 0x44), dcmp("dcmp", 0, 0, 0x45), snew("snew", 4, 0, 0x0c), pop("pop", 0, 0, 0x04), pop2("pop2", 0, 0, 0x05);
    private String value;
    private int length1;
    private int length2;
    private byte byteValue;

    OperationType(String value, int length1, int length2, int byteValue) {
        this.value = value;
        this.length1 = length1;
        this.length2 = length2;
        this.byteValue = (byte) byteValue;
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

    public int getLength1() {
        return length1;
    }

    public void setLength1(int length1) {
        this.length1 = length1;
    }

    public int getLength2() {
        return length2;
    }

    public void setLength2(int length2) {
        this.length2 = length2;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public void setByteValue(byte byteValue) {
        this.byteValue = byteValue;
    }
}
