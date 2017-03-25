package node;

import java.util.ArrayList;
import java.util.Collections;

import Interface.IDomain;
import domain.*;
/**
 * Node pour le problème des carré magique
 * 
 * @author Casanova Mario  et Araya Montalvo
 *
 */
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



	public void replace( IDomain old, Domain3 newD){
		int i;
		i=this.domains.indexOf(old);
		
		this.domains.set(i, newD);
	}
	

	/**
	 * Ajoute un domaine
	 * 
	 * @param d : le domaine à ajouter
	 */
	public void add(Domain3 d){
		this.domains.add(d);
	}
	


	public void removeLast(){
		if (this.domains.size()>0)this.domains.remove(this.domains.size()-1);
	}


	public Domain3 get(int index){
		return this.domains.get(index);
	}
	/**
	 *  Va chercher la ligne du domaine à un indice précis
	 * 
	 * @param index = l'indice du domaine
	 *  
	 * @return int : la ligne du domaine à l'indice donnée
	 */
	public int getLigneAt(int index){
		return this.domains.get(index).getLigne();
	}


	public ArrayList<Domain3> getDomains() {
		return domains;
	}


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
	/**
	 * Calcule le nombre au quel la somme d'une ligne,colonne,diagonale doit être égale
	 * 
	 * @param n : le nombre de case d'une ligne du carré
	 */
	public void calculeNbMagique(int n){
		this.nbMagique=(int) ((n*(Math.pow(n,2)+1))/2);
	}
	

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
