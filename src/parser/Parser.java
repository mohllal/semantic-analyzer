package parser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ast.*;
import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;

public class Parser {
	private Lexer lexer;
	private Token token;
	private Token errorToken;

	// hash table for operator precedence levels
	private final static Map<TokenType, Integer> binopLevels;
	
	private ArrayList <VarDecl> decelarations; //declarations symbol table
	private ArrayList <Identifier> identifiers; //identifiers symbol table
	private ArrayList <Assign> assigns; //assigns symbol table
	private ArrayList <Exp> conditions; //conditions symbol table
	
	private int errors;

	static {
		binopLevels = new HashMap<TokenType, Integer>();
		binopLevels.put(TokenType.AND, 10);
		binopLevels.put(TokenType.OR, 10);
		binopLevels.put(TokenType.LT, 20);
		binopLevels.put(TokenType.RT, 20);
		binopLevels.put(TokenType.LT_EQ, 20);
		binopLevels.put(TokenType.RT_EQ, 20);
		binopLevels.put(TokenType.EQ, 20);
		binopLevels.put(TokenType.NEQ, 20);
		binopLevels.put(TokenType.PLUS, 30);
		binopLevels.put(TokenType.MINUS, 30);
		binopLevels.put(TokenType.TIMES, 40);
		binopLevels.put(TokenType.DIV, 40);
		binopLevels.put(TokenType.MOD, 40);
		binopLevels.put(TokenType.LBRACKET, 50);
	}

	public Parser(FileReader file) throws IOException {
		this.lexer = new Lexer(file);
		this.token = lexer.getToken();
		this.decelarations = new ArrayList<VarDecl>();
		this.identifiers = new ArrayList<Identifier>();
		this.assigns = new ArrayList<Assign>();
		this.conditions = new ArrayList<Exp>();
	}

	// verifies current token type and grabs next token or reports error
	private boolean eat(TokenType type) throws IOException {
		if (token.getType() == type) {
			token = lexer.getToken();
			return true;
		} else {
			error(type);
			return false;
		}
	}

	// reports an error to the console
	private void error(TokenType type) {
		// only report error once per erroneous token
		if (token == errorToken)
			return;

		// print error report
		System.err.print("ERROR: " + token.getType());
		System.err.print(" at line " + token.getLineNumber() + ", column " + token.getColumnNumber());
		System.err.println("; Expected " + type);
				
		errorToken = token; // set error token to prevent cascading
		errors++; // increment error counter
	}

	// skip tokens until match in follow set for error recovery
	private void skipTo(TokenType... follow) throws IOException {
		while (token.getType() != TokenType.EOF) {
			for (TokenType skip : follow) {
				if (token.getType() == skip)
					return;
			}
			token = lexer.getToken();
		}
	}

	// number of reported syntax errors
	public int getErrors() {
		return errors;
	}

	public ArrayList <VarDecl> getDecelarations() {
		return decelarations;
	}

	public ArrayList <Identifier> getIdentifiers() {
		return identifiers;
	}

	public ArrayList <Assign> getAssigns() {
		return assigns;
	}

	public ArrayList <Exp> getConditions() {
		return conditions;
	}

	// Program ::= int main '('')' { Declarations StatementList }
	public Program parseProgram() throws IOException {		
		eat(TokenType.INT);
		eat(TokenType.MAIN);
		eat(TokenType.LPAREN);
		eat(TokenType.RPAREN);
		eat(TokenType.LBRACE);

		Declarations declarations = parseDeclarations();
		StatementList statementList = parseStatementList();

		eat(TokenType.RBRACE);
		eat(TokenType.EOF);
		return new Program(statementList, declarations);
	}

	// Declarations ::= { VarDeclList }
	private Declarations parseDeclarations() throws IOException{
		Declarations declarations = new Declarations();

		while(token.getType() == TokenType.INT || token.getType() == TokenType.FLOAT
				|| token.getType() == TokenType.BOOLEAN || token.getType() == TokenType.CHAR)
			declarations.addElement(parseVarDecList());

		return declarations;
	}

	// VarDeclList ::= VarDecl { , Identifier };
	private VarDeclList parseVarDecList() throws IOException{
		VarDeclList varDeclList = new VarDeclList();
		VarDecl varDecl = parseVarDecl();
		varDeclList.addElement(varDecl);
		getDecelarations().add(varDecl);

		// check for additional varDecl
		while (token.getType() == TokenType.COMMA) {
			eat(TokenType.COMMA);
			VarDecl newVarDecl = new VarDecl(varDecl.getType(), parseIdentifier());
			varDeclList.addElement(newVarDecl);
			getDecelarations().add(newVarDecl);
		}
		eat(TokenType.SEMI);

		return varDeclList;
	}

