import main.LexicalAnalyzer.LexicalAnalyzer;
import main.parser.*;
import main.Token.Token;
import main.Memory.Memory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// #########################################
// TIPS ON HOW TO RUN
// use command: java -cp bin main/Main.java main/test.txt 

// NOTE:
//    - interchange test.txt with any txt file  
//    - must be in src directory or else will have to change file paths
// // #########################################

//Missing File handling on line 58

public class Main {
    public static void main(String[] args) {
        try {

            // use file from command Line
            // missing file argument caught at catch block down below

            File file = new File(args[0]);
            FileReader sc = new FileReader(file);

            // arraylist for chars of file
            ArrayList<Character> chars = new ArrayList<Character>();

            // parse through files chars
            int character;
            while ((character = sc.read()) != -1) {
                chars.add((char) character);
            }
            sc.close();

            // create full String from chars
            String expression = getStringRepresentation(chars);

            // create Lexical analyzer object and pass expression from file
            System.out.println("");

            LexicalAnalyzer first = new LexicalAnalyzer(expression);
            Memory newMemory = new Memory();
            // get tokens

            // Parser creation
            Parser parser = new Parser(first, newMemory);

            // For testing purposes
            // System.out.println("**********LEXICAL ANALYZER************");
            // first.printTokens();
            // System.out.println("**********LEXICAL ANALYZER************");
            // returns valid or invalid code segment
            parser.parse();

            // file passing exceptions
        } catch (IOException e) {
            System.out.println("File not Found");
        }
    }

    // // EOS token handling
    // } catch (IndexOutOfBoundsException e) {
    // System.out.println("\n*******ERROR***********");
    // System.out.println("Missing EOS token in File (space).");
    // System.out.println("*******ERROR***********\n");
    // System.out.println(e.getMessage());
    //
    // }

    // turning Arraylist of chars into one string
    public static String getStringRepresentation(ArrayList<Character> list) {
        StringBuilder builder = new StringBuilder(list.size());
        for (Character ch : list) {
            builder.append(ch);
        }
        return builder.toString();
    }

}
