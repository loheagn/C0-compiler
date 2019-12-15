package com.loheagn.grammaAnalysis;

import com.loheagn.ast.*;
import com.loheagn.semanticAnalysis.Parameter;
import com.loheagn.semanticAnalysis.VariableType;
import com.loheagn.tokenizer.Token;
import com.loheagn.tokenizer.TokenType;
import com.loheagn.utils.CompileException;
import com.loheagn.utils.ExceptionString;
import com.loheagn.utils.Position;

import java.util.ArrayList;
import java.util.List;

public class GrammaAnalyser {
    private List<Token> tokens;
    private int tokenIndex;
    private Position position;

    /**
     * <C0-program> ::=
     * {<variable-declaration>}{<function-definition>}
     *
     * @return 返回整个C0程序对应的总的那棵语法树
     */
    private C0ProgramAST C0Program() throws CompileException {
        C0ProgramAST c0ProgramAST = new C0ProgramAST();
        // 首先是判断多条变量声明的循环
        while (true) {
            Token token = nextToken();
            // 如果是null, 说明已经读到了token序列的末尾,那么直接返回现在已经识别的结果就可以了
            if (token == null) return c0ProgramAST;
            else if (JudgeToken.isConstQualifier(token)) {
                unreadToken();
                c0ProgramAST.addVariableDeclarationAST(variableDeclaration());
            } else if (JudgeToken.isTypeSpecifier(token)) {
                token = nextToken();
                if (token == null || token.getType() != TokenType.IDENTIFIER)
                    throw new CompileException(ExceptionString.VariableDeclaration, position);
                else {
                    token = nextToken();
                    if (token != null && token.getType() == TokenType.LEFT_PARE) {
                        unreadToken();
                        unreadToken();
                        unreadToken();
                        break;
                    } else {
                        unreadToken();
                        unreadToken();
                        unreadToken();
                        c0ProgramAST.addVariableDeclarationAST(variableDeclaration());
                    }
                }
            } else {
                throw new CompileException(ExceptionString.VariableDeclaration, position);
            }
        }
        while (true) {
            FunctionAST functionAST = function();
            if (functionAST == null) break;
            c0ProgramAST.addFunctionAST(functionAST);
        }
        return c0ProgramAST;
    }

    /**
     * <variable-declaration> ::=
     * [<const-qualifier>]<type-specifier><init-declarator-list>';'
     * <init-declarator-list> ::=
     * <init-declarator>{','<init-declarator>}
     * <init-declarator> ::=
     * <identifier>[<initializer>]
     * <initializer> ::=
     * '='<expression>
     *
     * @return 返回相应类型的语法树
     */
    private VariableDeclarationAST variableDeclaration() throws CompileException {
        VariableDeclarationAST variableDeclarationAST = new VariableDeclarationAST();
        Token token = nextToken();
        if (token == null) {
            return variableDeclarationAST;
        } else if (JudgeToken.isConstQualifier(token)) {
            variableDeclarationAST.setConst();
            token = nextToken();
            if (token == null) throw new CompileException(ExceptionString.VariableDeclaration, position);
        }
        if (!JudgeToken.isTypeSpecifier(token))
            throw new CompileException(ExceptionString.VariableDeclaration, position);
        variableDeclarationAST.addIdentifier(token.getStringValue());
        while (true) {
            token = nextToken();
            if (token == null) throw new CompileException(ExceptionString.NoSemi, position);
            else if (token.getType() == TokenType.SEMI) return variableDeclarationAST;
            else if (token.getType() == TokenType.ASSGN) break;
            else if (token.getType() == TokenType.IDENTIFIER)
                variableDeclarationAST.addIdentifier(token.getStringValue());
            else throw new CompileException(ExceptionString.NoSemi, position);
        }
        variableDeclarationAST.setExpressionAST(expression());
        token = nextToken();
        if (token == null || token.getType() != TokenType.SEMI)
            throw new CompileException(ExceptionString.NoSemi, position);
        return variableDeclarationAST;
    }

