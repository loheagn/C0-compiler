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

    public CompileException(ExceptionString message) {
        this.message = message;
        this.position = new Position(0,0);
    }

    @Override
    public String toString() {
        return "ERROR: Row\t" + position.row + ", column\t" + position.column + ": " + message.getMessage();
    }

}