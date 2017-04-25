package visitor;
import ast.*;

/*
 * 
 * Printing for abstract syntax trees. Allows verification that the
 * trees for a program's syntax were properly constructed during the parsing 
 * stage. It is utilized by the TestParser class in MiniJava/test.
 * 
 */

public class PrintVisitor implements Visitor {
	@Override
	public void visit(Program main) {
		if(main.getList() != null) main.getList().accept(this);
		System.out.println();
	}
	
	@Override
	public void visit(Declarations decl){
		if(decl.getList() != null){
			for (VarDeclList varDeclList : decl.getList()) {
				for(VarDecl varDecl : varDeclList.getList())
					System.out.println(varDecl);
			}
		}
		System.out.println();
	}

	@Override
	public void visit(VarDecl var) {
		if (var.getType() != null) var.getType().accept(this);
		System.out.print(" ");
		if (var.getId() != null) var.getId().accept(this);
		System.out.print(";");
	}

	@Override
	public void visit(VarDeclList varList) {
		if (varList.getList() != null) varList.accept(this);
		System.out.println();
	}

	@Override
	public void visit(Formal param) {
		if (param.getType() != null) param.getType().accept(this);
		System.out.print(" ");
		if (param.getId() != null) param.getId().accept(this);
	}

	@Override
	public void visit(IntegerArrayType intArrayT) {
		System.out.print("int[]");
	}

	@Override
	public void visit(FloatArrayType floatArrayT) {
		System.out.print("float[]");
	}

	@Override
	public void visit(BooleanType boolT) {
		System.out.print("boolean");
	}

	@Override
	public void visit(IntegerType intT) {
		System.out.print("int");
	}

	@Override
	public void visit(FloatType floatT) {
		System.out.print("float");
	}
	
	@Override
	public void visit(BooleanLiteral booleanLiteral){
		System.out.println(booleanLiteral.getValue());
	}

	@Override
	public void visit(BooleanArrayType booleanArrayT){
		System.out.print("boolean[]");
	}

	@Override
	public void visit(CharLiteral charLiteral){
		System.out.println(charLiteral.getValue());
	}

	@Override
	public void visit(CharArrayType charArrayT){
		System.out.print("char[]");
	}

	@Override
	public void visit(CharType charT){
		System.out.print("char");
	}

	@Override
	public void visit(IdentifierType idT) {
		if (idT.getName() != null)
			System.out.print(idT.getName());
	}

	@Override
	public void visit(Block blockStm) {
		System.out.println("{");

		if (blockStm.getStms() != null) {
			for (int i = 0; i < blockStm.getStms().size(); i++) {
				if (blockStm.getStms().elementAt(i) == null)
					continue;

				System.out.print("\t\t\t");
				blockStm.getStms().elementAt(i).accept(this);
				System.out.println();
			}
		}

		System.out.println("\t\t}");
	}

	@Override
	public void visit(If ifStm) {
		System.out.print("if (");
		if (ifStm.getCondExp() != null) ifStm.getCondExp().accept(this);
		System.out.println(")");

		System.out.print("\t\t\t");
		if (ifStm.getTrueStm() != null) ifStm.getTrueStm().accept(this);
		System.out.println();

		System.out.println("\t\telse");
		System.out.print("\t\t\t");
		if (ifStm.getFalseStm() != null) ifStm.getFalseStm().accept(this);
	}

	@Override
	public void visit(While whileStm) {
		System.out.print("while (");
		if (whileStm.getCondExp() != null) whileStm.getCondExp().accept(this);
		System.out.print(")");
		if (whileStm.getStm() != null) whileStm.getStm().accept(this);
	}

	@Override
	public void visit(Assign assignStm) {
		if (assignStm.getId() != null) assignStm.getId().accept(this);
		System.out.print(" = ");
		if (assignStm.getValue() != null) assignStm.getValue().accept(this);
		System.out.print(";");
	}

	@Override
	public void visit(ArrayAssign arrayAssignStm) {
		if (arrayAssignStm.getId() != null) arrayAssignStm.getId().accept(this);
		System.out.print("[");
		if (arrayAssignStm.getIndex() != null) arrayAssignStm.getIndex().accept(this);
		System.out.print("] = ");
		if (arrayAssignStm.getValue() != null) arrayAssignStm.getValue().accept(this);
		System.out.print(";");
	}

	@Override
	public void visit(And andExp) {
		System.out.print("(");
		if (andExp.getLHS() != null) andExp.getLHS().accept(this);
		System.out.print(" && ");
		if (andExp.getRHS() != null) andExp.getRHS().accept(this);
		System.out.print(")");
	}

	@Override
	public void visit(Or andExp) {
		System.out.print("(");
		if (andExp.getLHS() != null) andExp.getLHS().accept(this);
		System.out.print(" || ");
		if (andExp.getRHS() != null) andExp.getRHS().accept(this);
		System.out.print(")");
	}

