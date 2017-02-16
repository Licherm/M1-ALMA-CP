package first;

import java.util.ArrayList;
import java.util.TreeSet;

import com.sun.org.apache.bcel.internal.generic.CPInstruction;

public class BackTrackingQueen {
	
	
	/**
	 * @brief test si une affectation donn√©e est possible
	 * @param node = node ou chacun des domains est r√©duit √† une seule valeur
	 *  
	 * @return true si l'affectation est valide false sinon
	 */
	public boolean isValide(Node node){
		
		Node nodeCopy = new Node(node);
		//System.out.println(node.toStringQueen());
		//System.out.println("Nb domain : "+node.getDomains().size());
		for (int i=0;i<nodeCopy.getDomains().size();++i){
			Domain d=nodeCopy.get(i);
			int val1=d.getValeurs().first();
			for (int n=i+1;n<nodeCopy.getDomains().size();++n){
				Domain d2=nodeCopy.get(n);
				int val2=d2.getValeurs().first();
				//System.out.println("val2 = "+val2+" et val1 = "+val1);
				//Test Colonne pas dans le m√™me colonne
				if(val1==val2){
					//System.out.println("Yo egale");
					return false; // si dans la m√™me colonne
				}
				
				//Test la diagonale
				//System.out.println("n-i = "+(n-i));
				if (Math.abs(val1-val2)==n-i){
					//System.out.println("Yo diago");
					return false; // sur la diagonale
				}
			}			
		}
		return true;
		
	}
	
	
	
	/**
	 * @brief PremiËre version du Backtracking pour le problËme des queens. Fonction rÈcursive
	 * @param n = le node
	 * @warning la fonction fait elle mÍme l'affichage 
	 * 
	 */
	public void backTrackingQueenPrune(Node n){
		Node copyNode= new Node(n);
		Node copyNode2= new Node();
		Node copyNode3;
		Domain domain= new Domain();
		TreeSet<Integer> valeurs= new TreeSet<Integer>();
		TreeSet<Integer> tree= new TreeSet<Integer>();
		boolean solution=true;
		int compteur=0;
		int compteur2=0;
		
		while((solution)&&(compteur<copyNode.getDomains().size())){
			//Si la taille du domain est de 1 alors on a plus besoin de travailler dessus on passe au suivant
			if(copyNode.getDomains().get(compteur).getValeurs().size()==1){
				copyNode2.add(copyNode.getDomains().get(compteur));
				++compteur;
			}else{
				solution=false; // Un des domaines a encore plusieur valeurs -> on est pas encore arriver ‡ une solution
			}
			
		}
		copyNode3 = new Node (copyNode2);
		if(solution){
			System.out.println("Une solution !!!!!!!!!!!!!!!!!!!!!");
			System.out.println(n.toStringQueen());
			
		}else{
			
			//On copy les valeurs du premier domaine de la liste qui ‡ encore une taille supÈrieur ‡ 1
			valeurs=copyNode.getDomains().get(compteur).getValeurs();
			for (Integer val : valeurs){
				compteur2=0;
				tree.add(val);
				domain.setValeurs(tree);
				copyNode2.add(domain);
				if(isValide(copyNode2)){//Cette combinaison marche pour l'instant on avance
					for (int i=compteur+1;i<copyNode.getDomains().size();++i){
						copyNode2.add(copyNode.get(i));
						++compteur2;	
					}
					// On reduit un domaine de plus ‡ la taille de 1 on fait l'apelle recursif
					backTrackingQueenPrune(copyNode2);
					for (int i=0;i<compteur2;++i){
						copyNode2.removeLast();
					}
				}
				copyNode2.removeLast();
				tree.clear();				
			}
		}
		
	}
	
	

		
	
	
	public static void main(String[] args) {
		BackTrackingQueen backQ= new BackTrackingQueen();
		Domain d= new Domain();
		Node n = new Node();
		TreeSet<Integer> tree= new TreeSet<Integer>();
		int nbCase=4;// Les dimension de l'Èchequier
		
		for (int i=0;i<nbCase;++i){
			tree.add(i+1);
			System.out.println(i+1);
		}
		
		for (int i=0;i<nbCase;++i){
			d.setValeurs(tree);
			n.add(d);
		}
		System.out.println("size de n: "+n.getDomains().size());
		
		backQ.backTrackingQueenPrune(n);

	}

	
	
	

}
