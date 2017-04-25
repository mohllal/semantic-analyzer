package ast;

import visitor.Visitor;

public interface Exp {
	public void accept(Visitor v);
}
