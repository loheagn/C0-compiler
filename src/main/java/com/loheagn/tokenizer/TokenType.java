package com.loheagn.tokenizer;

public enum TokenType {
    NON("~"), IDENTIFIER("~"), CONST("const"), VOID("void"), INT("int"), CHAR("char"), DOUBLE("double"),
    STRUCT("const"), IF("if"), ELSE("else"), SWITCH("switch"), CASE("case"), DEFAULT("default"), WHILE("while"),
    FOR("for"), DO("do"), RETURN("return"), BREAK("break"), CONTINUE("continue"), PRINT("print"), SCAN("scan"),
    INTEGER("0"), MULTI("*"), PLUS("+"), DIV("/"), MINUS("-"), ASSGN("="), EQUAL("=="), GREATER(">"), GE(">="),
    LESS("<"), LE("<="), NEQ("!="), SEMI(";"), COMMA(","), LEFT_BRACE("{"), RIGHT_BRACE("}"), LEFT_PARE("("),
    RIGHT_PARE(")"), COLON(":"), STRING("~~"), CHARACTER("`"), FLOAT("0.0"), COMMENT("//");

    private String value;

    TokenType(String value) {
        this.value = value;
    }

    public static TokenType judgeKeyWords(String value) {
        for (TokenType tokenType : TokenType.values()) {
            if (tokenType.getValue().equals(value)) {
                return tokenType;
            }
        }
        return NON;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
}