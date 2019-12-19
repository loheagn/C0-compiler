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
}
