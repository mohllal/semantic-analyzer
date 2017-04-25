package ast;

import visitor.Visitor;

public class While implements Statement {
	private Exp condExp;
	private Statement stm;
	
	public While(Exp condExp, Statement stm) {
		this.condExp = condExp;
		this.stm = stm;
	}
	
	public Exp getCondExp() {
		return condExp;
	}
	
	public Statement getStm() {
		return stm;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
