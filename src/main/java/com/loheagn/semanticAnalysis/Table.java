package com.loheagn.semanticAnalysis;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private static List<Identifier> variableTable = new ArrayList<Identifier>();
    private static List<Function> functionTable = new ArrayList<Function>();
    private static List<ConstIdentifier> constTable = new ArrayList<ConstIdentifier>();

    /**
     * 向变量表中插入一个变量
     * @param identifier    要插入的变量
     * @return  如果变量表中没有重复的,也就是说之前没有定义过 则说明插入是成功的,否则返回假
     */
    public static boolean addVariable(Identifier identifier) {
        for(int i=variableTable.size()-1;i>=0;i--) {
            if(variableTable.get(i).getLevel() != identifier.getLevel()) break;
            else if(variableTable.get(i).getName().equals(identifier.getName())) return false;
        }
        identifier.setOffset(Stack.getOffset());
        identifier.setLevel(Stack.getLevel());
        variableTable.add(identifier);
        return true;
    }

    /**
     * 获取一个变量的地址,也就是该变量相对于当前栈帧BP指针的偏移
     */
    public static Integer getVariableAddress (String name) {
        for(int i = variableTable.size()-1;i>=0;i--){
            if(variableTable.get(i).getName().equals(name)) return variableTable.get(i).getOffset();
        }
        return null;
    }

    /**
     * 弹出当前变量表中所有最高层级的变量,用于退出一个大括号结构的时候使用
     */
    public static void popLocalVariables() {
        if(variableTable.size()<=0) return;
        int level = Stack.getLevel();
        for(int i = variableTable.size()-1;i>=0;i--) {
            if(variableTable.get(i).getLevel() == level) variableTable.remove(i);
        }
    }

    public static int addConst(ConstIdentifier constIdentifier) {
        constTable.add(constIdentifier);
        return constTable.size()-1;
    }

    public static int getConstIndex(Object o) {
        for(ConstIdentifier identifier:constTable){
            if(identifier.getValue().equals(o)) return constTable.indexOf(identifier);
        }
        return -1;
    }

    public static boolean addFunction(Function function){
        for(Function function1:functionTable) {
            if(function.getName().getName().equals(function1.getName().getName())) return false;
        }
        functionTable.add(function);
        return true;
    }

    public static int getFunctionIndex(String name) {
        for(Function function:functionTable) {
            if(function.getName().getName().equals(name)) return functionTable.indexOf(function);
        }
        return -1;
    }
}
