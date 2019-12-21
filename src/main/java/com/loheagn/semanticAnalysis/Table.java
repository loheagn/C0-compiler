package com.loheagn.semanticAnalysis;

import com.loheagn.utils.CompileException;
import com.loheagn.utils.ExceptionString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Identifier {
    String name;
    int level;
    Object what;
    Identifier(String name, int level, Object what) {
        this.name = name;
        this.level = level;
        this.what  = what;
    }
}

public class Table {

//    private static List<Identifier> variableTable = new ArrayList<Identifier>();
    private static List<Function> functionTable = new ArrayList<Function>();
    private static List<ConstIdentifier> constTable = new ArrayList<ConstIdentifier>();

    private static List<Identifier> identifierTable = new ArrayList<Identifier>();

    /**
     * 向标识符表中插入一个变量
     * @param variable    要插入的变量
     */
    public static void addVariable(Variable variable) throws CompileException {
        for(int i = identifierTable.size()-1; i>=0; i--) {
            if(identifierTable.get(i).level == variable.getLevel() && variable.getName().equals(identifierTable.get(i).name)) throw new CompileException(ExceptionString.IdentifierDuplicateDefinition);
        }
        identifierTable.add(new Identifier(variable.getName(), variable.getLevel(), variable));
    }

    /**
     * 获取一个变量
     */
    public static Variable getVariable (String name) throws CompileException {
        for(int i = identifierTable.size()-1; i>=0; i--) {
            if(name.equals(identifierTable.get(i).name)){
                if(identifierTable.get(i).what instanceof Variable)
                    return (Variable) identifierTable.get(i).what;
                else throw new CompileException(ExceptionString.IdentifierNotDefined);
            }
        }
        throw new CompileException(ExceptionString.IdentifierNotDefined);
    }

    /**
     * 弹出当前变量表中所有最高层级的变量,用于退出一个大括号结构的时候使用
     */
    public static void popLocalVariables() {
        if(identifierTable.size()<=0) return;
        final int level = Stack.getLevel();
        identifierTable = identifierTable.stream().filter(identifier -> identifier.level==level).collect(Collectors.toList());
    }

    public static int addConst(ConstIdentifier constIdentifier) {
        constTable.add(constIdentifier);
        return constTable.size()-1;
    }

    public static int getConstIndex(Object o) throws CompileException {
        for(ConstIdentifier identifier:constTable){
            if(identifier.getValue().equals(o)) return constTable.indexOf(identifier);
        }
        throw new CompileException(ExceptionString.ConstNotFound);
    }

    public static void addFunction(Function function)throws CompileException {
        for(int i = identifierTable.size()-1; i>=0; i--) {
            if(identifierTable.get(i).level == 0 && function.getName().getName().equals(identifierTable.get(i).name)) throw new CompileException(ExceptionString.IdentifierDuplicateDefinition);
        }
        identifierTable.add(new Identifier(function.getName().getName(), 0, function));
    }

    public static Function getFunction(String name) throws CompileException {
        for(int i = identifierTable.size()-1;i>=0;i--) {
            if(identifierTable.get(i).name.equals(name)) {
                if(identifierTable.get(i).what instanceof  Function) return (Function)identifierTable.get(i).what;
                else throw new CompileException(ExceptionString.FunctionNotFound);
            }
        }
        throw new CompileException(ExceptionString.FunctionNotFound);
    }

    public static int getFunctionIndex(String name) throws CompileException {
        return functionTable.indexOf(getFunction(name));
    }

    public static List<String> generateConstTable(){
        List<String> result = new ArrayList<String>();
        result.add(".constants:");
        for(ConstIdentifier constIdentifier:constTable) {
            result.add(constTable.indexOf(constIdentifier)+" " + constIdentifier.toString());
        }
        return result;
    }

    public static List<String> generateFunctionTable () {
        List<String> result = new ArrayList<String>();
        result.add(".functions");
        for(Function function : functionTable) {
            result.add("" + functionTable.indexOf(function) + " " + getConstIndex(function.getName().getName()) + " " + function.toString());
        }
        return result;
    }
}
