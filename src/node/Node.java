package node;
/**
 * Première version des Nodes pour le problème des queens  qui utilise 
 * les Domaine (avec treeSet)
 * 
 * @author Casanova Mario  et Araya Montalvo
 *
 */
import java.util.ArrayList;

import Interface.IDomain;
import Interface.INode;
import domain.Domain;

public class Node  {
	
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
	

	public void replace( IDomain old, Domain newD){
		int i;
		i=this.domains.indexOf(old);
		
		this.domains.set(i, newD);
	}

	/**
	 * Ajoute un domaine
	 * 
	 * @param d : le domaine à ajouter
	 */
	public void add(Domain d){
		this.domains.add(d);
	}
	

	public void removeLast(){
		if (this.domains.size()>0)this.domains.remove(this.domains.size()-1);
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
	

	public String toStringQueen(){
		String s="";
		int i=1;
		for (Domain d : this.domains){
			s+="La queen de la ligne "+i+" se trouve en position "+d.getValeurs().first()+'\n';
			++i;
		}
		return s;
		
	}

}
