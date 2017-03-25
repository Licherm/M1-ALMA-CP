package node;
import Interface.IDomain;
import Interface.INode;
import domain.Domain;
import domain.Domain2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
/**
 * Seconde version des Nodes pour le problème des queens  qui utilise 
 * les Domaine2 (avec LinkedList)
 * 
 * @author Casanova Mario  et Araya Montalvo
 *
 */
public class Node2 {

private ArrayList<Domain2> domains;
	
	public Node2(){
		this.domains= new ArrayList<Domain2>();
	}
	
	public Node2(ArrayList<Domain2> domains){
		this.domains=domains;
	}
	
	public Node2(Node2 n){
		this.domains=n.domains;
	}



	public void replace( IDomain old, Domain2 newD){
		int i;
		i=this.domains.indexOf(old);
		
		this.domains.set(i, newD);
	}

	/**
	 * Ajoute un domaine
	 * 
	 * @param d : le domaine à ajouter
	 */
	public void add(Domain2 d){
		this.domains.add(d);
	}

	/**
	 * remove le dernier Domaine
	 */
	public void removeLast(){
		if (this.domains.size()>0)this.domains.remove(this.domains.size()-1);
	}

	public Domain2 get(int index){
		return this.domains.get(index);
	}
	/**
	 * Va chercher la ligne du domaine à un indice précis
	 * 
	 * @param index = l'indice du domaine
	 *  
	 * @return int : la ligne du domaine à l'indice donnée
	 */
	public int getLigneAt(int index){
		return this.domains.get(index).getLigne();
	}


	public ArrayList<Domain2> getDomains() {
		return domains;
	}


	public void setDomains(ArrayList<Domain2> domains) {
		this.domains = domains;
	}

	public String toStringQueen(){
		String s="";
		int i=1;
		for (Domain2 d : this.domains){
			s+="La queen de la ligne "+d.getLigne()+" se trouve en position "+d.getValeurs().getFirst()+'\n';
			++i;
		}
		return s;
		
	}
	
	public void sortDomaines(){
		Collections.sort(this.domains);
	}

}
