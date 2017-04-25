package ast;

import visitor.Visitor;

public class Identifier {
	private String name;
	
	public Identifier(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
