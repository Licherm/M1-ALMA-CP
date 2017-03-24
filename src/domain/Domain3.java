package domain;

import java.util.LinkedList;

import Interface.IDomain;

public class Domain3  implements  IDomain, Comparable<Domain3>{

	private LinkedList<Integer> valeurs;
	private int ligne; // Pour éviter de devoir jouer avec des compteurs
	private int colonne;
	

	public Domain3() {
		super();
		valeurs= new LinkedList<Integer>();
		ligne=0;
		colonne=0;
	}

	public Domain3(LinkedList<Integer> valeurs) {
		super();
		this.valeurs = valeurs;
		ligne=0;
		colonne=0;

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
	
	public void addLast(int val){
		this.valeurs.addLast(val);
	}

	public LinkedList<Integer> getValeurs() {
		// TODO Auto-generated method stub
		return this.valeurs;
	}

	public void setValeurs(LinkedList<Integer> valeurs) {
		this.valeurs=valeurs;
		
	}

	public int getLigne() {
		return ligne;
	}

	public void setLigne(int ligne) {
		this.ligne = ligne;
	}

	public int getColonne() {
		return colonne;
	}

	public void setColonne(int colonne) {
		this.colonne = colonne;
	}

	@Override
	public int compareTo(Domain3 arg0) {
		
		return ((Integer)this.valeurs.size()).compareTo((Integer)arg0.valeurs.size());
	}

}
