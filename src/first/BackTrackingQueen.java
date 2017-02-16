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
		System.out.println(node.toStringQueen());
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
	//FIXME s
	public void backTrackingQueenPrune(Node n){
		System.out.println("Hello");
		Node copyNode= new Node(n);
		Node copyNode2= new Node();
		Node copyNode3;
		Domain domain= new Domain();
		TreeSet<Integer> valeurs= new TreeSet<Integer>();
		TreeSet<Integer> tree= new TreeSet<Integer>();
		boolean solution=true;
		int compteur=0;
		
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
				//System.out.println("plop");
				tree.add(val);
				domain.setValeurs(tree);
				copyNode2.add(domain);
				//System.out.println("copy Node2 syze : "+copyNode2.getDomains().size());
				if(isValide(copyNode2)){//Cette combinaison marche pour l'instant on avance
					//System.out.println("IS Valide");
					//for (Domain domainRestant : copyNode.getDomains()){
					for (int i=compteur+1;i<copyNode.getDomains().size();++i){
						System.out.println("i = "+i);
						copyNode2.add(copyNode.get(i));
					}
					// On reduit un domaine de plus ‡ la taille de 1 on fait l'apelle recursif
					System.out.println("Recursivite !!!!!!!!!!!!!!!!!!!");
					backTrackingQueenPrune(copyNode2);
					System.out.println("Retour !!");
					copyNode2 = new Node();
					copyNode2.setDomains(copyNode3.getDomains());
					System.out.println("copy Node2 syze : "+copyNode2.getDomains().size());

					//System.out.println("Compteur ="+compteur);
					
					
					
				}
				//Clear des variables pour la prochaine boucle
				//copyNode2.setDomains(copyNode3.getDomains());
				//for (int p=0;p<=compteur;++p){
					copyNode2.removeLast();
				//}
				//System.out.println("copy Node3 syze : "+copyNode3.getDomains().size());
				//System.out.println("copy Node2 syze : "+copyNode2.getDomains().size());
				domain.removeLast();
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
