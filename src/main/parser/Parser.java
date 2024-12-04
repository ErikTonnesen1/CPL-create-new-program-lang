package main.parser;

import main.Token.Token;
import main.Token.Token.Type;

import java.beans.Expression;

import main.LexicalAnalyzer.LexicalAnalyzer;
import main.nonterminals.*;
import main.Memory.Memory;

public class Parser {

    private Token currentToken;
    private LexicalAnalyzer lexanyzer;
    private int parenthesisCount;
    private Memory memory;

    // constructor
    public Parser(LexicalAnalyzer lex, Memory mem) {

        this.currentToken = lex.getToken();
        this.lexanyzer = lex;
        this.memory = mem;
        parenthesisCount = 0;
    }

    // controller class to print any exceptions found when parsing
    public void parse() {
        try {
            ProgramNode root = program();
            root.evaluate();
            // if (this.parenthesisCount != 0) {
            // throw new RuntimeException("Unclosed parenthesis pair: " + parenthesisCount);
            // } else {
            // System.out.println("Code is valid");
            // System.out.println("Output: " + root.evaluate());
            // }

        } catch (Exception e) {
            System.out.println(e);

        }

    }

    // Assignment 9 implementation

    // <Program> ::= <Stmt_List>
    private ProgramNode program() {
        ProgramNode p = stmnt_list();
        return p;
    }

    // <Stmt_List> ::= <Statement> <Stmt_List> | <Statement>
    private StatementListNode stmnt_list() {
        if (currentToken.getType() == Token.Type.EOS_TOKEN) {
            return null;
        }
        StatementNode s = statement();
        if (currentToken.getType() == Token.Type.ID || currentToken.getType() == Token.Type.INPUT
                || currentToken.getType() == Token.Type.DISPLAY || currentToken.getType() == Token.Type.EOS_TOKEN) {
            // match(currentToken.getType());
            StatementListNode sl = stmnt_list();
            return new StatementListNode(s, sl);
        } else {
            // return new StatementListNode(s, null);
            throw new RuntimeException("[Statement List] : Unexpected Token: " + currentToken.getType());
        }

    }

    // < Statement > ::= <Assn_Stmt > | <Display_Stmt> | <Input_Stmt>
    private StatementNode statement() {
        if (currentToken.getType() == Token.Type.ID) {
            return assn_stmt();
        }
        if (currentToken.getType() == Token.Type.DISPLAY) {
            return display_stmt();
        }
        if (currentToken.getType() == Token.Type.INPUT) {
            return input_stmt();
        } else {
            throw new RuntimeException("[Statement Node] : Unexpected Token: " + currentToken.getType());

        }
    }

    // <Assn_Stmt> ::= Id “<-” <Expression>
    private AssignmentNode assn_stmt() {
        String id = currentToken.getLexeme();
        match(Token.Type.ID);
        match(Token.Type.ASSIGN);
        ExpressionNode e = expression();
        return new AssignmentNode(id, memory, e);

    }

    // <Display_Stmt> ::= “display” Id
    private DisplayNode display_stmt() {
        match(Token.Type.DISPLAY);
        String id = currentToken.getLexeme();
        match(Token.Type.ID);
        return new DisplayNode(id, memory);
    }
    // <Input_Stmt> ::= “input” Id

    private InputNode input_stmt() {
        match(Token.Type.INPUT);
        String id = currentToken.getLexeme();
        match(Token.Type.ID);
        return new InputNode(id, memory);
    }

    // From Assignment 8 and before
    // code created from pseudo-code template

    // < Expression > ::= <Term> <Expression_Prime>
    private ExpressionNode expression() {
        TermNode t = term();
        ExpressionNode e = expression_prime(t);
        return e;

    }

    // < Term > ::= <Factor> <Term_Prime>
    private TermNode term() {
        FactorNode f = factor();
        TermNode e = term_prime(f);
        return e;

    }

    // < Expression_Prime > ::= “+” < Term > <Expression_Prime>
    // | “-” < Term > <Expression_Prime>
    // | null
    private ExpressionNode expression_prime(ExpressionNode expr) {
        if (currentToken.getType() == Token.Type.ADDITION) {
            match(Token.Type.ADDITION);
            TermNode t = term();
            BinaryExpressionNode e1 = new BinaryExpressionNode(expr, t, "+");
            return expression_prime(e1);
        } else if (currentToken.getType() == Token.Type.SUBTRACTION) {
            match(Token.Type.SUBTRACTION);
            TermNode t = term();
            BinaryExpressionNode e1 = new BinaryExpressionNode(expr, t, "-");
            return expression_prime(e1);
        } else {
            return expr;
        }
    }

    // <Factor> ::= “(“ <Expression> “)” | “-” <Expression> | <Number>
    private FactorNode factor() {
        if (currentToken.getType() == Token.Type.LEFT_PARENTH) {
            match(Token.Type.LEFT_PARENTH);
            // System.out.println(currentToken.getType());
            ExpressionNode e = expression();
            // System.out.println(currentToken.getType());
            match(Token.Type.RIGHT_PARENTH);
            // System.out.println(currentToken.getType());
            return new ParenFactorNode(e);
        } else if (currentToken.getType() == Token.Type.SUBTRACTION) {
            match(Token.Type.SUBTRACTION);
            ExpressionNode e = expression();
            return new NegFactor(e);
        } else if (currentToken.getType() == Token.Type.INT_LIT) {
            int l = number();
            return new NumNode(l);
        } else if (currentToken.getType() == Token.Type.ID) {
            // do something here
            String id = currentToken.getLexeme();
            match(Token.Type.ID);
            return new IDNode(id, memory);
        } else {
            throw new RuntimeException("[Factor Node] : Unexpected Token: " + currentToken.getType() + " at "
                    + currentToken.getRowNumber() + ":" + currentToken.getColNumber());
        }

    }

    // <Term_Prime> ::= “*” <Factor> <Term_Prime>
    // | “/” <Factor> <Term_Prime>
    // | null
    private TermNode term_prime(TermNode expr) {
        if (currentToken.getType() == Token.Type.MULTIPLICATION) {
            match(Token.Type.MULTIPLICATION);
            FactorNode f = factor();
            BinaryTermNode t1 = new BinaryTermNode(expr, f, "*");
            return term_prime(t1);
        } else if (currentToken.getType() == Token.Type.DIVISION) {
            match(Token.Type.DIVISION);
            FactorNode f = factor();
            BinaryTermNode t1 = new BinaryTermNode(expr, f, "/");
            return term_prime(t1);
        } else {
            return expr;

        }

    }

    // < Number > ::= Int-Lit
    private int number() {
        if (currentToken.getType() == Token.Type.INT_LIT) {
            try {
                int numtoReturn = Integer.parseInt(currentToken.getLexeme());
                match(Token.Type.INT_LIT);
                return numtoReturn;
            } catch (Exception e) {
                System.out.println(currentToken.getType() == Token.Type.INT_LIT);
                return -1;
            }
        } else {
            throw new RuntimeException("Syntax error: expecting number at " + currentToken.getRowNumber() + ":"
                    + currentToken.getColNumber());
        }
    }

    // match
    // compares expected token to current token
    // if they are different then
    // error
    // else
    // get next token
    private void match(Token.Type expectedToken) {
        if (currentToken.getType() != expectedToken) {
            throw new IllegalArgumentException(
                    "Syntax Error: Expected: " + expectedToken + " but found: " + currentToken.getType() + " at "
                            + currentToken.getRowNumber() + ":" + currentToken.getColNumber());

        } else {
            // System.out.println(currentToken.getType());
            currentToken = lexanyzer.getToken();
        }

    }

}
