grammar AblFilter;

options {
    backtrack = true;
    memoize = true;
}

@header {
    package nl.gmt.jsdo.query.parser;
}

@lexer::header {
    package nl.gmt.jsdo.query.parser;
}

////////////////////////////////////////////////////////////////////////////////
// PARSER                                                                     //
////////////////////////////////////////////////////////////////////////////////

filter returns [Expression value]
    : e=expression EOF { value = e; }
    ;

expression returns [Expression value]
    : loe=logicalOrExpression { value = loe; }
    | pe=parenExpression { value = pe; }
    ;

parenExpression returns [Expression value]
    :
        (
            OP_PAREN_LEFT e=expression OP_PAREN_RIGHT
            { value = e; }
        )
    ;

logicalOrExpression returns [Expression value]
    :
        e=logicalAndExpression
        { value = e; }
        (
            KW_OR
            e=logicalAndExpression
            { value = new BinaryExpression(BinaryOperator.OR, value, e); }
        )*
    ;

logicalAndExpression returns [Expression value]
    :
        e=logicalNotExpression
        { value = e; }
        (
            KW_AND
            e=logicalNotExpression
            { value = new BinaryExpression(BinaryOperator.AND, value, e); }
        )*
    ;

logicalNotExpression returns [Expression value]
@init {
    boolean hadNot = false;
}
    :
        ( KW_NOT { hadNot = true; } )?
        p=precicate
        {
            if (hadNot) {
                value = new UnaryExpression(UnaryOperator.NOT, p);
            } else {
                value = p;
            }
        }
    ;

precicate returns [Expression value]
    : be=binaryExpression { value = be; }
    | me=matchesExpression { value = me; }
    | pe=parenExpression { value = pe; }
    ;

binaryExpression returns [Expression value]
    :
        e=operand
        { value = e; }
        (
            bo=binaryOperator
            e=operand
            { value = new BinaryExpression(bo, value, e); }
        )?
    ;

binaryOperator returns [BinaryOperator value]
    : OP_NE { value = BinaryOperator.NE; }
    | OP_EQ { value = BinaryOperator.EQ; }
    | OP_GT { value = BinaryOperator.GT; }
    | OP_LT { value = BinaryOperator.LT; }
    | OP_GE { value = BinaryOperator.GE; }
    | OP_LE { value = BinaryOperator.LE; }
    | KW_IS { value = BinaryOperator.IS; }
    ;

matchesExpression returns [Expression value]
@init {
    MatchesType type = null;
}
    :
        e=operand
        (
            KW_BEGINS
            { type = MatchesType.BEGINS; }
        |
            KW_MATCHES
            { type = MatchesType.MATCHES; }
        )
        s=STRING
        { value = new MatchesExpression(type, e, s.getText()); }
    ;

operand returns [Expression value]
    : l=literal { value = l; }
    | i=IDENTIFIER { value = new NameExpression(i.getText()); }
    ;

literal returns [LiteralExpression value]
    : s=STRING { value = new LiteralExpression(s.getText(), LiteralType.STRING); }
    | i=INTEGER { value = new LiteralExpression(i.getText(), LiteralType.INTEGER); }
    | r=REAL { value = new LiteralExpression(r.getText(), LiteralType.REAL); }
    | KW_NULL { value = new LiteralExpression(null, LiteralType.NULL); }
    | KW_NOT KW_NULL { value = new LiteralExpression(null, LiteralType.NOT_NULL); }
    ;

////////////////////////////////////////////////////////////////////////////////
// LEXER                                                                      //
////////////////////////////////////////////////////////////////////////////////

OP_EQ : '=' ;
OP_NE : '<>' ;
OP_LT : '<' ;
OP_LE : '<=' ;
OP_GT : '>' ;
OP_GE : '>=' ;
OP_PAREN_LEFT : '(' ;
OP_PAREN_RIGHT : ')' ;

KW_MATCHES : 'MATCHES' ;
KW_AND : 'AND' ;
KW_BEGINS : 'BEGINS' ;
KW_NOT : 'NOT' ;
KW_IS : 'IS' ;
KW_OR : 'OR' ;
KW_NULL : 'NULL' ;

fragment
Exponent
    : ( 'e' | 'E' ) ( '+' | '-' )? Digit+
    ;

fragment
Digit
    : '0'..'9'
    ;

fragment
Letter
    : 'a'..'z' | 'A'..'Z'
    ;

fragment
INTEGER : ;

REAL
    :
        Digit+
        (
            ( '.' Digit+ Exponent? ) => '.' Digit+ Exponent?
        |
            { $type = INTEGER; }
        )
    ;


STRING
    : '\'' ( '\'\'' | ~( '\'' ) )* '\''
    ;

IDENTIFIER
    :
        ( Letter | '_' )
        ( Letter | Digit | '_' )*
    ;

Space
    :
        ( ' ' | '\r' | '\t' | '\u000C' | '\n' )
        { skip(); }
    ;
