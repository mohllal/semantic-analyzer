package ast;

import java.util.ArrayList;
import java.util.List;

public class ExpList {
	private List<Exp> list;
	
	public ExpList() {
		list = new ArrayList<Exp>();
	}
	
	public void addElement(Exp exp) {
		list.add(exp);
	}
	
	public Exp elementAt(int index) {
		return list.get(index);
	}
	
	public int size() {
		return list.size();
	}
}
