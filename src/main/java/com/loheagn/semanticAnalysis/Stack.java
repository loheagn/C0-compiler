package com.loheagn.semanticAnalysis;

import com.loheagn.utils.Position;

/**
 * Stack 运行时栈描述
 */
public class Stack {

    public final static int doubleOffset = 2;

    public final static int intOffset = 1;

    public static boolean isFunctionCompoundStatement = false;

    private static int BP = 0;
    private static int SP = 0;

    public static Position position = new Position(0,0);

    private static int level = 0;

    /**
     * 创建一个新的栈帧,这时候需要把BP和SP都置为0
     */
    public static void newStack() {
        BP=SP = 0;
    }

    /**
     * 新开始一个大括号结构,变量的层级要提升
     */
    public static void newLevel() {
        level++;
    }

    /**
     * 递减level
     */
    public static void minusLevel() {
        level--;
    }

    /**
     * 获取当前变量的位置
     */
    public static int getOffset() {
        return SP-BP;
    }

    /**
     * 压栈操作
     */
    public static void push(int offset) {
        SP += offset;
    }

    public static void pop(int offset) {
        SP -= offset;
    }

    public static void pop(VariableType variableType) {
        if(variableType == VariableType.VOID) pop(0);
        else if(variableType == VariableType.DOUBLE) pop(doubleOffset);
        else pop(intOffset);
    }

    public static void push(VariableType type) {
        if(type == VariableType.VOID) push(0);
        else if(type == VariableType.DOUBLE) push(doubleOffset);
        else push(intOffset);
    }

    public static int getLevel() {
        return level;
    }

    public static void setOffset(int preSP) {
        SP = preSP;
    }
}
