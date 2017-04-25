package ast;

import visitor.Visitor;

public class Assign implements Statement {
	private Identifier id;
	private Exp value;
	
	public Assign(Identifier id, Exp value) {
		this.id = id;
		this.value = value;
	}
	
	public Identifier getId() {
		return id;
	}
	
	public Exp getValue() {
		return value;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
