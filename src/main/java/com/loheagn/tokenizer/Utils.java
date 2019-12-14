package com.loheagn.tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

enum DFAState {
    INITIAL_STATE, IDENTIFIER_STATE, DECIMAL_STATE, ZERO_STATE, DOT_STATE, MULTI_STATE, PLUS_STATE, DIV_STATE,
    MINUS_STATE, ASSGN_STATE, GREATER_STATE, LESS_STATE, EXCLAMATION_MARK_STATE, NON_EQUAL_STATE, SEMI_STATE,
    COMMA_STATE, LEFT_BRACE_STATE, RIGHT_BRACE_STATE, LEFT_PARE_STATE, RIGHT_PARE_STATE, COLON_STATE,
    STRING_VALUE_STATE, STRING_VALUE_ESCAPE_STATE, STRING_VALUE_EXCAPE_HEX_0_STATE, STRING_VALUE_EXCAPE_HEX_1_STATE,
    SINGLE_QUOTE_STATE, CHAR_VALUE_STATE, CHAR_EXCAPT_STATE, CHAR_EXCAPT_HEX_0_STATE, CHAR_EXCAPT_HEX_1_STATE,
    HEX_STATE, HEX_VAL_STATE, FLOAT_EXP_STATE, FLOAT_DOT_STATE, EQUAL_STATE, COMMENT_SINGLE_STATE, COM_MULTI_STATE,
    COM_MULTI_HALF_STATE
}

class JudgeChar {
    private static List<Character> validChars = new ArrayList<Character>(
            Arrays.asList('_', '(', ')', '[', ']', '{', '}', '<', '=', '>', '.', ',', ':', ';', '!', '?', '+', '-', '*',
                    '/', '%', '^', '&', '|', '~', '\\', '"', '\'', '`', '$', '#', '@'));

    static boolean isSpace(char currentChar) {
        return Character.isWhitespace(currentChar) || currentChar == '\n' || currentChar == '\r';
    }

    static boolean isNonDigit(char currentChar) {
        return Character.isLetter(currentChar);
    }

    static boolean isNonzeroDigit(char currentChar) {
        return Character.isDigit(currentChar) && currentChar != '0';
    }

    static boolean isDigit(char currentChar) {
        return Character.isDigit(currentChar);
    }

    static boolean isHexDigit(char currentChar) {
        return Character.isDigit(currentChar) || (currentChar >= 'a' && currentChar <= 'f')
                || (currentChar >= 'A' && currentChar <= 'F');
    }

    private static boolean isValid(char currentChar) {
        return Character.isWhitespace(currentChar) || Character.isLetter(currentChar)
                || Character.isDigit(currentChar) || validChars.indexOf(currentChar) != -1;
    }

    static boolean isCChar(char currentChar) {
        return isValid(currentChar) && currentChar != '"' && currentChar != '\\' && currentChar != '\n'
                && currentChar != '\r';
    }

    static boolean isSChar(char currentChar) {
        return isValid(currentChar) && currentChar != '\'' && currentChar != '\\' && currentChar != '\n'
                && currentChar != '\r';
    }

    static boolean isExpChar(char currentChar) {
        return currentChar == 'n' || currentChar == 'r' || currentChar == '\\' || currentChar == 't'
                || currentChar == '\'' || currentChar == '\"';
    }
}