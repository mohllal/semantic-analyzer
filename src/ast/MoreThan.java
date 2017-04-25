package ast;

import visitor.Visitor;

public class MoreThan implements Exp{
	private Exp lhs, rhs;

	public MoreThan(Exp lhs, Exp rhs) {
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
