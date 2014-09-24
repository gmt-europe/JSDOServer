package nl.gmt.jsdo.query.parser;

import org.apache.commons.lang.Validate;

public class MatchesExpression extends Expression {
    private final MatchesType type;
    private final Expression left;
    private final String right;

    public MatchesExpression(MatchesType type, Expression left, String right) {
        Validate.notNull(type, "type");
        Validate.notNull(left, "left");
        Validate.notNull(right, "right");

        this.type = type;
        this.left = left;
        this.right = right;
    }

    public MatchesType getType() {
        return type;
    }

    public Expression getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    @Override
    public void accept(Visitor visitor) throws Exception {
        if (!visitor.isDone()) {
            visitor.visitMatchesExpression(this);
        }
    }

    @Override
    public <T> T accept(ActionVisitor<T> visitor) throws Exception {
        return visitor.visitMatchesExpression(this);
    }
}
