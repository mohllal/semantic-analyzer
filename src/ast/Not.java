package ast;

import visitor.Visitor;

public class Not implements Exp {
	private Exp exp;
	
	public Not(Exp exp) {
		this.exp = exp;
	}
	
	public Exp getExp() {
		return exp;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
