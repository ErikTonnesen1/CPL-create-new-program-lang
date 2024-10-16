package src.main.parser;
import src.main.Token.Token;

import src.main.LexicalAnalyzer.LexicalAnalyzer;

public class Parser {

    private Token currentToken;
    private LexicalAnalyzer lexanyzer; 
    private int parenthesisCount;

    // constructor
    public Parser( LexicalAnalyzer lex){

        this.currentToken = lex.getToken();
        this.lexanyzer = lex;
        parenthesisCount = 0; 
    }

    // controller class to print any exceptions found when parsing
    public void parse(){
        try {
            expression();
            if (this.parenthesisCount != 0) {
                throw new RuntimeException("Unclosed parenthesis pair: " + parenthesisCount);
            } else {
                System.out.println("Code is valid");
            }

        }catch(Exception e){
            System.out.println(e);

        }

    }

    // code created from pseudo-code template

    // < Expression > ::= <Term> <Expression_Prime>
    private void expression(){
        term();
        expression_prime();

    }
    // < Term > ::= <Factor> <Term_Prime>
    private void term(){
        factor();
        term_prime();

    }
//     < Expression_Prime > ::= “+” < Term > <Expression_Prime>
                            // | “-” < Term > <Expression_Prime>
                            // | null
    private void expression_prime(){
        if(currentToken.getType() == Token.Type.ADDITION){
            match(Token.Type.ADDITION);
            term();
            expression_prime();
        }else if (currentToken.getType() == Token.Type.SUBTRACTION){
            match(Token.Type.SUBTRACTION);
            term();
            expression_prime();
        }  
    }
    // <Factor> ::= “(“ <Expression> “)” | “-” <Expression> | <Number>
    private void factor(){
        if(currentToken.getType() == Token.Type.LEFT_PARENTH){
            match(Token.Type.LEFT_PARENTH);
            expression();
            match(Token.Type.RIGHT_PARENTH);
        }else if (currentToken.getType() == Token.Type.SUBTRACTION){
            match(Token.Type.SUBTRACTION);
            expression();
        }else if (currentToken.getType() == Token.Type.INT_LIT){
            number();
        }else{
            throw new RuntimeException("Syntax error: unexpected character at" + currentToken.getRowNumber() + ":" + currentToken.getColNumber());
        }

    }
    // <Term_Prime> ::= “*” <Factor> <Term_Prime>
                    // | “/” <Factor> <Term_Prime>
                    // | null
    private void term_prime(){
        if(currentToken.getType() == Token.Type.MULTIPLICATION){
            match(Token.Type.MULTIPLICATION);
            factor();
            term_prime();
        }else if (currentToken.getType() == Token.Type.DIVISION){
            match(Token.Type.DIVISION);
            factor();
            term_prime();
        }  

    }

    // < Number > ::= Int-Lit
    private void number(){
        if (currentToken.getType() == Token.Type.INT_LIT){
            match(Token.Type.INT_LIT);
        } else {
            throw new RuntimeException("Syntax error: expecting number at " + currentToken.getRowNumber() + ":" + currentToken.getColNumber());
        }
    }

// match
// compares expected token to current token
        // if they are different then
                // error
        // else
                // get next token
    private void match(Token.Type expectedToken){
        if (currentToken.getType() != expectedToken) {
            throw new IllegalArgumentException ("Syntax Error: Expected: " + expectedToken + " but found: " + currentToken.getType() + " at " + currentToken.getRowNumber() + ":" + currentToken.getColNumber());

        } else {
            System.out.println(currentToken.getType());
            currentToken = lexanyzer.getToken();
        }

    }
    
}
