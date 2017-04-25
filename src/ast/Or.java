package ast;

import visitor.Visitor;

public class Or implements Exp {
	private Exp lhs, rhs;

	public Or(Exp lhs, Exp rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	public Exp getLHS() {
		return lhs;
	}

	public Exp getRHS() {
		return rhs;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
