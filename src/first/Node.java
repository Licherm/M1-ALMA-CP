package first;

import java.util.ArrayList;
import java.util.TreeSet;

public class Node {
	
	private ArrayList<Domain> domains;
	
	public Node(){
		this.domains= new ArrayList<Domain>();
	}
	
	public Node(ArrayList<Domain> domains){
		this.domains=domains;
	}
	
	public Node(Node n){
		this.domains=n.domains;
	}
	
	public void replace( Domain old, Domain newD){
		int i;
		i=this.domains.indexOf(old);
		
		this.domains.set(i, newD);
	}
	
	public Domain get(int index){
		return this.domains.get(index);
	}

	public ArrayList<Domain> getDomains() {
		return domains;
	}

	public void setDomains(ArrayList<Domain> domains) {
		this.domains = domains;
	}

}
