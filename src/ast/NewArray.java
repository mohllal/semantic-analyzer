package ast;

import visitor.Visitor;

public class NewArray implements Exp {
	private Exp arraySize;
	
	public NewArray(Exp arraySize) {
		this.arraySize = arraySize;
	}
	
	public Exp getArraySize() {
		return arraySize;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}
}