    /**
     * <function-definition> ::=
     * <type-specifier><identifier><parameter-clause><compound-statement>
     *
     * @return 返回相应类型的语法树
     */
    private FunctionAST function() throws CompileException {
        FunctionAST functionAST = new FunctionAST();
        Token token = nextToken();
        if (token == null || !JudgeToken.isTypeSpecifier(token)) {
            throw new CompileException(ExceptionString.FunctionIncomplete, position);
        }
        functionAST.setFunctionType(VariableType.getVariableType(token.getStringValue()));
        token = nextToken();
        if (token == null || token.getType() != TokenType.IDENTIFIER) {
            throw new CompileException(ExceptionString.FunctionIncomplete, position);
        }
        functionAST.setName(token.getStringValue());
        functionAST.setParameters(parameterClause());
        functionAST.setCompoundStatementAST(compoundStatement());
        return functionAST;
    }

    /**
     * <parameter-clause> ::=
     * '(' [<parameter-declaration-list>] ')'
     * <parameter-declaration-list> ::=
     * <parameter-declaration>{','<parameter-declaration>}
     * <parameter-declaration> ::=
     * [<const-qualifier>]<type-specifier><identifier>
     *
     * @return 返回解析到的参数列表, 元素在列表中的位置就是其在参数声明中的次序
     **/
    private List<Parameter> parameterClause() throws CompileException {
        List<Parameter> parameters = new ArrayList<Parameter>();
        Token token = nextToken();
        if (token == null || token.getType() != TokenType.LEFT_PARE) {
            throw new CompileException(ExceptionString.FunctionIncomplete, position);
        }
        token = nextToken();
        if (token == null) throw new CompileException(ExceptionString.FunctionIncomplete, position);
        else if (token.getType() == TokenType.RIGHT_PARE) return parameters;
        else if (JudgeToken.isTypeSpecifier(token) || JudgeToken.isConstQualifier(token)) {
            unreadToken();
            while (true) {
                token = nextToken();
                if (token == null) throw new CompileException(ExceptionString.FunctionIncomplete, position);
                if (JudgeToken.isTypeSpecifier(token) || JudgeToken.isConstQualifier(token)) {
                    unreadToken();
                    break;
                }
                // 解析出一个参数
                Parameter parameter = new Parameter();
                if (JudgeToken.isConstQualifier(token)) {
                    parameter.setConst(true);
                    token = nextToken();
                    if (token == null || !JudgeToken.isTypeSpecifier(token))
                        throw new CompileException(ExceptionString.FunctionIncomplete, position);
                } else {
                    parameter.setVariableType(VariableType.getVariableType(token.getStringValue()));
                    token = nextToken();
                    if (token == null || token.getType() != TokenType.IDENTIFIER)
                        throw new CompileException(ExceptionString.FunctionIncomplete, position);
                    parameter.setName(token.getStringValue());
                }
                parameters.add(parameter);

                // 判断读到的是不是逗号,如果是逗号,就接着循环,如果不是逗号,那么直接break
                token = nextToken();
                if (token == null || token.getType() != TokenType.COMMA) {
                    unreadToken();
                    break;
                }
            }
        } else throw new CompileException(ExceptionString.FunctionIncomplete, position);
        token = nextToken();
        if (token == null || token.getType() != TokenType.RIGHT_PARE)
            throw new CompileException(ExceptionString.FunctionIncomplete, position);
        return parameters;
    }

    /**
     * <compound-statement> ::=
     * '{' {<variable-declaration>} <statement-seq> '}'
     * <statement-seq> ::=
     * {<statement>}
     * <statement> ::=
     * <compound-statement>
     * |<condition-statement>
     * |<loop-statement>
     * |<jump-statement>
     * |<print-statement>
     * |<scan-statement>
     * |<assignment-expression>';'
     * |<function-call>';'
     * |';'
     *
     * @return 返回该函数体对应的语法树
     */
    private CompoundStatementAST compoundStatement() throws CompileException {
        CompoundStatementAST compoundStatementAST = new CompoundStatementAST();
        Token token = nextToken();
        if (token == null || token.getType() != TokenType.LEFT_BRACE)
            throw new CompileException(ExceptionString.MissingLeftBrace, position);
        // 首先是解析前面若干条变量声明语句
        while (true) {
            token = nextToken();
            if (token == null) throw new CompileException(ExceptionString.MissingRightBrace, position);
            else if (JudgeToken.isConstQualifier(token) || JudgeToken.isTypeSpecifier(token))
                compoundStatementAST.addVariableDeclarationAST(variableDeclaration());
            else {
                unreadToken();
                break;
            }
        }
        // 然后是后半部分
        while (true) {
            token = nextToken();
            if(token == null) throw new CompileException(ExceptionString.MissingRightBrace, position);
            else if(token.getType() == TokenType.RIGHT_BRACE) return compoundStatementAST;
            else{
                unreadToken();
                compoundStatementAST.addStatementASTList(statement());
            }
        }
    }

