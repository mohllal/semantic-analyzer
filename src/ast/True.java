package ast;

import visitor.Visitor;

public class True implements Exp {
	public void accept(Visitor v) {
		v.visit(this);
	}
}
