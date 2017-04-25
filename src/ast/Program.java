package ast;

import visitor.Visitor;

public class Program {
	private StatementList stm;
	private Declarations list;
	
	public Program(StatementList stm, Declarations list) {
		this.stm = stm;
		this.setList(list);
	}
	
	public StatementList getStm() {
		return stm;
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}

	public Declarations getList() {
		return list;
	}

	public void setList(Declarations list) {
		this.list = list;
	}
}
