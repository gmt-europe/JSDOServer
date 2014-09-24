package nl.gmt.jsdo.query.parser;

import org.apache.commons.lang.Validate;

public class LiteralExpression extends Expression {
    private final String value;
    private final LiteralType type;

    public LiteralExpression(String value, LiteralType type) {
        Validate.isTrue(value != null || type == LiteralType.NULL || type == LiteralType.NOT_NULL);
        Validate.notNull(type, "type");

        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public LiteralType getType() {
        return type;
    }

    @Override
    public void accept(Visitor visitor) throws Exception {
        if (!visitor.isDone()) {
            visitor.visitLiteralExpression(this);
        }
    }

    @Override
    public <T> T accept(ActionVisitor<T> visitor) throws Exception {
        return visitor.visitLiteralExpression(this);
    }
}
