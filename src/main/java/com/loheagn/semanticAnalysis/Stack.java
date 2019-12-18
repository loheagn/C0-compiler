package com.loheagn.semanticAnalysis;

/**
 * Stack 运行时栈描述
 */
public class Stack {

    private static int BP = 0;
    private static int SP = 0;

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
     * 获取当前变量的位置
     * @return
     */
    public static int getOffset() {
        return SP-BP;
    }

    /**
     * 压栈操作
     */
    public static void push() {
        SP++;
    }

    public static int getLevel() {
        return level;
    }
}