    private StatementAST statement() throws CompileException {
        Token token = nextToken();
        StatementAST statementAST;
        if(token == null) throw new CompileException(ExceptionString.MissingRightBrace, position);
        switch (token.getType()){
            case LEFT_BRACE:{
                unreadToken();
                return compoundStatement();
            }
            case IF:
            case SWITCH: {
                unreadToken();
                return conditionStatement();
            }
            case WHILE:
            case FOR:
            case DO:{
                unreadToken();
                return loopStatement();
            }
            case BREAK:
            case RETURN:
            case CONTINUE:{
                unreadToken();
                return jumpStatement();
            }
            case SCAN: {
                unreadToken();
                return scanStatement();
            }
            case PRINT: {
                unreadToken();
                return printStatement();
            }
            case IDENTIFIER: {
                token = nextToken();
                if(token==null) throw new CompileException(ExceptionString.AssignStatementIncomplete, position);
                if(token.getType() == TokenType.ASSGN) {
                    unreadToken();
                    unreadToken();
                    statementAST =  assignStatement();
                } else if(token.getType() == TokenType.LEFT_PARE) {
                    unreadToken();
                    unreadToken();
                    statementAST = functionCallStatement();
                } else {
                    throw new CompileException(ExceptionString.AssignStatementIncomplete, position);
                }
                token = nextToken();
                if(token == null || token.getType()!=TokenType.SEMI) throw new CompileException(ExceptionString.NoSemi, position);
                return statementAST;
            }
            case SEMI: {
                return new SemiStatementAST();
            }
            default : {
                throw new CompileException(ExceptionString.InvalidStatement, position);
            }
        }
    }


    private ConditionStatementAST conditionStatement() throws CompileException {
        ConditionStatementAST conditionStatementAST = new ConditionStatementAST();
        return conditionStatementAST;
    }

    private LoopStatementAST loopStatement() throws CompileException {
        LoopStatementAST loopStatementAST = new LoopStatementAST();
        return loopStatementAST;
    }

    private JumpStatementAST jumpStatement() throws CompileException {
        JumpStatementAST jumpStatementAST = new JumpStatementAST();
        return jumpStatementAST;
    }

    private ScanStatementAST scanStatement() throws CompileException {
        ScanStatementAST scanStatementAST = new ScanStatementAST();
        return scanStatementAST;
    }

    private PrintStatementAST printStatement() throws CompileException {
        PrintStatementAST printStatementAST = new PrintStatementAST();
        return printStatementAST;
    }

    private AssignStatementAST assignStatement() throws CompileException {
        AssignStatementAST assignStatementAST = new AssignStatementAST();
        return assignStatementAST;
    }

    private FunctionCallStatementAST functionCallStatement() throws CompileException {
        FunctionCallStatementAST functionCallStatementAST = new FunctionCallStatementAST();
        return functionCallStatementAST;
    }

    private ExpressionAST expression() {
        return null;
    }


    public GrammaAnalyser(List<Token> tokens) {
        this.tokens = tokens;
        this.tokenIndex = -1;
        position = new Position(0, -1);
    }

    /**
     * @return 返回读到的下一个token, 如果token为null, 那么说明已经读完了全部的token序列
     */
    private Token nextToken() {
        try {
            Token token = tokens.get(++tokenIndex);
            position = token.getStartPosition();
            return token;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private void unreadToken() {
        tokenIndex--;
    }
}
