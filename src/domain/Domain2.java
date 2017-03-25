package domain;
import java.util.LinkedList;
import java.util.TreeSet;

import Interface.IDomain;

/**
 * Seconde version des domaines pour le problème des queens avec des ArrayList
 * 
 * @author Casanova Mario  et Araya Montalvo
 *
 */
public class Domain2 implements  IDomain, Comparable<Domain2> {

	private LinkedList<Integer> valeurs;
	private int ligne; // Pour éviter de devoir jouer avec des compteurs
	

	public Domain2() {
		super();
		valeurs= new LinkedList<Integer>();
		ligne=0;
	}

	public Domain2(LinkedList<Integer> valeurs) {
		super();
		this.valeurs = valeurs;
		ligne=0;

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

	@Override
	public int compareTo(Domain2 arg0) {
		
		return ((Integer)this.valeurs.size()).compareTo((Integer)arg0.valeurs.size());
	}

}
