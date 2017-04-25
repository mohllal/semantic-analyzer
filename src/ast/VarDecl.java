package ast;

import visitor.Visitor;

public class VarDecl {
	private Type type;
	private Identifier id;
	
	public VarDecl(Type type, Identifier id) {
		this.type = type;
		this.id = id;
	}
	
	public Type getType() {
		return type;
	}
	
	public Identifier getId() {
		return id;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
