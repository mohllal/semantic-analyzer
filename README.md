# TinyJava-SemanticAnalyzer
A semantic analyzer for a subset of the Java programming language.

## Implemented compilation phases:
  1. Lexical analysis.
  2. Syntax analysis (Parsing).
  3. Semantic analysis.
 
## Installation:
You must [download](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html) and install JDK (Java Development Kit).

## Usage:
  1. Download or clone this repository
  2. Compile and run any Tester class (Testlexer, TestParser, or TestSemantic) with a command-line argument refers to a test file path.

```Java
javac Testlexer.java
java Testlexer test_file_path
```
```Java
javac TestParser.java
java TestParser test_file_path
```
```Java
javac TestSemantic.java
java TestSemantic test_file_path
```
**Note**: You can find some test files in the tests folder.

## Tokens (Lexemes) regular expression:
* Letter: [a-zA-Z]
* Digit: [0-9]
* Identifier: letter (letter | digit)*
* Integer: digit+
* Float: Integer . Integer
* Boolean: true | false
* Char: ‘ASCII Char‘
* Literal: Integer | Boolean | Float | Char
* ReservedWords: main | int | float | char| boolean | if | else | true | false | false | while
* AssignmentOperator: =
* EqualOperator: ==
* NotEqualOperator: !=
* RelationalOperator: <|<=|>|>= 
* ArithmeticOperator: + | - | * | / | %
* UnaryOperator: - | ! 
* BooleanOperator: && | ||
* Operator: EqualOperator| NotEqualOperator | RelationalOperator | ArithmeticOperator| UnaryOperator | BooleanOperator | AssignmentOperator
* RightBrace:  }
* LeftBrace: {  
* RightParenthese: ) 
* LeftParenthese: ( 
* RightBracket: ]  
* LeftBracket: [
* Semicolon: ;
* Comma: ,

## Grammer BNF notation:
* Program ::= int main () { Declarations Statements }
* Declarations ::= { Declaration }
* Declaration ::= Type Identifier [ [ Integer ] ] { , Identifier [ [ Integer ] ] }
* Type ::= int | bool | float | char
* Statements ::= {  Statement  }
* Statement ::= ; | Block | Assignment | IfStatement | WhileStatement
* Block ::= { Statements }
* Assignment ::= Identifier [ [ Expression ] ] = Expression;
* IfStatement ::= if ( Expression ) Statement [ else Statement ]
* WhileStatement ::= while ( Expression ) Statement
* Expression ::= Conjunction { || Conjunction }
* Conjunction ::= Equality { && Equality }
* Equality ::= Relation [ EquOp Relation ]
* EquOp ::= == | != 
* Relation ::=  Addition [ RelOp Addition]
* RelOp ::= < | <= | > | >=
* Addition ::= Term { AddOp Term }
* AddOp ::= + | -
* Term ::= Factor { MulOp Factor }
* MulOp ::= * | / | %
* Factor ::= [ UnaryOp ] Primary
* UnaryOP ::= - | !
* Primary ::= Identifier [ [Expression] ] | Literal | ( Expression ) | Type ( Expression)
* Identifier ::= Letter { Letter | Digit }
* Letter ::= a | b | … | z | A | B | … | Z
* Digit ::= 0 | 1 | … | 9
* Literal ::= Integer | Boolean | Float | Char
* Integer ::= Digit { Digit }
* Boolean ::= true | false
* Float ::= Integer.Integer
* Char ::= 'ASCIIChar'

## Semantic Rules:
* ##### Rule one:
  * All referenced variables must be declared.
  * All declared variables must have unique names.
* ##### Rule two: 
  An Assignment is valid if:
    1. Its target Variable is declared.
    2. Its source Expression is valid.
    3. If the target Variable is float, then the type of the source Expression must be either float or int.
    4. Otherwise if the target Variable is int, then the type of the source Expression must be either int or char.
    5. Otherwise the target Variable must have the same type as the source Expression.
