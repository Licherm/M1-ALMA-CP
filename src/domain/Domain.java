package domain;

import java.util.TreeSet;

import Interface.IDomain;

/**
 * Première version des domaines pour le problème des queens avec des treeSet
 * 
 * @author Casanova Mario  et Araya Montalvo
 *
 */
public class Domain implements IDomain {

	private TreeSet<Integer> valeurs;
	
	public Domain (){
		valeurs= new TreeSet<Integer> ();
	}
	
	public Domain (TreeSet<Integer> valeurs){
		this.valeurs=valeurs;
	}

	/* (non-Javadoc)
	 * @see first.IDomain#remove(int)
	 */
	@Override
	public boolean remove(int e){
		return this.valeurs.remove(e);
	}
	
	
	/* (non-Javadoc)
	 * @see first.IDomain#removeFirst()
	 */
	@Override
	public boolean removeFirst(){
		return this.valeurs.pollFirst() !=null;
	}
	
	/* (non-Javadoc)
	 * @see first.IDomain#removeLast()
	 */
	@Override
	public boolean removeLast(){
		return this.valeurs.pollLast() !=null;
	}
		
	
	
	
	public TreeSet<Integer> getValeurs() {
		return valeurs;
	}

	
	public void setValeurs(TreeSet<Integer> valeurs) {
		this.valeurs = valeurs;
	}
	
	
}
