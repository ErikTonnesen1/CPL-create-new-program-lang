package main.parser;

import main.Token.Token;
import main.Token.Token.Type;
import main.LexicalAnalyzer.LexicalAnalyzer;
import main.nonterminals.*;

public class Parser {

    private Token currentToken;
    private LexicalAnalyzer lexanyzer;
    private int parenthesisCount;

    // constructor
    public Parser(LexicalAnalyzer lex) {

        this.currentToken = lex.getToken();
        this.lexanyzer = lex;
        parenthesisCount = 0;
    }

    // controller class to print any exceptions found when parsing
    public void parse() {
        try {
            ExpressionNode root = expression();
            if (this.parenthesisCount != 0) {
                throw new RuntimeException("Unclosed parenthesis pair: " + parenthesisCount);
            } else {
                System.out.println("Code is valid");
                System.out.println("Output: " + root.evaluate());
            }

        } catch (Exception e) {
            System.out.println(e);

        }

    }

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
            ExpressionNode e = expression();
            match(Token.Type.RIGHT_PARENTH);
            return new ParenFactorNode(e);
        } else if (currentToken.getType() == Token.Type.SUBTRACTION) {
            match(Token.Type.SUBTRACTION);
            ExpressionNode e = expression();
            return new NegFactor(e);
        } else if (currentToken.getType() == Token.Type.INT_LIT) {
            int l = number();
            return new NumNode(l);
        } else {
            throw new RuntimeException("Syntax error: unexpected character at" + currentToken.getRowNumber() + ":"
                    + currentToken.getColNumber());
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
            match(Token.Type.INT_LIT);
            return Integer.parseInt(currentToken.getLexeme());
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
            System.out.println(currentToken.getType());
            currentToken = lexanyzer.getToken();
        }

    }

}
