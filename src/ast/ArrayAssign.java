package ast;

import visitor.Visitor;

public class ArrayAssign implements Statement {
	private Identifier id;
	private Exp index, value;
	
	public ArrayAssign(Identifier id, Exp index, Exp value) {
		this.id = id;
		this.index = index;
		this.value = value;
	}
	
	public Identifier getId() {
		return id;
	}
	
	public Exp getIndex() {
		return index;
	}
	
	public Exp getValue() {
		return value;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
