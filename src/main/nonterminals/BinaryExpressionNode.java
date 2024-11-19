package main.nonterminals;

public class BinaryExpressionNode extends ExpressionNode {

    ExpressionNode expression;
    TermNode term;
    String operator;

    public BinaryExpressionNode(ExpressionNode expression, TermNode term, String operator) {

        this.expression = expression;
        this.term = term;
        this.operator = operator;

    }

    @Override
    public int evaluate() {

        /*
         * Other implementation:
         * int expressionVal = expression.evaluate();
         * int termVal = term.evaluate();
         *
         * switch(operator){
         * case "+":
         * return expressionVal + termVal;
         * case "-":
         * return expressionVal - termVal;
         * }
         *
         */

        switch (operator) {
            case "+":
                return expression.evaluate() + term.evaluate();
            case "-":
                return expression.evaluate() - term.evaluate();
            default:
                throw new IllegalArgumentException("Unexpected Operator ( binary expression)");
        }

    }

}