	// VarDecl ::= Type Identifier
	private VarDecl parseVarDecl() throws IOException {
		Type type = parseType();
		Identifier id = parseIdentifier();
		return new VarDecl(type, id);
	}

	/*
	 * Type ::= int | int '['integer']' | float | float'['integer']' | boolean | boolean'['integer']' | char | char'['integer']'
	 * int (IntegerType)
	 * int [integer] (IntArrayType)
	 * float (FloatType)
	 * float[integer] (FloatArrayType)
	 * boolean (BooleanType)
	 * boolean[integer] (BooleanArrayType)
	 */
	private Type parseType() throws IOException {
		switch (token.getType()) {

		case INT:
			eat(TokenType.INT);

			// check for integer array type
			if (token.getType() == TokenType.LBRACKET) {
				eat(TokenType.LBRACKET);

				// check array size integer
				if(eat(TokenType.INT_CONST)){
					if (token.getType() == TokenType.RBRACKET) {
						eat(TokenType.RBRACKET);
						return new IntegerArrayType();
					}
				}

				// invalid integer type declaration
				eat(TokenType.TYPE);
				return null;
			}
			return new IntegerType();

		case FLOAT:
			eat(TokenType.FLOAT);

			// check for integer array type
			if (token.getType() == TokenType.LBRACKET) {
				eat(TokenType.LBRACKET);

				// check array size integer
				if(eat(TokenType.INT_CONST)){
					if (token.getType() == TokenType.RBRACKET) {
						eat(TokenType.RBRACKET);
						return new FloatArrayType();
					}
				}

				// invalid integer type declaration
				eat(TokenType.TYPE);
				return null;
			}
			return new FloatType();

		case BOOLEAN:
			eat(TokenType.BOOLEAN);

			// check for integer array type
			if (token.getType() == TokenType.LBRACKET) {
				eat(TokenType.LBRACKET);

				// check array size integer
				if(eat(TokenType.INT_CONST)){
					if (token.getType() == TokenType.RBRACKET) {
						eat(TokenType.RBRACKET);
						return new BooleanArrayType();
					}
				}

				// invalid integer type declaration
				eat(TokenType.TYPE);
				return null;
			}
			return new BooleanType();

		case CHAR:
			eat(TokenType.CHAR);

			// check for integer array type
			if (token.getType() == TokenType.LBRACKET) {
				eat(TokenType.LBRACKET);

				// check array size integer
				if(eat(TokenType.INT_CONST)){
					if (token.getType() == TokenType.RBRACKET) {
						eat(TokenType.RBRACKET);
						return new CharArrayType();
					}
				}

				// invalid integer type declaration
				eat(TokenType.TYPE);
				return null;
			}
			return new CharType();

		default:
			// unknown type
			eat(TokenType.TYPE);
			return null;

		}
	}

	// Identifier ::= Letter { Letter | Digit }
	private Identifier parseIdentifier() throws IOException {
		Identifier identifier = null;

		// grab ID value if token type is ID
		if (token.getType() == TokenType.ID)
			identifier = new Identifier(token.getAttribute().getIdVal());
		
		eat(TokenType.ID);

		return identifier;
	}

	// StatementList ::= { Statement }
	private StatementList parseStatementList() throws IOException{
		StatementList statementList = new StatementList();
		while (isStatement())
			statementList.addElement(parseStatement());
		return statementList;
	}

	// checks the beginning of a new statement 
	private boolean isStatement() {
		switch(token.getType()){
		case SEMI :
		case IF :
		case WHILE :
		case LPAREN :
		case LBRACE:
		case ID :
			return  true;
		default:
			return false;
		}
	}

