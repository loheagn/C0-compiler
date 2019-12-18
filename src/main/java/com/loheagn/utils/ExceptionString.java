package com.loheagn.utils;

public enum ExceptionString {
    EOF("Reach the end of the file."), OpenFile("Open file error."), ReadFile("Read file error."),
    CloseFile("Close file error"), IllegalInput("Illegal input error."), IntegerTooBig("The integer is too big."),
    IllegalComment("Illegal comment error."), FloatTooBig("The float number is too big."),
    FloatTooSmall("The float number is too small."), VariableDeclaration("VariableDeclaration error!"), NoSemi("Missing semicolon."), FunctionIncomplete("Function definition is incomplete."), MissingLeftBrace("Missing {."), MissingRightBrace("Missing }."), AssignStatementIncomplete("Assignment statement is incomplete"), InvalidStatement("Invalid statement."), OtherSyntaxError("Unknown syntax error."), NoIf("Missing <if>."), NoLeftPare("Missing (."), NoRightPare("Missing )."), NoSwitch("Missing <switch>."), NoCase("Missing <case>."), CaseIncomplete("Case statement is incomplete."), NoColon("Missing <:>."), NODefault("Missing <default>."), NoWhile("Missing <while>."),NoDo("Missing <do>."), NoFor("Missing <for>."),ForIncomplete("The for loop statement is incomplete."), NoForUpdateStatement("Missing for update statement."),NoIdentifier("Missing identifier."), PrintIncomplete("The print statement is incomplete."), FunctionCallIncomplete("The function call is incomplete."), CastToVoid("Can't cast to void."), CastFromVoid("Can't cast from void."), ExpressionIncomplete("The expression is incomplete.");

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