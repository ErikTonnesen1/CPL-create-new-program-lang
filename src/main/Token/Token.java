
package src.main.Token;


public class Token {

    //enumerated type
    public enum Type { ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION, LEFT_PARENTH, RIGHT_PARENTH, INT_LIT, EOS_TOKEN };
    private Type type;
    private String Lexeme;
    private int rowNum;
    private int colNum;


    //constructor
    public Token(String lexeme,Type tokenType, int row, int column) throws IllegalArgumentException{

        this.Lexeme = lexeme;
        this.type = tokenType;
        this.rowNum = row;
        this.colNum = column;

    }




//getters
public Type getType(){
    return type;
}
public String getLexeme(){
    return Lexeme;
}
public int getRowNumber(){
    return rowNum;
}
public int getColNumber(){
    return colNum;
}

//setters
public void setType(Type newType){
    this.type = newType;
}
public void setLexeme(String newLexeme){
    this.Lexeme = newLexeme;
}
public void setRowNumber(int newRowNumber){
    this.rowNum = newRowNumber;
}
public void setColNumber(int newColNumber){
    this.colNum = newColNumber;
}
}

