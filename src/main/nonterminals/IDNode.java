package main.nonterminals;

import java.util.HashMap;
import main.nonterminals.FactorNode;
import main.Memory.Memory;

public class IDNode extends FactorNode {
    String id;
    Memory memory;

    public IDNode(String id, Memory memory) {
        this.id = id;
        this.memory = memory;
    }

    @Override
    public int evaluate() {
        if (memory.contains(id)) {
            return memory.retrieve(id);
        } else {
            throw new RuntimeException("Breakdown at IDNode.java");
        }

    }
}
