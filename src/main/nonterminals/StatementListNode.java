package main.nonterminals;

import main.nonterminals.StatementNode;

public class StatementListNode extends ProgramNode {
    private StatementNode first;
    private StatementListNode next;

    public StatementListNode(StatementNode first, StatementListNode next) {
        this.first = first;
        this.next = next;
    }

    @Override
    public void evaluate() {
        first.evaluate();
        if (next != null) {
            next.evaluate();
        }
    }

}
