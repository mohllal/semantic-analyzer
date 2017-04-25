package ast;

import visitor.Visitor;

public class IdentifierType implements Type {
	private String name;
	
	public IdentifierType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
