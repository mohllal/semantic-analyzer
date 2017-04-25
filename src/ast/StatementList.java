package ast;

import java.util.ArrayList;
import java.util.List;

public class StatementList {
	private List<Statement> list;
	
	public StatementList() {
		list = new ArrayList<Statement>();
	}
	
	public void addElement(Statement stm) {
		list.add(stm);
	}
	
	public Statement elementAt(int index) {
		return list.get(index);
	}
	
	public int size() {
		return list.size();
	}
}
