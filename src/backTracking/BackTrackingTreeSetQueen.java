
package backTracking;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TreeSet;

import com.sun.org.apache.bcel.internal.generic.CPInstruction;

import Interface.IDomain;
import Interface.INode;
import domain.Domain;
import node.Node;
/**
 * Première version du backTracking avec les TreeSet pour les domaines.
 * 
 * @author Casanova Mario  et Araya Montalvo
 *
 */
public class BackTrackingTreeSetQueen {
	
	
	/**
	 * @brief test si une affectation donnÃ©e est possible
	 * @param node = node ou chacun des domains est rÃ©duit Ã  une seule valeur
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
				//Test Colonne pas dans le même colonne
				if(val1==val2){
					return false; // si dans la même colonne
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
	 * @brief test si une affectation donnÃ©e est possible. Version amélioré sans vérification inutiles.
	 * @param node = node ou chacun des domains est rÃ©duit Ã  une seule valeur
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
				
				//Test Colonne pas dans le même colonne
				if(val2==val){
					return false; // si dans la même colonne
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
	 * @brief Première version du Backtracking pour le problème des queens. Fonction récursive
	 * @param n = le node
	 * @return int : le nombre de solution trouvé
	 * @warning la fonction fait elle même l'affichage et la compléxité est très mauvaise
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
			//copyNode.getDomains().get(compteur).getValeurs().size()==1
			if(copyNode.get(compteur).getValeurs().size()==1){
				copyNode2.add(copyNode.get(compteur));
				++compteur;
			}else{
				solution=false; // Un des domaines a encore plusieur valeurs -> on est pas encore arriver à une solution
			}
			
		}
		if(solution){
			//Affichage prend trop de temper enlevez sauf pour debug
			//System.out.println("Une solution !");
			//System.out.println(n.toStringQueen());
			return ++nbSolution;
			
		}else{
			
			//On copy les valeurs du premier domaine de la liste qui à encore une taille supérieur à 1
			valeurs=copyNode.get(compteur).getValeurs();
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
					// On reduit un domaine de plus à la taille de 1 on fait l'apelle recursif
					nbSolution+=backTrackingQueenPrune(copyNode2);
					
					for (int i=0;i<compteur2;++i){// On suprime les domaines ajouter dans le foreach précedent
						copyNode2.removeLast();
					}
				}
				copyNode2.removeLast();// Enlève pour essayer la prochaine valeur
				tree.clear();				
			}
		}
		return nbSolution;
	}
	
	

		
	
	
	public static void main(String[] args) {
		BackTrackingTreeSetQueen backQ= new BackTrackingTreeSetQueen();
		Node n = new Node();
		TreeSet<Integer> tree= new TreeSet<Integer>();
		double chrono=System.currentTimeMillis();
		
		int nbCase=15;// Les dimensions de l'échequier
		
		for (int i=0;i<nbCase;++i){
			tree.add(i+1);
		}
		
		for (int i=0;i<nbCase;++i){
			Domain d= new Domain();
			d.setValeurs(tree);
			n.add(d);
		}
		System.out.println("Taille de l'échéquier "+nbCase);
		
		
		System.out.println(backQ.backTrackingQueenPrune(n)+" solutions trouvé");
		chrono=System.currentTimeMillis()-chrono;
		chrono=chrono/1000;
		System.out.println("En "+chrono+" secondes");
			

		}

		
		
		

}


