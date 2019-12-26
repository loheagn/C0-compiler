package com.loheagn.grammaAnalysis;

import com.loheagn.tokenizer.Token;
import com.loheagn.tokenizer.TokenType;

class JudgeToken {
    static boolean isTypeSpecifier(Token token) {
        TokenType type = token.getType();
        return type == TokenType.VOID || type == TokenType.CHAR || type == TokenType.DOUBLE || type == TokenType.INT;
    }

    static boolean isUnaryOperator(Token token) {
        TokenType type = token.getType();
        return type == TokenType.PLUS || type == TokenType.MINUS;
    }

    static boolean isAdditiveOperator(Token token) {
        return isUnaryOperator(token);
    }

    static boolean isMultiplicativeOperator(Token token) {
        TokenType type = token.getType();
        return type == TokenType.MULTI || type == TokenType.DIV;
    }

    static boolean isConstQualifier(Token token) {
        return token.getType() == TokenType.CONST;
    }

    static boolean isRelationOperator(Token token) {
        TokenType type = token.getType();
        return type == TokenType.LE || type == TokenType.GE || type == TokenType.GREATER || type == TokenType.LESS || type == TokenType.NEQ || type == TokenType.EQUAL;
    }
}
