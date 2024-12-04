package main.nonterminals;

import main.Memory.Memory;
import main.nonterminals.ExpressionNode;

public class AssignmentNode extends StatementNode {

    String id;
    Memory memInstance;
    ExpressionNode expression;

    public AssignmentNode(String id, Memory memInstance, ExpressionNode expression) {
        this.id = id;
        this.memInstance = memInstance;
        this.expression = expression;
    }

    @Override
    public void evaluate() {
        memInstance.store(id, expression.evaluate());

    }
}
