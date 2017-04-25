package ast;

import visitor.Visitor;

public interface Type {
	public void accept(Visitor v);
}
