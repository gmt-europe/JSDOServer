package nl.gmt.jsdo.demo;

import nl.gmt.jsdo.query.parser.*;

public class FilterVisitor implements ActionVisitor<String> {
    @Override
    public String visitUnaryExpression(UnaryExpression expression) throws Exception {
        String operator;
        String operand = expression.getOperand().accept(this);

        switch (expression.getOperator()) {
            case NOT:
                operator = "NOT";
                break;
            default:
                throw new IllegalStateException();
        }

        return operator + " " + operand;
    }

    @Override
    public String visitNameExpression(NameExpression expression) throws Exception {
        return "`" + expression.getIdentifier() + "`";
    }

    @Override
    public String visitBinaryExpression(BinaryExpression expression) throws Exception {
        boolean parenthesize = false;
        String operator;
        String left = expression.getLeft().accept(this);
        String right = expression.getRight().accept(this);

        switch (expression.getOperator()) {
            case AND:
                parenthesize = true;
                operator = "AND";
                break;
            case NE:
                operator = "<>";
                break;
            case EQ:
                operator = "=";
                break;
            case GT:
                operator = ">";
                break;
            case LT:
                operator = "<";
                break;
            case GE:
                operator = ">=";
                break;
            case LE:
                operator = "<=";
                break;
            case IS:
                operator = "IS";
                break;
            case OR:
                operator = "OR";
                parenthesize = true;
                break;
            default:
                throw new IllegalStateException();
        }

        if (parenthesize) {
            left = "(" + left + ")";
            right = "(" + right + ")";
        }

        return left + " " + operator + " " + right;
    }

    @Override
    public String visitLiteralExpression(LiteralExpression expression) throws Exception {
        switch (expression.getType()) {
            case NULL:
                return "NULL";
            case NOT_NULL:
                return "NOT NULL";
            default:
                return expression.getValue();
        }
    }

    @Override
    public String visitMatchesExpression(MatchesExpression expression) throws Exception {
        String left = expression.getLeft().accept(this);
        String right = parseString(expression.getRight());

        if (expression.getType() == MatchesType.BEGINS) {
            right += "*";
        }

        return String.format("%s LIKE '%s'", left, right.replace('*', '%').replace("'", "''"));
    }

    private String parseString(String text) {
        assert text.length() >= 2 && text.charAt(0) == '\'' && text.charAt(text.length() - 1) == '\'';

        return text.substring(1, text.length() - 1).replace("''", "'");
    }
}
