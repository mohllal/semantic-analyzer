package ast;

import visitor.Visitor;

public interface Statement {
	public void accept(Visitor v);
}
