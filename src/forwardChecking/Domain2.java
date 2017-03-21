package forwardChecking;
import java.util.LinkedList;
import java.util.TreeSet;

import Interface.IDomain;

public class Domain2 implements  IDomain {

	private LinkedList<Integer> valeurs;
	

	public Domain2() {
		super();
		valeurs= new LinkedList<Integer>();
	}

	public Domain2(LinkedList<Integer> valeurs) {
		super();
		this.valeurs = valeurs;
	}

	
	@Override
	public boolean remove(int e) {
		
		return this.valeurs.removeFirstOccurrence(e);
	}
	
	
	@Override
	public boolean removeFirst() {
		return this.valeurs.pollFirst()!=null;
	}

	@Override
	public boolean removeLast() {
		// TODO Auto-generated method stub
		return this.valeurs.pollLast()!=null;
	}

	public LinkedList<Integer> getValeurs() {
		// TODO Auto-generated method stub
		return this.valeurs;
	}

	public void setValeurs(LinkedList<Integer> valeurs) {
		this.valeurs=valeurs;
		
	}

}
