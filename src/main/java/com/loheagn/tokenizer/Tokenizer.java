package com.loheagn.tokenizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.loheagn.utils.Position;
import com.loheagn.utils.CompileException;
import com.loheagn.utils.ExceptionString;

/**
 * Tokenizer
 */
public class Tokenizer {

    private Position currentPosition;
    private List<String> content;

    private void readFile(BufferedReader reader) throws CompileException {
        content = new ArrayList<String>();
        try {
            String line = reader.readLine();
            while (line != null) {
                content.add(line + "\n");
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new CompileException(ExceptionString.ReadFile, currentPosition);
        }
    }

    private Character nextChar() {
        currentPosition.column++;
        if (currentPosition.column > content.get(currentPosition.row).length() - 1) {
            currentPosition.column = 0;
            currentPosition.row++;
            if (currentPosition.row > content.size() - 1)
                return null;
        }
        return content.get(currentPosition.row).charAt(currentPosition.column);
    }

    private void unreadChar() {
        currentPosition.column--;
        if (currentPosition.column < 0) {
            if (currentPosition.row == 0)
                return;
            currentPosition.row--;
            currentPosition.column = content.get(currentPosition.row).length() - 1;
        }
    }

    private Token dealWithIdentifier(StringBuilder builder, Position position, Position currentPosition) {
        String value = builder.toString();
        TokenType tokenType = TokenType.judgeKeyWords(value);
        if (tokenType == TokenType.NON)
            tokenType = TokenType.IDENTIFIER;
        return new Token(tokenType, value, position, currentPosition);
    }

    private Token dealWithDecimalInteger(StringBuilder builder, Position position, Position currentPosition) {
        int value;
        try {
            value = Integer.parseInt(builder.toString());
        } catch (NumberFormatException e) {
            value = Integer.MAX_VALUE;
        }
        return new Token(TokenType.INTEGER, value, position, currentPosition);
    }

    private Token dealWithHexInteger(StringBuilder builder, Position position, Position currentPosition) {
        int value;
        try {
            value = Integer.parseInt(builder.toString(), 16);
        } catch (NumberFormatException e) {
            value = Integer.MAX_VALUE;
        }
        return new Token(TokenType.INTEGER, value, position, currentPosition);
    }

    private Token dealWithFloat(StringBuilder builder, Position position, Position currentPosition) {
        return new Token(TokenType.FLOAT, Float.parseFloat(builder.toString()), position, currentPosition);
    }

    private Token nextToken() throws CompileException {
        DFAState state = DFAState.INITIAL_STATE;
        // 记录当前读到的字符
        StringBuilder builder = new StringBuilder();
        // 记录token开始的位置
        Position position = null;
        while (true) {
            // 当前读取到的字符
            Character currentChar = nextChar();
            switch (state) {
            case INITIAL_STATE: {
                if (currentChar == null)
                    return null; // 已经读到文件尾,返回空
                else if (JudgeChar.isSpace(currentChar))
                    state = DFAState.INITIAL_STATE;
                else if (JudgeChar.isNonDigit(currentChar))
                    state = DFAState.IDENTIFIER_STATE;
                else if (JudgeChar.isNonzeroDigit(currentChar))
                    state = DFAState.DECIMAL_STATE;
                else {
                    switch (currentChar) {
                        case '0':
                            state = DFAState.ZERO_STATE;
                            break;
                        case '.':
                            state = DFAState.DOT_STATE;
                            break;
                        case '*':
                            state = DFAState.MULTI_STATE;
                            break;
                        case '+':
                            state = DFAState.PLUS_STATE;
                            break;
                        case '/':
                            state = DFAState.DIV_STATE;
                            break;
                        case '-':
                            state = DFAState.MINUS_STATE;
                            break;
                        case '=':
                            state = DFAState.ASSGN_STATE;
                            break;
                        case '>':
                            state = DFAState.GREATER_STATE;
                            break;
                        case '<':
                            state = DFAState.LESS_STATE;
                            break;
                        case '!':
                            state = DFAState.EXCLAMATION_MARK_STATE;
                            break;
                        case ';':
                            state = DFAState.SEMI_STATE;
                            break;
                        case ',':
                            state = DFAState.COMMA_STATE;
                            break;
                        case '{':
                            state = DFAState.LEFT_BRACE_STATE;
                            break;
                        case '}':
                            state = DFAState.RIGHT_BRACE_STATE;
                            break;
                        case '(':
                            state = DFAState.LEFT_PARE_STATE;
                            break;
                        case ')':
                            state = DFAState.RIGHT_PARE_STATE;
                            break;
                        case ':':
                            state = DFAState.COLON_STATE;
                            break;
                        case '"':
                            state = DFAState.STRING_VALUE_STATE;
                            break;
                        case '\'':
                            state = DFAState.SINGLE_QUOTE_STATE;
                            break;
                        default:
                            throw new CompileException(ExceptionString.IllegalInput, currentPosition);
                    }
                }
                if (state != DFAState.INITIAL_STATE) {
                    position = currentPosition;
                    builder.append(currentChar);
                }
                break;
            }
            case IDENTIFIER_STATE: {
                if (currentChar == null) {
                    return dealWithIdentifier(builder, position, currentPosition);
                } else {
                    if (JudgeChar.isDigit(currentChar) || JudgeChar.isNonDigit(currentChar)) {
                        builder.append(currentChar);
                    } else {
                        unreadChar();
                        return dealWithIdentifier(builder, position, currentPosition);
                    }
                }
                break;
            }
            case ZERO_STATE: {
                if (currentChar == null) {
                    return new Token(TokenType.INTEGER, 0, position, currentPosition);
                } else if (currentChar == 'x' || currentChar == 'X') {
                    builder.append(currentChar);
                    state = DFAState.HEX_STATE;
                } else if (currentChar == 'e' || currentChar == 'E') {
                    builder.append(currentChar);
                    state = DFAState.FLOAT_EXP_STATE;
                } else if (currentChar == '.') {
                    builder.append(currentChar);
                    state = DFAState.FLOAT_DOT_STATE;
                } else {
                    unreadChar();
                    return new Token(TokenType.INTEGER,0, position, currentPosition);
                }
                break;
            }
            case DOT_STATE: {
                if (currentChar == null) {
                    throw new CompileException(ExceptionString.IllegalInput, currentPosition);
                } else if (JudgeChar.isDigit(currentChar)) {
                    builder.append(currentPosition);
                    state = DFAState.FLOAT_DOT_STATE;
                } else {
                    unreadChar();
                    throw new CompileException(ExceptionString.IllegalInput, currentPosition);
                }
                break;
            }
            case DECIMAL_STATE: {
                if (currentChar == null) {
                    return dealWithDecimalInteger(builder, position, currentPosition);
                } else if (JudgeChar.isDigit(currentChar)) {
                    state = DFAState.DECIMAL_STATE;
                    builder.append(currentChar);
                } else if (currentChar == '.') {
                    state = DFAState.FLOAT_DOT_STATE;
                    builder.append(currentChar);
                } else if (currentChar == 'e' || currentChar == 'E') {
                    state = DFAState.FLOAT_EXP_STATE;
                    builder.append(currentChar);
                } else {
                    unreadChar();
                    return dealWithDecimalInteger(builder, position, currentPosition);
                }
                break;
            }
            case MULTI_STATE: {
                unreadChar();
                return new Token(TokenType.MULTI, "*", position, currentPosition);
            }
            case PLUS_STATE: {
                unreadChar();
                return new Token(TokenType.PLUS, "+", position, currentPosition);
            }
            case MINUS_STATE: {
                unreadChar();
                return new Token(TokenType.MINUS, "-", position, currentPosition);
            }
            case DIV_STATE: {
                if (currentChar == null) {
                    return new Token(TokenType.DIV, "/", position, currentPosition);
                } else if (currentChar == '/') {
                    state = DFAState.COMMENT_SINGLE_STATE;
                    builder.append('/');
                } else if (currentChar == '*') {
                    state = DFAState.MULTI_STATE;
                    builder.append('*');
                } else {
                    unreadChar();
                    return new Token(TokenType.DIV,"/", position, currentPosition);
                }
                break;
            }
            case ASSGN_STATE: {
                if (currentChar == null) {
                    return new Token(TokenType.ASSGN,"=", position, currentPosition);
                } else if (currentChar == '=') {
                    return new Token(TokenType.EQUAL, "==", position, currentPosition);
                } else {
                    unreadChar();
                    return new Token(TokenType.ASSGN, "=", position, currentPosition);
                }
            }
            case GREATER_STATE: {
                if (currentChar == null) {
                    return new Token(TokenType.GREATER, ">", position, currentPosition);
                } else if (currentChar == '=') {
                    return new Token(TokenType.GE, ">=", position, currentPosition);
                } else {
                    unreadChar();
                    return new Token(TokenType.GREATER, ">", position, currentPosition);
                }
            }
            case LESS_STATE: {
                if (currentChar == null) {
                    return new Token(TokenType.LESS, "<", position, currentPosition);
                } else if (currentChar == '=') {
                    return new Token(TokenType.LE, "<=", position, currentPosition);
                } else {
                    unreadChar();
                    return new Token(TokenType.LESS, "<", position, currentPosition);
                }
            }
            case EXCLAMATION_MARK_STATE: {
                if (currentChar == null || currentChar != '=') {
                    throw new CompileException(ExceptionString.IllegalInput, currentPosition);
                } else {
                    return new Token(TokenType.NEQ, "!=", position, currentPosition);
                }
            }
            case SEMI_STATE: {
                unreadChar();
                return new Token(TokenType.SEMI,";", position, currentPosition);
            }
            case COMMA_STATE: {
                unreadChar();
                return new Token(TokenType.COMMA, ",", position, currentPosition);
            }
            case LEFT_BRACE_STATE: {
                unreadChar();
                return new Token(TokenType.LEFT_BRACE, "{", position, currentPosition);
            }
            case RIGHT_BRACE_STATE: {
                unreadChar();
                return new Token(TokenType.RIGHT_BRACE, "}", position, currentPosition);
            }
            case LEFT_PARE_STATE: {
                unreadChar();
                return new Token(TokenType.LEFT_PARE, "(", position, currentPosition);
            }
            case RIGHT_PARE_STATE: {
                unreadChar();
                return new Token(TokenType.RIGHT_PARE, ")", position, currentPosition);
            }
            case COLON_STATE: {
                unreadChar();
                return new Token(TokenType.COLON, ":", position, currentPosition);
            }
            case STRING_VALUE_STATE: {
                if (currentChar == null) {
                    throw new CompileException(ExceptionString.IllegalInput, currentPosition);
                } else if (JudgeChar.isSChar(currentChar)) {
                    state = DFAState.STRING_VALUE_STATE;
                    builder.append(currentChar);
                } else if (currentChar == '\\') {
                    state = DFAState.STRING_VALUE_ESCAPE_STATE;
                    builder.append(currentChar);
                } else if (currentChar == '"') {
                    return new Token(TokenType.STRING, builder.toString(), position, currentPosition);
                } else {
                    throw new CompileException(ExceptionString.IllegalInput, currentPosition);
                }
                break;
            }
            case STRING_VALUE_ESCAPE_STATE: {
                if (currentChar == null) {
                    throw new CompileException(ExceptionString.IllegalInput, currentPosition);
                } else if (JudgeChar.isExpChar(currentChar)) {
                    state = DFAState.STRING_VALUE_STATE;
                    builder.append(currentChar);
                } else if (currentChar == 'x') {
                    state = DFAState.STRING_VALUE_EXCAPE_HEX_0_STATE;
                    builder.append(currentChar);
                } else {
                    throw new CompileException(ExceptionString.IllegalInput, currentPosition);
                }
                break;
            }
            case STRING_VALUE_EXCAPE_HEX_0_STATE: {
                if (currentChar == null || !JudgeChar.isHexDigit(currentChar)) {
                    throw new CompileException(ExceptionString.IllegalInput, currentPosition);
                } else {
                    state = DFAState.STRING_VALUE_EXCAPE_HEX_1_STATE;
                    builder.append(currentChar);
                }
                break;
            }
            case STRING_VALUE_EXCAPE_HEX_1_STATE: {
                if (currentChar == null || !JudgeChar.isHexDigit(currentChar)) {
                    throw new CompileException(ExceptionString.IllegalInput, currentPosition);
                } else {
                    state = DFAState.STRING_VALUE_STATE;
                    builder.append(currentChar);
                }
                break;
            }
            case SINGLE_QUOTE_STATE: {
                if (currentChar == null) {
                    throw new CompileException(ExceptionString.IllegalInput, currentPosition);
                } else if (JudgeChar.isCChar(currentChar)) {
                    state = DFAState.CHAR_VALUE_STATE;
                    builder.append(currentChar);
                } else if (currentChar == '\\') {
                    state = DFAState.CHAR_EXCAPT_STATE;
                } else {
                    throw new CompileException(ExceptionString.IllegalInput, currentPosition);
                }
                break;
            }
            case CHAR_VALUE_STATE: {
                if (currentChar == null || currentChar != '\'') {
                    throw new CompileException(ExceptionString.IllegalInput, currentPosition);
                } else {
                    return new Token(TokenType.CHARACTER, builder.charAt(0), position, currentPosition);
                }
            }
            case CHAR_EXCAPT_STATE: {
                if (currentChar == null) {
                    throw new CompileException(ExceptionString.IllegalInput, currentPosition);
                } else if (JudgeChar.isExpChar(currentChar)) {
                    state = DFAState.CHAR_VALUE_STATE;
                    if (currentChar == 'n')
                        builder.append('\n');
                    else if (currentChar == 'r')
                        builder.append('\r');
                    else if (currentChar == '\\')
                        builder.append('\\');
                    else if (currentChar == 't')
                        builder.append('\t');
                    else if (currentChar == '\'')
                        builder.append('\'');
                    else
                        builder.append('\"');
                } else if (currentChar == 'x') {
                    state = DFAState.CHAR_EXCAPT_HEX_0_STATE;
                } else {
                    throw new CompileException(ExceptionString.IllegalInput, currentPosition);
                }
                break;
            }
            case CHAR_EXCAPT_HEX_0_STATE: {
                if (currentChar == null || !JudgeChar.isHexDigit(currentChar)) {
                    throw new CompileException(ExceptionString.IllegalInput, currentPosition);
                } else {
                    builder.append(currentChar);
                    state = DFAState.CHAR_EXCAPT_HEX_1_STATE;
                }
                break;
            }
            case CHAR_EXCAPT_HEX_1_STATE: {
                if (currentChar == null || !JudgeChar.isHexDigit(currentChar)) {
                    throw new CompileException(ExceptionString.IllegalInput, currentPosition);
                } else {
                    builder.append(currentChar);
                    builder = new StringBuilder().append((char) Integer.parseInt(builder.toString(), 16));
                    state = DFAState.CHAR_VALUE_STATE;
                }
                break;
            }
            case HEX_STATE: {
                if (currentChar == null || !JudgeChar.isHexDigit(currentChar)) {
                    throw new CompileException(ExceptionString.IllegalInput, currentPosition);
                } else {
                    state = DFAState.HEX_VAL_STATE;
                    builder = new StringBuilder().append(currentChar);
                }
                break;
            }
            case HEX_VAL_STATE: {
                if (currentChar == null) {
                    return dealWithHexInteger(builder, position, currentPosition);
                } else if (JudgeChar.isHexDigit(currentChar)) {
                    builder.append(currentChar);
                    state = DFAState.HEX_VAL_STATE;
                } else {
                    unreadChar();
                    return dealWithHexInteger(builder, position, currentPosition);
                }
                break;
            }
            case FLOAT_DOT_STATE: {
                if (currentChar == null) {
                    return dealWithFloat(builder, position, currentPosition);
                } else if (JudgeChar.isDigit(currentChar)) {
                    state = DFAState.FLOAT_DOT_STATE;
                    builder.append(currentChar);
                } else if (currentChar == 'e' || currentChar == 'E') {
                    state = DFAState.FLOAT_EXP_STATE;
                    builder.append(currentChar);
                } else {
                    unreadChar();
                    return dealWithFloat(builder, position, currentPosition);
                }
                break;
            }
            case FLOAT_EXP_STATE: {
                if (currentChar == null) {
                    throw new CompileException(ExceptionString.IllegalInput, currentPosition);
                } else {
                    switch (currentChar) {
                    case '+':
                    case '-':
                        builder.append(currentChar);
                    default: {
                        if (JudgeChar.isDigit(currentChar)) {
                            builder.append(currentChar);
                            currentChar = nextChar();
                            while (currentChar != null && JudgeChar.isDigit(currentChar)) {
                                builder.append(currentChar);
                                currentChar = nextChar();
                            }
                            unreadChar();
                            return dealWithFloat(builder, position, currentPosition);
                        } else {
                            throw new CompileException(ExceptionString.IllegalInput, currentPosition);
                        }
                    }
                    }
                }
            }
            case COMMENT_SINGLE_STATE: {
                if (currentChar == null || currentChar == '\n' || currentChar == '\r')
                    return null;
                else {
                    state = DFAState.COMMENT_SINGLE_STATE;
                }
                break;
            }
            case COM_MULTI_STATE: {
                if (currentChar == null) {
                    throw new CompileException(ExceptionString.IllegalComment, currentPosition);
                } else if (currentChar == '*') {
                    state = DFAState.COM_MULTI_HALF_STATE;
                } else {
                    state = DFAState.COM_MULTI_STATE;
                }
                break;
            }
            case COM_MULTI_HALF_STATE: {
                if (currentChar == null) {
                    throw new CompileException(ExceptionString.IllegalComment, currentPosition);
                } else if (currentChar == '/') {
                    return null;
                } else {
                    state = DFAState.COM_MULTI_STATE;
                }
                break;
            }
            case EQUAL_STATE: {
                unreadChar();
                return new Token(TokenType.EQUAL, "==", position, currentPosition);
            }
            case NON_EQUAL_STATE: {
                unreadChar();
                return new Token(TokenType.NEQ,"!=", position, currentPosition);
            }
                default:
                    throw new IllegalStateException("Unexpected value: " + state);
            }
        }
    }

    public List<Token> getAllTokens(String fileName) throws CompileException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            readFile(reader);
        } catch (IOException e) {
            throw new CompileException(ExceptionString.OpenFile, currentPosition);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new CompileException(ExceptionString.CloseFile, currentPosition);
                }
            }
        }
        List<Token> tokens = new ArrayList<Token>();
        Token token = nextToken();
        while (token != null) {
            tokens.add(token);
            token = nextToken();
        }
        return tokens;
    }

}