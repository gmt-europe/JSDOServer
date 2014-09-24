package nl.gmt.jsdo.query.parser;

import org.apache.commons.lang.Validate;

public class BinaryExpression extends Expression {
    private final BinaryOperator operator;
    private final Expression left;
    private final Expression right;

    public BinaryExpression(BinaryOperator operator, Expression left, Expression right) {
        Validate.notNull(operator, "operator");
        Validate.notNull(left, "left");
        Validate.notNull(right, "right");

        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public BinaryOperator getOperator() {
        return operator;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public void accept(Visitor visitor) throws Exception {
        if (!visitor.isDone()) {
            visitor.visitBinaryExpression(this);
        }
    }

    @Override
    public <T> T accept(ActionVisitor<T> visitor) throws Exception {
        return visitor.visitBinaryExpression(this);
    }
}
