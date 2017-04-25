package ast;

import visitor.Visitor;

public class IntegerType implements Type {
	public void accept(Visitor v) {
		v.visit(this);
	}
}
