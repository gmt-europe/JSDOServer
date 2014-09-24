package nl.gmt.jsdo.query.parser;

public interface Visitor {
    boolean isDone();

    void visitUnaryExpression(UnaryExpression expression) throws Exception;

    void visitNameExpression(NameExpression expression) throws Exception;

    void visitBinaryExpression(BinaryExpression expression) throws Exception;

    void visitLiteralExpression(LiteralExpression expression) throws Exception;

    void visitMatchesExpression(MatchesExpression expression) throws Exception;
}
