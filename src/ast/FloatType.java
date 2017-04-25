package ast;

import visitor.Visitor;

public class FloatType implements Type{
	public void accept(Visitor v) {
		v.visit(this);
	}
}
