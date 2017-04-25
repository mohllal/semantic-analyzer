package ast;

import java.util.ArrayList;
import java.util.List;

public class FormalList {
	private List<Formal> list;
	
	public FormalList() {
		list = new ArrayList<Formal>();
	}
	
	public void addElement(Formal param) {
		list.add(param);
	}
	
	public Formal elementAt(int index) {
		return list.get(index);
	}
	
	public int size() {
		return list.size();
	}
}
