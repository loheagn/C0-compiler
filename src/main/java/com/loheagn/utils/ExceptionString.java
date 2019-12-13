package com.loheagn.utils;

public enum ExceptionString {
    EOF("Reach the end of the file."), OpenFile("Open file error."), ReadFile("Read file error."),
    CloseFile("Close file error"), IllegalInput("Illegal input error."), IntegerTooBig("The integer is too big."),
    IllegalComment("Illegal comment error."), FloatTooBig("The float number is too big."),
    FloatTooSmall("The float number is too small.");

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