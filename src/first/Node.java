package first;

import java.util.ArrayList;

import Interface.IDomain;
import Interface.INode;

public class Node implements INode {
	
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
	@Override
	public void replace( IDomain old, Domain newD){
		int i;
		i=this.domains.indexOf(old);
		
		this.domains.set(i, newD);
	}
	
	/* (non-Javadoc)
	 * @see first.INode#add(first.Domain)
	 */
	@Override
	public void add(Domain d){
		this.domains.add(d);
	}
	
	/* (non-Javadoc)
	 * @see first.INode#removeLast()
	 */
	@Override
	public void removeLast(){
		if (this.domains.size()>0)this.domains.remove(this.domains.size()-1);
	}
	
	/* (non-Javadoc)
	 * @see first.INode#get(int)
	 */
	@Override
	public Domain get(int index){
		return this.domains.get(index);
	}

	/* (non-Javadoc)
	 * @see first.INode#getDomains()
	 */
	@Override
	public ArrayList<Domain> getDomains() {
		return domains;
	}

	/* (non-Javadoc)
	 * @see first.INode#setDomains(java.util.ArrayList)
	 */
	@Override
	public void setDomains(ArrayList<Domain> domains) {
		this.domains = domains;
	}
	
	/* (non-Javadoc)
	 * @see first.INode#toStringQueen()
	 */
	@Override
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
