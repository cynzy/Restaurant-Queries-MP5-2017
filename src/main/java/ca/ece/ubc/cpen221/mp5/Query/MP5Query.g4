grammar MP5Query;


// lexical rules for parsing
AND : '&&' ;
OR : '||' ;
LPAREN : '(' ;
RPAREN : ')' ;
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
STR: [A-Za-z]+ ;


//token rules for parsing
query : expr <EOF> ;
expr: andExpr | orExpr ;
orExpr : andExpr(OR andExpr)* ;
andExpr : atom (AND atom)* ;
atom : in | category | rating | price | name | LPAREN orExpr RPAREN ;
ineq : GT | GTE | LT | LTE | EQ ;
in : IN LPAREN STR RPAREN ;
category : CATEGORY LPAREN STR RPAREN ;
name : NAME LPAREN STR RPAREN ;
rating : RATING ineq NUM ;
price : PRICE ineq NUM ;
