package com.loheagn.semanticAnalysis;

public class CodeStack {

    public static int offset = 0;   // 当前指令的位置与此函数第一条指令之间的差值

    public static VariableType functionType;    // 当前处理的函数的返回类型
}
