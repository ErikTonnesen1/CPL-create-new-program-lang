package main.nonterminals;

public class NumNode extends FactorNode {

    int num;

    public NumNode(int num) {
        this.num = num;
    }

    @Override
    public int evaluate() {
        return num;

    }

}
