package ast;

import visitor.Visitor;

public class BooleanArrayType implements Type {
	public void accept(Visitor v) {
		v.visit(this);
	}
}
