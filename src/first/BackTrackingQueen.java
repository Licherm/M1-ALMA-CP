package first;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TreeSet;

import com.sun.org.apache.bcel.internal.generic.CPInstruction;

import Interface.IDomain;
import Interface.INode;

public class BackTrackingQueen {
	
	
	/**
	 * @brief test si une affectation donn√©e est possible
	 * @param node = node ou chacun des domains est r√©duit √† une seule valeur
	 *  
	 * @return true si l'affectation est valide false sinon
	 */
	public boolean isValide(Node node){
		
		Node nodeCopy = new Node(node);
		for (int i=0;i<nodeCopy.getDomains().size();++i){
			Domain d=nodeCopy.get(i);
			int val1=d.getValeurs().first();
			for (int n=i+1;n<nodeCopy.getDomains().size();++n){
				Domain d2=nodeCopy.get(n);
				int val2=d2.getValeurs().first();
				//Test Colonne pas dans le mÍme colonne
				if(val1==val2){
					return false; // si dans la mÍme colonne
				}
				
				//Test la diagonale
				if (Math.abs(val1-val2)==n-i){
					return false; // sur la diagonale
				}
			}			
		}
		return true;
		
	}
	
	
	/**
	 * @brief test si une affectation donn√©e est possible. Version amÈliorÈ sans vÈrification inutiles.
	 * @param node = node ou chacun des domains est r√©duit √† une seule valeur
	 *  
	 * @return true si l'affectation est valide false sinon
	 */
	public boolean isValideV2(Node node){
		
		Node nodeCopy = new Node(node);
		int lastVal=node.getDomains().size()-1;
		if (lastVal>0){ 
			Domain domainLast=nodeCopy.get(lastVal);
			int val = domainLast.getValeurs().first(); 
			for (int i=0;i<nodeCopy.getDomains().size()-1;++i){
				Domain d=nodeCopy.get(i);
				int val2=d.getValeurs().first();
				
				//Test Colonne pas dans le mÍme colonne
				if(val2==val){
					return false; // si dans la mÍme colonne
				}
					
				//Test la diagonale
				if (Math.abs(val2-val)==lastVal-i){
					return false; // sur la diagonale
				}
							
			}
		}
		return true;
		
	}
	
	
	/**
	 * @brief PremiËre version du Backtracking pour le problËme des queens. Fonction rÈcursive
	 * @param n = le node
	 * @return int : le nombre de solution trouvÈ
	 * @warning la fonction fait elle mÍme l'affichage et la complÈxitÈ est trËs mauvaise
	 * 
	 */
	public int backTrackingQueenPrune(Node n){
		Node copyNode= new Node(n);
		Node copyNode2= new Node();
		Domain domain= new Domain();
		TreeSet<Integer> valeurs= new TreeSet<Integer>();
		TreeSet<Integer> tree= new TreeSet<Integer>();
		boolean solution=true;
		int compteur=0;
		int compteur2=0;
		int nbSolution=0;
		
		while((solution)&&(compteur<copyNode.getDomains().size())){
			//Si la taille du domain est de 1 alors on a plus besoin de travailler dessus on passe au suivant
			if(copyNode.getDomains().get(compteur).getValeurs().size()==1){
				copyNode2.add(copyNode.getDomains().get(compteur));
				++compteur;
			}else{
				solution=false; // Un des domaines a encore plusieur valeurs -> on est pas encore arriver ‡ une solution
			}
			
		}
		if(solution){
			System.out.println("Une solution !");
			System.out.println(n.toStringQueen());
			return ++nbSolution;
			
		}else{
			
			//On copy les valeurs du premier domaine de la liste qui ‡ encore une taille supÈrieur ‡ 1
			valeurs=copyNode.getDomains().get(compteur).getValeurs();
			for (Integer val : valeurs){
				compteur2=0;
				tree.add(val);
				domain.setValeurs(tree);
				copyNode2.add(domain);
				if(isValideV2(copyNode2)){//Cette combinaison marche pour l'instant on avance
					for (int i=compteur+1;i<copyNode.getDomains().size();++i){ // Ajout des autres domaines
						copyNode2.add(copyNode.get(i));
						++compteur2;	
					}
					// On reduit un domaine de plus ‡ la taille de 1 on fait l'apelle recursif
					nbSolution+=backTrackingQueenPrune(copyNode2);
					
					for (int i=0;i<compteur2;++i){// On suprime les domaines ajouter dans le foreach prÈcedent
						copyNode2.removeLast();
					}
				}
				copyNode2.removeLast();// EnlËve pour essayer la prochaine valeur
				tree.clear();				
			}
		}
		return nbSolution;
	}
	
	

		
	
	
	public static void main(String[] args) {
		BackTrackingQueen backQ= new BackTrackingQueen();
		Domain d= new Domain();
		Node n = new Node();
		TreeSet<Integer> tree= new TreeSet<Integer>();
		double chrono=System.currentTimeMillis();
		
		int nbCase=4;// Les dimensions de l'Èchequier
		
		for (int i=0;i<nbCase;++i){
			tree.add(i+1);
		}
		
		for (int i=0;i<nbCase;++i){
			d.setValeurs(tree);
			n.add(d);
		}
		System.out.println("Taille de l'ÈchÈquier "+nbCase);
		
		
		System.out.println(backQ.backTrackingQueenPrune(n)+" solutions trouvÈ");
		chrono=System.currentTimeMillis()-chrono;
		chrono=chrono/1000;
		System.out.println("En "+chrono+" secondes");
		

	}

	
	
	

}
