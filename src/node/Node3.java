package node;

import java.util.ArrayList;
import java.util.Collections;

import Interface.IDomain;
import forwardChecking.Domain2;
import forwardChecking.Node2;
import domain.*;

public class Node3 {
private ArrayList<Domain3> domains;
private int nbCase;
private int nbMagique; // Le nombre au quel la somme de chaque ligne, colonne, diagonal doit être égal
	
	public Node3(){
		this.domains= new ArrayList<Domain3>();
		nbMagique=0;
		nbCase=0;
	}
	
	public Node3(ArrayList<Domain3> domains){
		this.domains=domains;
		nbMagique=0;
		nbCase=0;
	}
	
	public Node3(Node3 n){
		this.domains=n.domains;
		nbMagique=n.nbCase;
		nbCase=n.nbMagique;
	}

	/* (non-Javadoc)
	 * @see first.INode#replace(Interface.IDomain, first.Domain)
	 */

	public void replace( IDomain old, Domain3 newD){
		int i;
		i=this.domains.indexOf(old);
		
		this.domains.set(i, newD);
	}
	
	/* (non-Javadoc)
	 * @see first.INode#add(first.Domain)
	 */

	public void add(Domain3 d){
		this.domains.add(d);
	}
	
	/* (non-Javadoc)
	 * @see first.INode#removeLast()
	 */

	public void removeLast(){
		if (this.domains.size()>0)this.domains.remove(this.domains.size()-1);
	}
	
	/* (non-Javadoc)
	 * @see first.INode#get(int)
	 */

	public Domain3 get(int index){
		return this.domains.get(index);
	}
	/**
	 * @brief Va chercher la ligne du domaine à un indice précis
	 * @param index = l'indice du domaine
	 *  
	 * @return int : la ligne du domaine à l'indice donnée
	 */
	public int getLigneAt(int index){
		return this.domains.get(index).getLigne();
	}

	/* (non-Javadoc)
	 * @see first.INode#getDomains()
	 */

	public ArrayList<Domain3> getDomains() {
		return domains;
	}

	/* (non-Javadoc)
	 * @see first.INode#setDomains(java.util.ArrayList)
	 */
	public void setDomains(ArrayList<Domain3> domains) {
		this.domains = domains;
	}
	
	public int getNbCase() {
		return nbCase;
	}

	public void setNbCase(int nbCase) {
		this.nbCase = nbCase;
	}

	public int getNbMagique() {
		return nbMagique;
	}

	public void setNbMagique(int nbMagique) {
		this.nbMagique = nbMagique;
	}
	
	public void calculeNbMagique(int n){
		this.nbMagique=(int) ((n*(Math.pow(n,2)+1))/2);
	}
	

	/* (non-Javadoc)
	 * @see first.INode#toStringQueen()
	 */
	public String toStringCarreMagique(){
		String s="";
		int i=1;
		for (Domain3 d : this.domains){
			
			s+=d.getValeurs().getFirst()+"|";
			if (d.getColonne()==nbCase){
				s+='\n';
			}
			++i;
		}
		return s;
		
		
		
	}
	
	public void sortDomaines(){
		Collections.sort(this.domains);
	}
}
