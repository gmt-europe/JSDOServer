package nl.gmt.jsdo.query.parser;

public abstract class Expression {
    public abstract void accept(Visitor visitor) throws Exception;

    public abstract <T> T accept(ActionVisitor<T> visitor) throws Exception;
}
