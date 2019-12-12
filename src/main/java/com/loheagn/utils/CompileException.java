package com.loheagn.utils;

/**
 * CompileException
 */
public class CompileException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -2463834329445053438L;

    private String message;
    private Position position;

    CompileException(String message, Position position) {
        this.message = message;
        this.position = position;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Row\t").append(position.row).append(", column\t").append(position.column).append(": ").append(message).toString();
    }

}