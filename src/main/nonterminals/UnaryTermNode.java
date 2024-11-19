package main.nonterminals;

public class UnaryTermNode extends TermNode {

    FactorNode factor;

    public UnaryTermNode(FactorNode factor) {
        this.factor = factor;
    }

    @Override
    public int evaluate() {
        return factor.evaluate();
    }

}
