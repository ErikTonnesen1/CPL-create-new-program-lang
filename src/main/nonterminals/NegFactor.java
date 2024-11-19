package main.nonterminals;

public class NegFactor extends FactorNode {

    ExpressionNode expression;

    public NegFactor(ExpressionNode expr) {
        this.expression = expr;
    }

    @Override
    public int evaluate() {
        return -expression.evaluate();
    }

}
