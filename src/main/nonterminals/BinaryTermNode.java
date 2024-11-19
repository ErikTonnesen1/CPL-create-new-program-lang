package main.nonterminals;

public class BinaryTermNode extends TermNode {

    TermNode term;
    FactorNode factor;

    String operator;

    public BinaryTermNode(TermNode term, FactorNode factor, String operator) {
        this.term = term;
        this.factor = factor;
        this.operator = operator;
    }

    @Override
    public int evaluate() {

        // can use other implementation found in binaryExpressionNode

        switch (operator) {
            case "*":
                return term.evaluate() * factor.evaluate();
            case "/":
                return term.evaluate() / factor.evaluate();
            default:
                throw new IllegalArgumentException("Unexpected Operator ( binary Term)");

        }

    }

}