	@Override
	public void visit(Equal andExp) {
		System.out.print("(");
		if (andExp.getLHS() != null) andExp.getLHS().accept(this);
		System.out.print(" == ");
		if (andExp.getRHS() != null) andExp.getRHS().accept(this);
		System.out.print(")");
	}

	@Override
	public void visit(NotEqual andExp) {
		System.out.print("(");
		if (andExp.getLHS() != null) andExp.getLHS().accept(this);
		System.out.print(" != ");
		if (andExp.getRHS() != null) andExp.getRHS().accept(this);
		System.out.print(")");
	}

	@Override
	public void visit(MoreThan andExp) {
		System.out.print("(");
		if (andExp.getLHS() != null) andExp.getLHS().accept(this);
		System.out.print(" > ");
		if (andExp.getRHS() != null) andExp.getRHS().accept(this);
		System.out.print(")");
	}

	@Override
	public void visit(MoreThanEqual andExp) {
		System.out.print("(");
		if (andExp.getLHS() != null) andExp.getLHS().accept(this);
		System.out.print(" >= ");
		if (andExp.getRHS() != null) andExp.getRHS().accept(this);
		System.out.print(")");
	}

	@Override
	public void visit(LessThan lessThanExp) {
		System.out.print("(");
		if (lessThanExp.getLHS() != null) lessThanExp.getLHS().accept(this);
		System.out.print(" < ");
		if (lessThanExp.getRHS() != null) lessThanExp.getRHS().accept(this);
		System.out.print(")");
	}

	@Override
	public void visit(LessThanEqual andExp) {
		System.out.print("(");
		if (andExp.getLHS() != null) andExp.getLHS().accept(this);
		System.out.print(" <= ");
		if (andExp.getRHS() != null) andExp.getRHS().accept(this);
		System.out.print(")");
	}

	@Override
	public void visit(Plus plusExp) {
		System.out.print("(");
		if (plusExp.getLHS() != null) plusExp.getLHS().accept(this);
		System.out.print(" + ");
		if (plusExp.getRHS() != null) plusExp.getRHS().accept(this);
		System.out.print(")");
	}

	@Override
	public void visit(Minus minusExp) {
		System.out.print("(");
		if (minusExp.getLHS() != null) minusExp.getLHS().accept(this);
		System.out.print(" - ");
		if (minusExp.getRHS() != null) minusExp.getRHS().accept(this);
		System.out.print(")");
	}

	@Override
	public void visit(Times timesExp) {
		System.out.print("(");
		if (timesExp.getLHS() != null) timesExp.getLHS().accept(this);
		System.out.print(" * ");
		if (timesExp.getRHS() != null) timesExp.getRHS().accept(this);
		System.out.print(")");
	}

	@Override
	public void visit(Divide timesExp) {
		System.out.print("(");
		if (timesExp.getLHS() != null) timesExp.getLHS().accept(this);
		System.out.print(" / ");
		if (timesExp.getRHS() != null) timesExp.getRHS().accept(this);
		System.out.print(")");
	}

	@Override
	public void visit(Modules timesExp) {
		System.out.print("(");
		if (timesExp.getLHS() != null) timesExp.getLHS().accept(this);
		System.out.print(" % ");
		if (timesExp.getRHS() != null) timesExp.getRHS().accept(this);
		System.out.print(")");
	}

	@Override
	public void visit(ArrayLookup arrayLookup) {
		if (arrayLookup.getArray() != null) arrayLookup.getArray().accept(this);
		System.out.print("[");
		if (arrayLookup.getIndex() != null) arrayLookup.getIndex().accept(this);
	}

	@Override
	public void visit(ArrayLength length) {
		if (length.getArray() != null) length.getArray().accept(this);
		System.out.print(".length");
	}



	@Override
	public void visit(IntegerLiteral intLiteral) {
		System.out.print(intLiteral.getValue());
	}

	@Override
	public void visit(FloatLiteral floatLiteral) {
		System.out.print(floatLiteral.getValue());
	}

	@Override
	public void visit(True trueLiteral) {
		System.out.print("true");
	}

	@Override
	public void visit(False falseLiteral) {
		System.out.print("false");
	}

	@Override
	public void visit(IdentifierExp identExp) {
		if (identExp.getName() != null)
			System.out.print(identExp.getName());
	}



	@Override
	public void visit(NewArray array) {
		System.out.print("new int [");
		if (array.getArraySize() != null) array.getArraySize().accept(this);
		System.out.print("]");
	}

	@Override
	public void visit(Not notExp) {
		System.out.print("!");
		if (notExp.getExp() != null) notExp.getExp().accept(this);
	}

	@Override
	public void visit(Negative negExp) {
		System.out.print("-");
		if (negExp.getExp() != null) negExp.getExp().accept(this);
	}


	@Override
	public void visit(Identifier id) {
		if (id.getName() != null)
			System.out.print(id.getName());
	}

}
