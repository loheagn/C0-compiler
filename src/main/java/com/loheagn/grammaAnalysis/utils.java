package com.loheagn.grammaAnalysis;

import com.loheagn.tokenizer.Token;
import com.loheagn.tokenizer.TokenType;

class JudgeToken {
    static boolean isTypeSpecifier(Token token) {
        TokenType type = token.getType();
        return type == TokenType.VOID || type == TokenType.CHAR || type == TokenType.DOUBLE || type == TokenType.INT;
    }

    static boolean isConstQualifier(Token token) {
        return token.getType() == TokenType.CONST;
    }
}