	// Statement ::= Block | IfStatement | WhileStatement | identifier = Exp | identifier '['Exp']' = Exp
	private Statement parseStatement() throws IOException {

		// IfStatement ::=  if '('Exp')' Statement [else Statement]
		if (token.getType() == TokenType.IF) {
			eat(TokenType.IF);

			// parse conditional expression
			if (!eat(TokenType.LPAREN))
				skipTo(TokenType.RPAREN, TokenType.LBRACE, TokenType.RBRACE);

			Exp condExp = parseExp();
			conditions.add(condExp);
			
			/*if(condExp instanceof IdentifierExp){
				IdentifierExp idExp = (IdentifierExp) condExp;
				Identifier identifier = new Identifier(idExp.getName());
				identifiers.add(identifier);
			}*/

			if (!eat(TokenType.RPAREN))
				skipTo(TokenType.LBRACE, TokenType.SEMI, TokenType.RBRACE);

			// parse true and false statements
			Statement trueStm;

			// BLock ::= '{' StatementList '}' 
			if (token.getType() == TokenType.LBRACE)
				trueStm = parseBlock();

			else
				// parse true statement
				trueStm = parseStatement();

			if (token.getType() == TokenType.ELSE){
				if (!eat(TokenType.ELSE))
					skipTo(TokenType.LBRACE, TokenType.SEMI, TokenType.RBRACE);

				Statement falseStm;

				// BLock ::= '{' StatementList '}' 
				if (token.getType() == TokenType.LBRACE)
					falseStm = parseBlock();

				else
					// parse false statement
					falseStm = parseStatement();

				return new If(condExp, trueStm, falseStm);
			}
			return new If(condExp, trueStm, null);
		}

		// WhileStatement ::= while '('Exp')' Statement
		if (token.getType() == TokenType.WHILE) {
			eat(TokenType.WHILE);

			// parse looping condition
			if (!eat(TokenType.LPAREN))
				skipTo(TokenType.RPAREN, TokenType.LBRACE, TokenType.RBRACE);

			Exp condExp = parseExp();
			conditions.add(condExp);
			
			/*if(condExp instanceof IdentifierExp){
				IdentifierExp idExp = (IdentifierExp) condExp;
				Identifier identifier = new Identifier(idExp.getName());
				identifiers.add(identifier);
			}*/

			if (!eat(TokenType.RPAREN))
				skipTo(TokenType.LBRACE, TokenType.SEMI, TokenType.RBRACE);

			Statement loopStm;

			// BLock ::= '{' StatementList '}' 
			if (token.getType() == TokenType.LBRACE)
				loopStm = parseBlock();

			else
				// parse looping statement
				loopStm = parseStatement();

			return new While(condExp, loopStm);
		}

		// Identifier statement
		if (token.getType() == TokenType.ID) {

			Identifier id = new Identifier(token.getAttribute().getIdVal());
			identifiers.add(id);
			eat(TokenType.ID);


			// Assignment statement: id = Exp ;
			if (token.getType() == TokenType.ASSIGN) {
				eat(TokenType.ASSIGN);
				Exp value = parseExp();
				
				/*if(value instanceof IdentifierExp){
					IdentifierExp idExp = (IdentifierExp) value;
					Identifier identifier = new Identifier(idExp.getName());
					identifiers.add(identifier);
				}*/
				
				eat(TokenType.SEMI);

				Assign assign = new Assign(id, value);
				assigns.add(assign);
				return assign;
			}

			// Array value assignment statement: id [ Exp ] = Exp ;
			if (token.getType() == TokenType.LBRACKET) {
				eat(TokenType.LBRACKET);
				Exp index = parseExp();
				
				if(!(index instanceof IntegerLiteral)){
					// statement type unknown
					eat(TokenType.TYPE);
					token = lexer.getToken();
					return null;
				}

				if (!eat(TokenType.RBRACKET))
					skipTo(TokenType.ASSIGN, TokenType.SEMI);

				if (!eat(TokenType.ASSIGN))
					skipTo(TokenType.SEMI);

				Exp value = parseExp();
				
				/*if(value instanceof IdentifierExp){
					IdentifierExp idExp = (IdentifierExp) value;
					Identifier identifier = new Identifier(idExp.getName());
					identifiers.add(identifier);
				}*/
				
				eat(TokenType.SEMI);
				
				Assign assign = new Assign(id, value);
				assigns.add(assign);
				return new ArrayAssign(id, index, value);
			}
		}

		// statement type unknown
		eat(TokenType.STATEMENT);
		token = lexer.getToken();
		return null;
	}

