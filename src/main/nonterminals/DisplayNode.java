package main.nonterminals;

import main.nonterminals.StatementNode;
import main.Memory.Memory;

public class DisplayNode extends StatementNode {

    String id;
    Memory mem;

    public DisplayNode(String id, Memory memInstance) {
        this.id = id;
        this.mem = memInstance;
    }

    public void evaluate() {
        System.out.println(mem.retrieve(id));
    }
}
