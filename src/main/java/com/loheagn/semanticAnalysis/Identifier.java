package com.loheagn.semanticAnalysis;

public class Identifier {

    private IdentifierType type;
    private int offset; // 标识符在栈上的位置与当前层级BP指针的偏移
    private String name;
    private int level;  // 标识符处于的层级
    private Number value;   // 该标识符当前的值
    private boolean isConst;

    public Identifier(IdentifierType type, String name) {
        this.type = type;
        this.name = name;
    }

    public IdentifierType getType() {
        return type;
    }

    public void setType(IdentifierType type) {
        this.type = type;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Number getValue() {
        return value;
    }

    public void setValue(Number value) {
        this.value = value;
    }

    public boolean isConst() {
        return isConst;
    }

    public void setConst(boolean aConst) {
        isConst = aConst;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Identifier)) return false;
        Identifier identifier = (Identifier) obj;
        return identifier.getName().equals(name);
    }
}
