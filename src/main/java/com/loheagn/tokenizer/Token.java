package com.loheagn.tokenizer;

import com.loheagn.utils.Position;

/**
 * Token
 */
public class Token {

    private TokenType type;
    private Object value;
    private Position startPosition;
    private Position endPosition;

    public Token(TokenType type, Object value, Position startPosition, Position endPosition) {
        this.type = type;
        this.value = value;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public Token() {
    }

    public String getStringValue(){
        return value.toString();
    }

    /**
     * @param endPosition the endPosition to set
     */
    public void setEndPosition(Position endPosition) {
        this.endPosition = endPosition;
    }

    /**
     * @param startPosition the startPosition to set
     */
    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * @param type the type to set
     */
    public void setType(TokenType type) {
        this.type = type;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * @return the endPosition
     */
    public Position getEndPosition() {
        return endPosition;
    }

    /**
     * @return the startPosition
     */
    public Position getStartPosition() {
        return startPosition;
    }

    /**
     * @return the type
     */
    public TokenType getType() {
        return type;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

}