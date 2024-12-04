package main.nonterminals;

import main.nonterminals.StatementNode;
import main.Memory.Memory;
import java.util.Scanner;

public class InputNode extends StatementNode {

    String id;
    Scanner sc;
    Memory mem;

    public InputNode(String id, Memory memInstance) {
        this.id = id;
        sc = new Scanner(System.in);
        mem = memInstance;

    }

    public void evaluate() {
        System.out.print("Enter value for " + id + ": ");
        // if not an int will crash
        try {
            int userInput = sc.nextInt();
            mem.store(id, userInput);
        } catch (Exception e) {
            System.out.println("[" + e + "] : Must Enter a number when prompted. Program will now crash.");
        }

    }

}
