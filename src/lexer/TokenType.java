package lexer;

public enum TokenType {
	ID, // [a-zA-Z][a-zA-Z0-9_]*
	INT_CONST, // [0-9]+
	FLOAT_CONST, //[0-9]+.[0-9]+
	CHAR_CONST, //'ASCII Char'
	BOOLEAN_CONST,
	EOF, // input stream has been consumed
	UNKNOWN, // character/token could not be processed
	
	// binary operators
	AND, // &&
	OR, // ||
	EQ, // ==
	NEQ, // !=
	LT, // <
	RT, // >
	LT_EQ, // <=
	RT_EQ, // >=
	PLUS, // +
	MINUS, // -
	TIMES, // *
	DIV, // /
	MOD, // %

	// reserved words
	MAIN, // main - relegate as ID (?)
	INT, // int
	CHAR, // char
	FLOAT, // float
	BOOLEAN, // boolean
	IF, // if
	ELSE, // else
	WHILE, // while

	// punctuation
	LPAREN, // (
	RPAREN, // )
	LBRACKET, // [
	RBRACKET, // ]
	LBRACE, // {
	RBRACE, // }
	SEMI, // ;
	COMMA, // ,
	ASSIGN, // =
	NEGATIVE, // -
	NOT, // !

	// for error reporting
	STATEMENT,
	EXPRESSION,
	OPERATOR,
	TYPE
}