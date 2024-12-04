// ########################
// Refactored Lexical Analyzer class
// ########################

package main.LexicalAnalyzer;

import java.util.*;

import main.Token.Token;
import main.Token.Token.Type;

public class LexicalAnalyzer {

    private String source;
    private int index;
    private ArrayList<Token> tokens = new ArrayList<Token>();
    private int currentIndex;

    // constructor
    public LexicalAnalyzer(String sourceCode) {
        this.source = sourceCode;
        this.index = 0;
        this.currentIndex = 0;

        findAllTokens();
    }

    // when a Lexical analyzer is initialized, it creates all tokens from source
    // code to be ready for a parser's getToken call
    public void findAllTokens() {
        // parse through source string
        for (int i = this.index; i < this.source.length(); i++) {

            if (this.source.charAt(i) == ' ' || this.source.charAt(i) == '\n') {

                // nothing

                // if character is a digit,
            } else if (Character.isDigit(this.source.charAt(i))) {
                // first check to see if digit could be multiple chars long ex: "123"
                if (i != this.source.length() - 1 && Character.isDigit(this.source.charAt(i + 1))) {
                    int findLastDigit = i;
                    // if longer than 1 char, find the end index of that number
                    while ((findLastDigit + 1) <= this.source.length() - 1
                            && Character.isDigit(this.source.charAt(findLastDigit))) {
                        findLastDigit++;
                    }
                    // For Later: this.index = findLastDigit;

                    // create substring of source, that results in the number Ex: (12+3)
                    // .substring(1, 3) = 12
                    String intToken = this.source.substring(i, findLastDigit);
                    // set i to end index to resume parsing after the already accounted for number
                    i = findLastDigit - 1;

                    // create token
                    createToken(intToken, 0, findLastDigit);

                } else {
                    // if (i + 1) != a digit, then it is a digit with a length of one
                    String intToken = Character.toString(this.source.charAt(i));
                    // create token
                    createToken(intToken, 0, i);

                }
                // if not a digit, must be an operator or symbol
            } else if (!Character.isDigit(this.source.charAt(i))) {
                // multi length string = identifier or keyword
                // 1. find full string
                if (i != this.source.length() - 1 && Character.isLetterOrDigit(this.source.charAt(i + 1))) {
                    int findWholeString = i;
                    while ((findWholeString + 1) <= this.source.length() - 1
                            && Character.isLetterOrDigit(this.source.charAt(findWholeString))
                            && this.source.charAt(findWholeString) != ' '
                            && this.source.charAt(findWholeString) != '\n') {
                        findWholeString++;
                    }
                    String wholeString = this.source.substring(i, findWholeString);
                    // System.out.println(wholeString);
                    // System.out.println(wholeString);
                    createToken(wholeString, 0, i);
                    i = findWholeString - 1;

                } else if (i != this.source.length() - 1 && this.source.charAt(i) == '<') {
                    if (this.source.charAt(i + 1) == '-') {
                        String assign = this.source.substring(i, i + 2);
                        // System.out.println(assign);
                        createToken(assign, 0, i);
                        i = i + 2;
                        // System.out.println(this.source.charAt(i));
                    }

                } else {
                    String nonIntToken = Character.toString(this.source.charAt(i));

                    // System.out.println(nonIntToken);
                    createToken(nonIntToken, 0, i);
                }
            }
        }
        // end of expression
        createToken("EOS", 0, this.source.length());

    }

    // now createToken is in the Lexical analyzer class instead of Token class
    private void createToken(String Lexeme, int row, int column) throws IllegalArgumentException {
        if (Lexeme.length() == 1) {
            if (isInteger(Lexeme)) {
                Token token = new Token(Lexeme, Token.Type.INT_LIT, row, column);
                tokens.add(token);

            } else if (Lexeme.equals("+")) {
                Token token = new Token(Lexeme, Token.Type.ADDITION, row, column);
                tokens.add(token);

            } else if (Lexeme.equals("-")) {
                Token token = new Token(Lexeme, Token.Type.SUBTRACTION, row, column);
                tokens.add(token);

            } else if (Lexeme.equals("*")) {
                Token token = new Token(Lexeme, Token.Type.MULTIPLICATION, row, column);
                tokens.add(token);

            } else if (Lexeme.equals("/")) {
                Token token = new Token(Lexeme, Token.Type.DIVISION, row, column);
                tokens.add(token);

            } else if (Lexeme.equals("(")) {
                Token token = new Token(Lexeme, Token.Type.LEFT_PARENTH, row, column);
                tokens.add(token);

            } else if (Lexeme.equals(")")) {
                Token token = new Token(Lexeme, Token.Type.RIGHT_PARENTH, row, column);
                tokens.add(token);

            } else if (Character.isLetter(Lexeme.toCharArray()[0])) {
                Token token = new Token(Lexeme, Token.Type.ID, row, column);
                tokens.add(token);

            } else {
                // throw exception
                throw new IllegalArgumentException(
                        "[Token Identification] Token Type is Invalid at index: " + column + ": " + Lexeme);
            }
        } else if (isInteger(Lexeme)) {
            Token token = new Token(Lexeme, Token.Type.INT_LIT, row, column);
            tokens.add(token);
        } else if (Lexeme.equals("EOS")) {
            Token token = new Token("EOS", Token.Type.EOS_TOKEN, row, column);
            tokens.add(token);

        } else if (Lexeme.length() >= 2) {
            // statements tokens
            switch (Lexeme.toLowerCase()) {
                case "<-":
                    Token assignToken = new Token(Lexeme, Token.Type.ASSIGN, row, column);
                    tokens.add(assignToken);
                    break;
                case "input":
                    Token inputToken = new Token(Lexeme, Token.Type.INPUT, row, column);
                    tokens.add(inputToken);
                    break;
                case "display":
                    Token displayToken = new Token(Lexeme, Token.Type.DISPLAY, row, column);
                    tokens.add(displayToken);
                    break;
                default:
                    Token token = new Token(Lexeme, Token.Type.ID, row, column);
                    tokens.add(token);
                    break;
            }
        } else {
            // throw exception
            throw new IllegalArgumentException(
                    "[Token Length > 1] Token Type is Invalid at index:  " + column);
        }
    }

    // determines if a string is an integer to define it as INT_LIT enumerated type
    private boolean isInteger(String Int) {
        try {
            Integer.parseInt(Int);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    // returns a token per method call as required
    public Token getToken() {
        if (currentIndex < tokens.size()) {
            Token nextToken = tokens.get(currentIndex);
            currentIndex++;
            return nextToken;
        } else {
            return null;
        }

    }

    // used for testing purposes
    public void printTokens() {
        for (int i = 0; i < tokens.size(); i++) {
            System.out.println("Tokens: " + tokens.get(i).getType() + ", " + tokens.get(i).getLexeme());
        }
        System.out.println(tokens.size());
    }

    // getters and setters
    public String getSource() {
        return source;
    }

    public int getIndex() {
        return index;
    }

    public void setSource(String newSource) {
        this.source = newSource;

    }

    public void setIndex(int newIndex) {
        this.index = newIndex;

    }

    public ArrayList<Token> getTokenList() {
        return tokens;
    }

    /***************************************
     * do you need these - particularly the set methods?
     */

}
