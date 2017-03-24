package node;

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
	
	/* (non-Javadoc)
	 * @see first.INode#replace(Interface.IDomain, first.Domain)
	 */
	public void replace( IDomain old, Domain newD){
		int i;
		i=this.domains.indexOf(old);
		
		this.domains.set(i, newD);
	}
	
	/* (non-Javadoc)
	 * @see first.INode#add(first.Domain)
	 */
	
	public void add(Domain d){
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
	public Domain get(int index){
		return this.domains.get(index);
	}

	/* (non-Javadoc)
	 * @see first.INode#getDomains()
	 */
	public ArrayList<Domain> getDomains() {
		return domains;
	}

	/* (non-Javadoc)
	 * @see first.INode#setDomains(java.util.ArrayList)
	 */
	public void setDomains(ArrayList<Domain> domains) {
		this.domains = domains;
	}
	
	/* (non-Javadoc)
	 * @see first.INode#toStringQueen()
	 */
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
