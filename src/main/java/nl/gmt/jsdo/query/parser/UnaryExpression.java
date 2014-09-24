package nl.gmt.jsdo.query.parser;

import org.apache.commons.lang.Validate;

public class UnaryExpression extends Expression {
    private final UnaryOperator operator;
    private final Expression operand;

    public UnaryExpression(UnaryOperator operator, Expression operand) {
        Validate.notNull(operator, "operator");
        Validate.notNull(operand, "operand");

        this.operator = operator;
        this.operand = operand;
    }

    public UnaryOperator getOperator() {
        return operator;
    }

    public Expression getOperand() {
        return operand;
    }

    @Override
    public void accept(Visitor visitor) throws Exception {
        if (!visitor.isDone()) {
            visitor.visitUnaryExpression(this);
        }
    }

    @Override
    public <T> T accept(ActionVisitor<T> visitor) throws Exception {
        return visitor.visitUnaryExpression(this);
    }
}
