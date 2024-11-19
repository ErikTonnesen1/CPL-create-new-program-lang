package main.nonterminals;

public class ParenFactorNode extends FactorNode {

    ExpressionNode expression;

    public ParenFactorNode(ExpressionNode expression) {
        this.expression = expression;
    }

    @Override
    public int evaluate() {

        return expression.evaluate();

    }

}
