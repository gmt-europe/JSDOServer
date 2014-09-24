package nl.gmt.jsdo.query.parser;

public interface ActionVisitor<T> {
    T visitUnaryExpression(UnaryExpression expression) throws Exception;

    T visitNameExpression(NameExpression expression) throws Exception;

    T visitBinaryExpression(BinaryExpression expression) throws Exception;

    T visitLiteralExpression(LiteralExpression expression) throws Exception;

    T visitMatchesExpression(MatchesExpression expression) throws Exception;
}
