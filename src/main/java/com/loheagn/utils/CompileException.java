package com.loheagn.utils;

enum ExceptionString {
    EOF("Reach the end of the file."), OpenFile("Open file error."), ReadFile("Read file error.");

    ExceptionString(String message) {
        this.message = message;
    }

    private String message;

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}

/**
 * CompileException
 */
public class CompileException {

}