	// BLock ::= '{' StatementList '}'
	// Block ::= '{' StatementList '}'
	private Block parseBlock() throws IOException{
		eat(TokenType.LBRACE);

		// recursively call parseStatement() until closing brace
		StatementList stms = new StatementList();
		while (token.getType() != TokenType.RBRACE && token.getType() != TokenType.EOF)
			stms.addElement(parseStatement());

		if (!eat(TokenType.RBRACE)) 
			skipTo(TokenType.RBRACE, TokenType.SEMI);

		return new Block(stms);
	}

	// Exp ::= PrimaryExp | BinopRHS
	// top-level parsing function for an expression
	private Exp parseExp() throws IOException {
		Exp lhs = parsePrimaryExp();
		return parseBinopRHS(0, lhs); // check for binops following exp
	}

	// parsePrimaryExp ::= INT_CONST | FLOAT_CONST | CHAR_CONST | BOOLEAN_CONST | NEGATIVE | NOT | Identifier
	// parse exp before any binop
	private Exp parsePrimaryExp() throws IOException {
		switch (token.getType()) {

		case INT_CONST:
			int intValue = token.getAttribute().getIntVal();
			eat(TokenType.INT_CONST);
			return new IntegerLiteral(intValue);

		case FLOAT_CONST:
			float floatValue = token.getAttribute().getFloatVal();
			eat(TokenType.FLOAT_CONST);
			return new FloatLiteral(floatValue);

		case BOOLEAN_CONST:
			boolean booleanVal = token.getAttribute().getBooleanVal();
			eat(TokenType.BOOLEAN_CONST);
			return new BooleanLiteral(booleanVal);

		case CHAR_CONST:
			char charVal = token.getAttribute().getCharVal();
			eat(TokenType.CHAR_CONST);
			return new CharLiteral(charVal);

		case ID:
			Identifier id = parseIdentifier();
			identifiers.add(id);
			return new IdentifierExp(id.getName());

		case NOT:
			eat(TokenType.NOT);
			return new Not(parseExp());

		case NEGATIVE:
			eat(TokenType.NEGATIVE);
			return new Negative(parseExp());

		case LPAREN:
			eat(TokenType.LPAREN);
			Exp exp = parseExp();
			eat(TokenType.RPAREN);
			return exp;

		default:
			// unrecognizable expression
			eat(TokenType.EXPRESSION);
			token = lexer.getToken();
			return null;
		}
	}

	// parse expressions according to operator precedence levels
	private Exp parseBinopRHS(int level, Exp lhs) throws IOException {
		// continuously parse exp until a lower order operator comes up
		while (true) {
			// grab operator precedence (-1 for non-operator token)
			Integer val = binopLevels.get(token.getType());
			int tokenLevel = (val != null) ? val.intValue() : -1;

			// either op precedence is lower than prev op or token is not an op
			if (tokenLevel < level)
				return lhs;

			// save binop before parsing rhs of exp
			TokenType binop = token.getType();
			eat(binop);

			Exp rhs = parsePrimaryExp(); // parse rhs of exp

			// grab operator precedence (-1 for non-operator token)
			val = binopLevels.get(token.getType());
			int nextLevel = (val != null) ? val.intValue() : -1;

			// if next op has higher precedence than prev op, make recursive call
			if (tokenLevel < nextLevel)
				rhs = parseBinopRHS(tokenLevel + 1, rhs);

			// build AST for exp
			switch (binop) {
			case AND:
				lhs = new And(lhs, rhs);
				break;
			case OR:
				lhs = new Or(lhs, rhs);
				break;
			case EQ:
				lhs = new Equal(lhs, rhs);
				break;
			case NEQ:
				lhs = new NotEqual(lhs, rhs);
				break;
			case LT:
				lhs = new LessThan(lhs, rhs);
				break;
			case RT:
				lhs = new MoreThan(lhs, rhs);
				break;
			case LT_EQ:
				lhs = new LessThanEqual(lhs, rhs);
				break;
			case RT_EQ:
				lhs = new MoreThanEqual(lhs, rhs);
				break;
			case PLUS:
				lhs = new Plus(lhs, rhs);
				break;
			case MINUS:
				lhs = new Minus(lhs, rhs);
				break;
			case TIMES:
				lhs = new Times(lhs, rhs);
				break;
			case DIV:
				lhs = new Divide(lhs, rhs);
				break;
			case MOD:
				lhs = new Modules(lhs, rhs);
				break;
			case LBRACKET:
				lhs = new ArrayLookup(lhs, rhs);
				eat(TokenType.RBRACKET);
				break;
			default:
				eat(TokenType.OPERATOR);
				break;
			}
		}
	}

}