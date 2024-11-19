package main.parseTree;

import main.nonterminals.ExpressionNode;

public class parseTree {
    ExpressionNode expression;

    public parseTree(ExpressionNode root) {
        this.expression = root;
    }

    public int evaluate() {
        // implement later
        return expression.evaluate();
    }

}
