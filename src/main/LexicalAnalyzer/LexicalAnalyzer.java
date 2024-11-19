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

    // constructor
    public LexicalAnalyzer(String sourceCode) {
        this.source = sourceCode;
        this.index = 0;

        findAllTokens();
    }

    // when a Lexical analyzer is initialized, it creates all tokens from source
    // code to be ready for a parser's getToken call
    public void findAllTokens() {
        // parse through source string
        boolean eos_find = false;

        for (int i = this.index; i < this.source.length(); i++) {
            // if whitespace, do nothing
            if (this.source.charAt(i) == ';') {

                /*****************************
                 * ; is not a valid character in
                 * our grammar
                 */
                // if (i != this.source.length() - 1){
                // throw new IllegalArgumentException("Improper end of line at row: 0 col: " +
                // i);
                // }else {
                String lexeme = String.valueOf(this.source.charAt(i));
                createToken(lexeme, 0, i);
                eos_find = true;
                // }

            } else if (this.source.charAt(i) == ' ') {

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
                String nonIntToken = Character.toString(this.source.charAt(i));

                createToken(nonIntToken, 0, i);
            }
        }
        if (!eos_find) {
            throw new RuntimeException("Missing End of line token (;)");
        }

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

            } else if (Lexeme.equals(";")) {
                /***********************
                 * ???
                 */
                Token token = new Token("EOS", Token.Type.EOS_TOKEN, row, column);
                tokens.add(token);

            } else {
                // throw exception
                throw new IllegalArgumentException("Token Type is Invalid at index: " + column);
            }
        } else if (isInteger(Lexeme)) {
            Token token = new Token(Lexeme, Token.Type.INT_LIT, row, column);
            tokens.add(token);
        } else {
            // throw exception
            throw new IllegalArgumentException("Token Type is Invalid at index:  " + column);
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
        Token nextToken = tokens.get(0);
        tokens.remove(0);
        return nextToken;

    }

    // used for testing purposes
    public void printTokens() {
        for (int i = 0; i < tokens.size(); i++) {
            System.out.println("Tokens: " + tokens.get(i).getLexeme() + ", ");
        }
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

    /***************************************
     * do you need these - particularly the set methods?
     */

}
