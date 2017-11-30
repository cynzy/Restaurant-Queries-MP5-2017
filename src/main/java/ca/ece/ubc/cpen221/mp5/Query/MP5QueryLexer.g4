lexer grammar MP5QueryLexer;

// lexical rules for parsing
AND : '&&' ;
OR : '||' ;
LPAREN : '('    -> mode(WORD) ;
GT : '>' ;
GTE : '>=' ;
LT : '<' ;
LTE : '<=' ;
EQ : '=' ;
NUM : [1-5] ;
IN : 'in' ;
CATEGORY : 'category' ;
NAME : 'name' ;
RATING : 'rating' ;
PRICE : 'price' ;
WS : [ /t]+ -> skip ;

mode WORD;
STR : [A-Za-z ]+ ;
RPAREN : ')' -> mode(DEFAULT_MODE);

