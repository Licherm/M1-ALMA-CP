package second;
import Interface.IDomain;
import Interface.INode;
import first.Domain;
import first.Node;

import java.util.ArrayList;

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
	
	/* (non-Javadoc)
	 * @see first.INode#replace(Interface.IDomain, first.Domain)
	 */

	public void replace( IDomain old, IDomain newD){
		int i;
		i=this.domains.indexOf(old);
		
		this.domains.set(i, newD);
	}
	
	/* (non-Javadoc)
	 * @see first.INode#add(first.Domain)
	 */

	public void add(Domain2 d){
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

	public Domain2 get(int index){
		return this.domains.get(index);
	}

	/* (non-Javadoc)
	 * @see first.INode#getDomains()
	 */

	public ArrayList<Domain2> getDomains() {
		return domains;
	}

	/* (non-Javadoc)
	 * @see first.INode#setDomains(java.util.ArrayList)
	 */
	public void setDomains(ArrayList<Domain2> domains) {
		this.domains = domains;
	}
	
	/* (non-Javadoc)
	 * @see first.INode#toStringQueen()
	 */
	public String toStringQueen(){
		String s="";
		int i=1;
		for (Domain2 d : this.domains){
			s+="La queen de la ligne "+i+" se trouve en position "+d.getValeurs().first()+'\n';
			++i;
		}
		return s;
		
	}

}
