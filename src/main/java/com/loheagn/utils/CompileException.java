package com.loheagn.utils;

/**
 * CompileException
 */
public class CompileException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -2463834329445053438L;

    private ExceptionString message;
    private Position position;

    public CompileException(ExceptionString message, Position currentPosition) {
        this.message = message;
        this.position = currentPosition;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Row\t").append(position.row).append(", column\t").append(position.column).append(": ").append(message.getMessage()).toString();
    }

}