package nl.gmt.jsdo.query.parser;

import org.apache.commons.lang.Validate;

public class NameExpression extends Expression {
    private final String identifier;

    public NameExpression(String identifier) {
        Validate.notNull(identifier, "identifier");

        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public void accept(Visitor visitor) throws Exception {
        if (!visitor.isDone()) {
            visitor.visitNameExpression(this);
        }
    }

    @Override
    public <T> T accept(ActionVisitor<T> visitor) throws Exception {
        return visitor.visitNameExpression(this);
    }
}
