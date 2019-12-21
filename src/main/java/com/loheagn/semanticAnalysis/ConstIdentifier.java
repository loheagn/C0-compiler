package com.loheagn.semanticAnalysis;

import com.loheagn.tokenizer.TokenType;

public class ConstIdentifier {
    private Object value;
    private TokenType type;

    public ConstIdentifier(Object value, TokenType type) {
        this.value = value;
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        if(type== TokenType.STRING) {
            return "S " + "\""+ value.toString() +"\"";
        } else if(type == TokenType.DOUBLE) {
            return "D " + "0x"+ Long.toHexString(Double.doubleToLongBits((Double) value));
        } else {
            return "I " + "0x"+ Long.toHexString((Integer) value);
        }
    }
}
