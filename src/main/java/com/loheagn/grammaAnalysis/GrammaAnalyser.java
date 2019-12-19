package com.loheagn.grammaAnalysis;

import com.loheagn.ast.*;
import com.loheagn.ast.expressionAST.*;
import com.loheagn.semanticAnalysis.IdentifierType;
import com.loheagn.semanticAnalysis.Parameter;
import com.loheagn.semanticAnalysis.RelationOperatorType;
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
                c0ProgramAST.addVariableDeclarationASTList(variableDeclaration());
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
                        c0ProgramAST.addVariableDeclarationASTList(variableDeclaration());
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
     *
     * @return 返回相应类型的语法树
     */
    private List<VariableDeclarationAST> variableDeclaration() throws CompileException {
        List<VariableDeclarationAST> variableDeclarationASTList = new ArrayList<VariableDeclarationAST>();
        boolean isConst = false;
        IdentifierType identifierType;
        Token token = nextToken();
        if (token == null) {
            return variableDeclarationASTList;
        } else if (JudgeToken.isConstQualifier(token)) {
            isConst = true;
            token = nextToken();
            if (token == null) throw new CompileException(ExceptionString.VariableDeclaration, position);
        }
        if (!JudgeToken.isTypeSpecifier(token))
            throw new CompileException(ExceptionString.VariableDeclaration, position);
        identifierType = IdentifierType.getVariableType(token.getStringValue());
        variableDeclarationASTList.add(variableDeclarationAST(isConst, identifierType));
        while (true) {
            token = nextToken();
            if (token == null) throw new CompileException(ExceptionString.NoSemi, position);
            else if (token.getType() == TokenType.SEMI) return variableDeclarationASTList;
            else if (token.getType() == TokenType.COMMA)
                variableDeclarationASTList.add(variableDeclarationAST(isConst, identifierType));
            else throw new CompileException(ExceptionString.NoSemi, position);
        }
    }

    /**
     * <init-declarator> ::=
     *     <identifier>[<initializer>]
     * <initializer> ::=
     *     '='<expression>
     */
    private VariableDeclarationAST variableDeclarationAST(boolean isConst, IdentifierType type) throws CompileException {
        VariableDeclarationAST variableDeclarationAST = new VariableDeclarationAST();
        if(isConst)
            variableDeclarationAST.setConst();
        variableDeclarationAST.setType(type);
        Token token = nextToken();
        if(token == null || token.getType()!=TokenType.IDENTIFIER) throw new CompileException(ExceptionString.VariableDeclaration, position);
        variableDeclarationAST.setIdentifier(token.getStringValue());
        token = nextToken();
        if(token != null && token.getType()==TokenType.ASSGN) {
            variableDeclarationAST.setExpressionAST(expression());
            return variableDeclarationAST;
        } else {
            unreadToken();
            return variableDeclarationAST;
        }
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
        if (token == null) {
            return null;
        }
        functionAST.setFunctionType(IdentifierType.getVariableType(token.getStringValue()));
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
                    parameter.setIdentifierType(IdentifierType.getVariableType(token.getStringValue()));
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
                compoundStatementAST.addVariableDeclarationASTList(variableDeclaration());
            else {
                unreadToken();
                break;
            }
        }
        // 然后是后半部分
        while (true) {
            token = nextToken();
            if (token == null) throw new CompileException(ExceptionString.MissingRightBrace, position);
            else if (token.getType() == TokenType.RIGHT_BRACE) return compoundStatementAST;
            else {
                unreadToken();
                compoundStatementAST.addStatementASTList(statement());
            }
        }
    }

    private StatementAST statement() throws CompileException {
        Token token = nextToken();
        StatementAST statementAST;
        if (token == null) throw new CompileException(ExceptionString.MissingRightBrace, position);
        switch (token.getType()) {
            case LEFT_BRACE: {
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
            case DO: {
                unreadToken();
                return loopStatement();
            }
            case BREAK:
            case RETURN:
            case CONTINUE: {
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
                statementAST = forUpdateExpression();
                token = nextToken();
                if (token == null || token.getType() != TokenType.SEMI)
                    throw new CompileException(ExceptionString.NoSemi, position);
                return statementAST;
            }
            case SEMI: {
                return new SemiStatementAST();
            }
            default: {
                throw new CompileException(ExceptionString.InvalidStatement, position);
            }
        }
    }

    /**
     * <condition> ::=
     *      <expression>[<relational-operator><expression>]
     * @return 返回条件语句的语法树
     */
    private ConditionAST condition() throws CompileException {
        ConditionAST conditionAST = new ConditionAST();
        conditionAST.setExpressionAST1(expression());
        // 试读下一个token
        Token token = nextToken();
        if(token == null) throw new CompileException(ExceptionString.NoSemi, position);
        else if(JudgeToken.isRelationOperator(token)){
            conditionAST.setRelationOperator(RelationOperatorType.getRelationOperatorType(token.getStringValue()));
            token = nextToken();
            if(token == null) throw new CompileException(ExceptionString.NoSemi, position);
            conditionAST.setExpressionAST2(expression());
        } else {
            unreadToken();
        }
        token = nextToken();
        if(token == null || token.getType()!=TokenType.SEMI) throw new CompileException(ExceptionString.NoSemi, position);
        return conditionAST;
    }

    /**
     * <condition-statement> ::=
     *      'if' '(' <condition> ')' <statement> ['else' <statement>]
     *     |'switch' '(' <expression> ')' '{' {<labeled-statement>} '}'
     */
    private ConditionStatementAST conditionStatement() throws CompileException {
        Token token = nextToken();
        if(token == null) throw new CompileException(ExceptionString.MissingRightBrace, position);
        else if(token.getType()==TokenType.IF){
            unreadToken();
            return ifConditionStatement();
        }
        else if(token.getType() == TokenType.SWITCH){
            unreadToken();
            return switchConditionStatement();
        }
        else throw new CompileException(ExceptionString.OtherSyntaxError, position);
    }

    /**
     * 'if' '(' <condition> ')' <statement> ['else' <statement>]
     */
    private IfConditionStatementAST ifConditionStatement() throws CompileException {
        IfConditionStatementAST ifConditionStatementAST = new IfConditionStatementAST();
        Token token = nextToken();
        if(token == null || token.getType()!=TokenType.IF) throw new CompileException(ExceptionString.NoIf, position);
        ifConditionStatementAST.setConditionAST(parseConditionPa());
        ifConditionStatementAST.setIfStatementAST(statement());
        // 试读
        token = nextToken();
        if(token != null && token.getType()==TokenType.ELSE) {
            ifConditionStatementAST.setElseStatementAST(statement());
        }
        unreadToken();
        return ifConditionStatementAST;
    }

    /**
     * 'switch' '(' <expression> ')' '{' {<labeled-statement>} '}'
     */
    private SwitchConditionStatementAST switchConditionStatement() throws CompileException {
        SwitchConditionStatementAST switchConditionStatementAST = new SwitchConditionStatementAST();
        Token token = nextToken();
        if(token == null || token.getType()!= TokenType.SWITCH) throw new CompileException(ExceptionString.NoSwitch, position);
        token = nextToken();
        if(token==null || token.getType()!= TokenType.LEFT_PARE) throw new CompileException(ExceptionString.NoLeftPare, position);
        switchConditionStatementAST.setExpressionAST(expression());
        token = nextToken();
        if(token == null || token.getType()!= TokenType.RIGHT_PARE) throw new CompileException(ExceptionString.NoRightPare, position);
        token = nextToken();
        if(token == null || token.getType()!=TokenType.LEFT_PARE) throw new CompileException(ExceptionString.MissingLeftBrace, position);
        while(true) {
            token = nextToken();
            if(token == null) throw new CompileException(ExceptionString.MissingRightBrace, position);
            else if(token.getType()== TokenType.RIGHT_BRACE) return switchConditionStatementAST;
            else switchConditionStatementAST.addLabeledStatementAST(labeledStatement());
        }
    }

    /**
     * <labeled-statement> ::=
     *      'case' (<integer-literal>|<char-literal>) ':' <statement>
     *     |'default' ':' <statement>
     */
    private LabeledStatementAST labeledStatement() throws CompileException {
        Token token = nextToken();
        if(token == null) throw new CompileException(ExceptionString.MissingRightBrace, position);
        else if(token.getType() == TokenType.CASE) {
            unreadToken();
            return caseLabeledStatement();
        } else if(token.getType() == TokenType.DEFAULT) {
            unreadToken();
            return defaultLabeledStatementAST();
        } else throw new CompileException(ExceptionString.OtherSyntaxError, position);
    }

    /**
     * 'case' (<integer-literal>|<char-literal>) ':' <statement>
     */
    private CaseLabeledStatementAST caseLabeledStatement() throws CompileException {
        CaseLabeledStatementAST caseLabeledStatementAST = new CaseLabeledStatementAST();
        Token token = nextToken();
        if(token==null || token.getType()!=TokenType.CASE) throw new CompileException(ExceptionString.NoCase, position);
        token = nextToken();
        if(token ==null || (token.getType()!=TokenType.INTEGER && token.getType()!=TokenType.CHARACTER)) throw new CompileException(ExceptionString.CaseIncomplete, position);
        caseLabeledStatementAST.setValue(token.getIntValue());
        token = nextToken();
        if(token == null || token.getType()!= TokenType.COLON) throw new CompileException(ExceptionString.NoColon, position);
        caseLabeledStatementAST.setStatementAST(statement());
        return caseLabeledStatementAST;
    }

    /**
     * 'default' ':' <statement>
     */
    private DefaultLabeledStatementAST defaultLabeledStatementAST() throws CompileException {
        DefaultLabeledStatementAST defaultLabeledStatementAST = new DefaultLabeledStatementAST();
        Token token = nextToken();
        if(token == null || token.getType()!= TokenType.DEFAULT) throw new CompileException(ExceptionString.NODefault, position);
        token = nextToken();
        if(token == null || token.getType()!= TokenType.COLON) throw new CompileException(ExceptionString.NoColon, position);
        defaultLabeledStatementAST.setStatementAST(statement());
        return defaultLabeledStatementAST;
    }

    /**
     * <loop-statement> ::=
     *     'while' '(' <condition> ')' <statement>
     *    |'do' <statement> 'while' '(' <condition> ')' ';'
     *    |'for' '('<for-init-statement> [<condition>]';' [<for-update-expression>]')' <statement>
     */
    private LoopStatementAST loopStatement() throws CompileException {
        Token token = nextToken();
        if(token == null) throw new CompileException(ExceptionString.OtherSyntaxError, position);
        switch (token.getType()) {
            case WHILE:
                unreadToken();
                return whileLoopStatement();
            case DO:
                unreadToken();
                return doLoopStatement();
            case FOR:
                unreadToken();
                return forLoopStatement();
            default:
                throw new CompileException(ExceptionString.OtherSyntaxError, position);
        }
    }

    /**
     * 'while' '(' <condition> ')' <statement>
     */
    private WhileLoopStatementAST whileLoopStatement() throws CompileException {
        WhileLoopStatementAST whileLoopStatementAST = new WhileLoopStatementAST();
        Token token = nextToken();
        if(token == null || token.getType()!= TokenType.WHILE) throw new CompileException(ExceptionString.NoWhile, position);
        whileLoopStatementAST.setConditionAST(parseConditionPa());
        whileLoopStatementAST.setStatementAST(statement());
        return null;
    }

    /**
     * 'do' <statement> 'while' '(' <condition> ')' ';'
     */
    private DoLoopStatementAST doLoopStatement() throws CompileException {
        DoLoopStatementAST doLoopStatementAST = new DoLoopStatementAST();
        Token token = nextToken();
        if(token == null || token.getType()!= TokenType.DO) throw new CompileException(ExceptionString.NoDo, position);
        doLoopStatementAST.setStatementAST(statement());
        token = nextToken();
        if(token == null || token.getType()!= TokenType.WHILE) throw new CompileException(ExceptionString.NoWhile, position);
        doLoopStatementAST.setConditionAST(parseConditionPa());
        token = nextToken();
        if(token == null || token.getType()!=TokenType.SEMI) throw new CompileException(ExceptionString.NoSemi, position);
        return null;
    }

    /**
     * 'for' '('<for-init-statement> [<condition>]';' [<for-update-expression>]')' <statement>
     *     <for-init-statement> ::=
     *     [<assignment-expression>{','<assignment-expression>}]';'
     * <for-update-expression> ::=
     *     (<assignment-expression>|<function-call>){','(<assignment-expression>|<function-call>)}
     */
    private ForLoopStatementAST forLoopStatement() throws CompileException {
        ForLoopStatementAST forLoopStatementAST = new ForLoopStatementAST();
        Token token = nextToken();
        if(token == null || token.getType()!=TokenType.FOR) throw new CompileException(ExceptionString.NoFor, position);
        token = nextToken();
        if(token == null || token.getType()!=TokenType.LEFT_PARE) throw new CompileException(ExceptionString.NoLeftPare, position);
        forLoopStatementAST.setAssignStatementASTList(forInitStatement());
        token = nextToken();
        if(token == null) throw new CompileException(ExceptionString.ForIncomplete, position);
        if(token.getType()!=TokenType.SEMI){
            unreadToken();
            forLoopStatementAST.setConditionAST(condition());
            token = nextToken();
            if(token == null) throw new CompileException(ExceptionString.ForIncomplete, position);
            if(token.getType()!=TokenType.SEMI) throw new CompileException(ExceptionString.NoSemi, position);
        }
        forLoopStatementAST.setConditionAST(null);
        token = nextToken();
        if(token == null) throw new CompileException(ExceptionString.ForIncomplete, position);
        if(token.getType()!= TokenType.RIGHT_PARE) {
            unreadToken();
            forLoopStatementAST.setForUpdateExpressionASTS(forUpdateExpressionList());
            token = nextToken();
            if(token == null) throw new CompileException(ExceptionString.ForIncomplete, position);
            if(token.getType()!=TokenType.SEMI) throw new CompileException(ExceptionString.NoRightPare, position);
        }
        forLoopStatementAST.setStatementAST(statement());
        return forLoopStatementAST;
    }

    /**
     * <for-init-statement> ::=
     *      [<assignment-expression>{','<assignment-expression>}]';'
     */
    private List<AssignStatementAST> forInitStatement () throws CompileException {
        List<AssignStatementAST> assignStatementASTList = new ArrayList<AssignStatementAST>();
        while(true) {
            Token token = nextToken();
            if(token == null) throw new CompileException(ExceptionString.ForIncomplete, position);
            if(token.getType() == TokenType.SEMI) {
                unreadToken();
                break;
            }
            unreadToken();
            assignStatementASTList.add(assignStatement());
            token = nextToken();
            if(token == null) throw new CompileException(ExceptionString.ForIncomplete, position);
            if(token.getType()!=TokenType.COMMA) {
                unreadToken();
                break;
            }
        }
        Token token = nextToken();
        if(token == null || token.getType()!=TokenType.SEMI) throw new CompileException(ExceptionString.NoSemi, position);
        return assignStatementASTList;
    }

    /**
    * <for-update-expression> ::=
     * *     (<assignment-expression>|<function-call>){','(<assignment-expression>|<function-call>)}
     */
    private List<ForUpdateExpressionAST> forUpdateExpressionList() throws CompileException {
        List<ForUpdateExpressionAST> forUpdateExpressionASTList = new ArrayList<ForUpdateExpressionAST>();
        forUpdateExpressionASTList.add(forUpdateExpression());
        while(true) {
            Token token = nextToken();
            if(token == null) throw new CompileException(ExceptionString.ForIncomplete, position);
            if(token.getType()!=TokenType.COMMA){
                unreadToken();
                break;
            }
            forUpdateExpressionASTList.add(forUpdateExpression());
        }
        return forUpdateExpressionASTList;
    }

    private ForUpdateExpressionAST forUpdateExpression() throws CompileException {
        Token token = nextToken();
        if (token == null) throw new CompileException(ExceptionString.AssignStatementIncomplete, position);
        if (token.getType() == TokenType.ASSGN) {
            unreadToken();
            unreadToken();
            return assignStatement();
        } else if (token.getType() == TokenType.LEFT_PARE) {
            unreadToken();
            unreadToken();
            return functionCallStatement();
        } else {
            throw new CompileException(ExceptionString.AssignStatementIncomplete, position);
        }
    }


    /**
     *<jump-statement> ::=
     *      'break' ';'
     *     |'continue' ';'
     *     |<return-statement>
     * <return-statement> ::= 'return' [<expression>] ';'
     */
    private JumpStatementAST jumpStatement() throws CompileException {
        Token token = nextToken();
        if(token == null) throw new CompileException(ExceptionString.OtherSyntaxError, position);
        switch (token.getType()) {
            case BREAK:
                token = nextToken();
                if(token == null || token.getType()!=TokenType.SEMI) throw new CompileException(ExceptionString.NoSemi, position);
                return new BreakJumpStatementAST();
            case CONTINUE:
                token = nextToken();
                if(token == null || token.getType()!=TokenType.SEMI) throw new CompileException(ExceptionString.NoSemi, position);
                return new ContinueJumpStatementAST();
            case RETURN:
                ReturnJumpStatementAST returnJumpStatementAST = new ReturnJumpStatementAST();
                token = nextToken();
                if(token == null) throw new CompileException(ExceptionString.NoSemi, position);
                if(token.getType()==TokenType.SEMI) return returnJumpStatementAST;
                else {
                    unreadToken();
                    returnJumpStatementAST.setExpressionAST(expression());
                    token = nextToken();
                    if(token == null || token.getType()!=TokenType.SEMI) throw new CompileException(ExceptionString.NoSemi, position);
                    return returnJumpStatementAST;
                }
            default:
                throw new CompileException(ExceptionString.OtherSyntaxError,position);
        }
    }

    /**
     * <scan-statement> ::=
     *     'scan' '(' <identifier> ')' ';'
     */
    private ScanStatementAST scanStatement() throws CompileException {
        ScanStatementAST scanStatementAST = new ScanStatementAST();
        Token token = nextToken();
        assert token!=null && token.getType()==TokenType.SCAN;
        token = nextToken();
        if(token == null || token.getType()!=TokenType.LEFT_PARE) throw new CompileException(ExceptionString.NoLeftPare, position);
        token = nextToken();
        if(token ==null || token.getType()!=TokenType.IDENTIFIER) throw new CompileException(ExceptionString.NoIdentifier, position);
        scanStatementAST.setVariable(token.getStringValue());
        token = nextToken();
        if(token == null || token.getType()!=TokenType.RIGHT_PARE) throw new CompileException(ExceptionString.NoRightPare, position);
        token = nextToken();
        if(token == null || token.getType()!=TokenType.SEMI) throw new CompileException(ExceptionString.NoSemi, position);
        return scanStatementAST;
    }

    /**
     * <print-statement> ::=
     *     'print' '(' [<printable-list>] ')' ';'
     * <printable-list>  ::=
     *     <printable> {',' <printable>}
     * <printable> ::=
     *     <expression> | <string-literal>
     */
    private PrintStatementAST printStatement() throws CompileException {
        PrintStatementAST printStatementAST = new PrintStatementAST();
        Token token = nextToken();
        assert token!=null;
        token = nextToken();
        if(token == null || token.getType()!=TokenType.LEFT_PARE) throw new CompileException(ExceptionString.NoLeftPare, position);
        token = nextToken();
        if(token == null) throw new CompileException(ExceptionString.PrintIncomplete, position);
        if(token.getType()!=TokenType.RIGHT_PARE){
            unreadToken();
            printStatementAST.setPrintList(printableList());
            token = nextToken();
            if(token == null || token.getType()!=TokenType.RIGHT_PARE) throw new CompileException(ExceptionString.NoRightPare, position);
        }
        token = nextToken();
        if(token == null || token.getType()!=TokenType.SEMI) throw new CompileException(ExceptionString.NoSemi, position);
        return printStatementAST;
    }

    /**
     * <printable-list>  ::=
     *     <printable> {',' <printable>}
     * <printable> ::=
     *     <expression> | <string-literal>
     */
    private List<Object> printableList()throws CompileException{
        List<Object> objectList = new ArrayList<Object>();
        Token token = nextToken();
        assert token!=null;
        if(token.getType()==TokenType.STRING) objectList.add(token.getStringValue());
        else {
            unreadToken();
            objectList.add(expression());
        }
        while(true) {
            token = nextToken();
            if(token == null) throw new CompileException(ExceptionString.PrintIncomplete, position);
            if(token.getType()!= TokenType.COMMA) {
                unreadToken();
                break;
            }
            token = nextToken();
            if(token == null) throw new CompileException(ExceptionString.PrintIncomplete, position);
            if(token.getType()==TokenType.STRING) objectList.add(token.getStringValue());
            else {
                unreadToken();
                objectList.add(expression());
            }
        }
        return objectList;
    }

    /**
     * <assignment-expression> ::=
     *     <identifier><assignment-operator><expression>
     */
    private AssignStatementAST assignStatement() throws CompileException {
        AssignStatementAST assignStatementAST = new AssignStatementAST();
        Token token = nextToken();
        assert token!=null && token.getType()==TokenType.IDENTIFIER;
        assignStatementAST.setIdentifier(token.getStringValue());
        token = nextToken();
        if(token==null || token.getType()!=TokenType.ASSGN) throw new CompileException(ExceptionString.AssignStatementIncomplete, position);
        assignStatementAST.setExpressionAST(expression());
        return assignStatementAST;
    }

    /**
     * <function-call> ::=
     *     <identifier> '(' [<expression-list>] ')'
     * <expression-list> ::=
     *     <expression>{','<expression>}
     */
    private FunctionCallStatementAST functionCallStatement() throws CompileException {
        FunctionCallStatementAST functionCallStatementAST = new FunctionCallStatementAST();
        Token token = nextToken();
        assert token!=null && token.getType()==TokenType.IDENTIFIER;
        functionCallStatementAST.setIdentifier(token.getStringValue());
        token = nextToken();
        if(token == null || token.getType()!=TokenType.LEFT_PARE) throw new CompileException(ExceptionString.NoLeftPare, position);
        token = nextToken();
        if(token == null) throw new CompileException(ExceptionString.FunctionIncomplete, position);
        if(token.getType()!= TokenType.RIGHT_PARE){
            unreadToken();
            functionCallStatementAST.setExpressionASTList(expressionList());
            token = nextToken();
            if(token == null || token.getType()!=TokenType.RIGHT_PARE) throw new CompileException(ExceptionString.NoRightPare, position);
        }
        return functionCallStatementAST;
    }

    /**
     * <expression-list> ::=
     *     <expression>{','<expression>}
     */
    private List<ExpressionAST> expressionList() throws CompileException {
        List<ExpressionAST> expressionASTList = new ArrayList<ExpressionAST>();
        expressionASTList.add(expression());
        while(true) {
            Token token = nextToken();
            if(token == null) throw new CompileException(ExceptionString.NoRightPare, position);
            if(token.getType()!=TokenType.COMMA) {
                unreadToken();
                break;
            }
            expressionASTList.add(expression());
        }
        return expressionASTList;
    }

    /**
     * <expression> ::=
     *     <additive-expression>
     */
    private ExpressionAST expression() throws CompileException {
        return additiveExpression();
    }

    /**
     * <additive-expression> ::=
     *      <multiplicative-expression>{<additive-operator><multiplicative-expression>}
     */
    private AdditiveExpressionAST additiveExpression() throws CompileException {
        AdditiveExpressionAST additiveExpressionAST = new AdditiveExpressionAST();
        additiveExpressionAST.addMultiplicativeExpressionAST(multiplicativeExpression());
        while(true) {
            Token token = nextToken();
            if(token==null) throw new CompileException(ExceptionString.NoSemi, position);
            if(!JudgeToken.isAdditiveOperator(token)) {
                unreadToken();
                break;
            }
            additiveExpressionAST.addOperator(token.getType());
            additiveExpressionAST.addMultiplicativeExpressionAST(multiplicativeExpression());
        }
        return additiveExpressionAST;
    }

    /**
     * <multiplicative-expression> ::=
     *      <cast-expression>{<multiplicative-operator><cast-expression>}
     */
    private MultiplicativeExpressionAST multiplicativeExpression() throws CompileException {
        MultiplicativeExpressionAST multiplicativeExpressionAST = new MultiplicativeExpressionAST();
        multiplicativeExpressionAST.addCastExpressionAST(castExpression());
        while(true) {
            Token token = nextToken();
            if(token==null) throw new CompileException(ExceptionString.NoSemi, position);
            if(!JudgeToken.isMultiplicativeOperator(token)) {
                unreadToken();
                break;
            }
            multiplicativeExpressionAST.addMultiplicativeOperator(token.getType());
            multiplicativeExpressionAST.addCastExpressionAST(castExpression());
        }
        return multiplicativeExpressionAST;
    }

    /**
     * <cast-expression> ::=
     *     {'('<type-specifier>')'}<unary-expression>
     */
    private CastExpressionAST castExpression() throws CompileException {
        CastExpressionAST castExpressionAST = new CastExpressionAST();
        TokenType typeSpecifier = null;
        while(true) {
            Token token = nextToken();
            if(token == null) throw new CompileException(ExceptionString.ExpressionIncomplete, position);
            if(token.getType()!=TokenType.LEFT_PARE) {
                unreadToken();
                break;
            }
            token = nextToken();
            if(token == null || !JudgeToken.isTypeSpecifier(token)) throw new CompileException(ExceptionString.ExpressionIncomplete, position);
            if(token.getType()== TokenType.VOID) throw new CompileException(ExceptionString.CastToVoid, position);
            if(typeSpecifier==null) typeSpecifier = token.getType();
            token = nextToken();
            if(token == null || token.getType()!=TokenType.RIGHT_PARE ) throw new CompileException(ExceptionString.NoRightPare, position);
        }
        castExpressionAST.setTypeSpecifiers(typeSpecifier);
        castExpressionAST.setUnaryExpressionAST(unaryExpression());
        return castExpressionAST;
    }

    /**
     * <unary-expression> ::=
     *     [<unary-operator>]<primary-expression>
     */
    private UnaryExpressionAST unaryExpression() throws CompileException {
        UnaryExpressionAST unaryExpressionAST = new UnaryExpressionAST();
        Token token = nextToken();
        if(token == null) throw new CompileException(ExceptionString.ExpressionIncomplete, position);
        if(JudgeToken.isUnaryOperator(token)) unaryExpressionAST.setOperator(token.getType());
        else unreadToken();
        // 处理primary expression
        token = nextToken();
        if(token == null) throw new CompileException(ExceptionString.ExpressionIncomplete, position);
        if(token.getType() == TokenType.LEFT_PARE){
            unaryExpressionAST.setPrimaryExpression(expression());
            token = nextToken();
            if(token == null || token.getType()!=TokenType.RIGHT_PARE) throw new CompileException(ExceptionString.NoRightPare, position);
        } else if(token.getType() == TokenType.IDENTIFIER) {
            String identifier = token.getStringValue();
            token = nextToken();
            if(token != null && token.getType()==TokenType.LEFT_PARE){
                unreadToken();
                unreadToken();
                unaryExpressionAST.setPrimaryExpression(functionCallStatement());
            }
            unreadToken();
            unaryExpressionAST.setPrimaryExpression(identifier);
        } else if(token.getType()==TokenType.INTEGER) unaryExpressionAST.setPrimaryExpression(token.getIntValue());
        else if(token.getType() == TokenType.DOUBLE) unaryExpressionAST.setPrimaryExpression(token.getDoubleValue());
        else if(token.getType()== TokenType.CHARACTER) unaryExpressionAST.setPrimaryExpression(token.getCharValue());
        else {
            throw new CompileException(ExceptionString.ExpressionIncomplete, position);
        }
        return unaryExpressionAST;
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

    private ConditionAST parseConditionPa() throws CompileException{
        Token token = nextToken();
        if(token == null || token.getType()!= TokenType.LEFT_PARE) throw new CompileException(ExceptionString.NoLeftPare, position);
        ConditionAST conditionAST = condition();
        token = nextToken();
        if(token == null || token.getType()!=TokenType.RIGHT_PARE) throw new CompileException(ExceptionString.NoRightPare, position);
        return conditionAST;
    }
}
