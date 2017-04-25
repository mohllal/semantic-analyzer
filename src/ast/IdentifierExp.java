package ast;

import visitor.Visitor;

public class IdentifierExp implements Exp {
	private String name;
	
	public IdentifierExp(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
