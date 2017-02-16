package first;

import java.util.TreeSet;

public class Domain {

	private TreeSet<Integer> valeurs;
	
	public Domain (){
		valeurs= new TreeSet<Integer> ();
	}
	
	public Domain (TreeSet<Integer> valeurs){
		this.valeurs=valeurs;
	}

	public boolean remove(int e){
		return this.valeurs.remove(e);
	}
	
	
	public boolean removeFirst(){
		return this.valeurs.pollFirst() !=null;
	}
	
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
