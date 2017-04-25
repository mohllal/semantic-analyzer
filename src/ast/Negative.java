package ast;

import visitor.Visitor;

public class Negative implements Exp{
	private Exp exp;

	public Negative(Exp exp) {
		this.exp = exp;
	}

	public Exp getExp() {
		return exp;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
