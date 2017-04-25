package ast;

import visitor.Visitor;

public class Block implements Statement {
	private StatementList stms;
	
	public Block(StatementList stms) {
		this.stms = stms;
	}
	
	public StatementList getStms() {
		return stms;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
