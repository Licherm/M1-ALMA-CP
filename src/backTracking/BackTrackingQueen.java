package backTracking;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.LinkedList;

import com.sun.org.apache.bcel.internal.generic.CPInstruction;
import forwardChecking.*;
import node.Node2;
import Interface.IDomain;
import Interface.INode;
import domain.Domain2;

/**
 * Seconde version du backTracking avec les LinkdedList pour les domaines.
 * 
 * @author Casanova Mario  et Araya Montalvo
 *
 */
public class BackTrackingQueen {
	
	
	/**
	 * test si une affectation donnée est valide. Version non optimis�.
	 * 
	 * @param Node2 = Node2 ou chacun des Domain2s est réduit à une seule valeur
	 *  
	 * @return true si l'affectation est valide false sinon
	 */
	public boolean isValide(Node2 Node2){
		
		Node2 Node2Copy = new Node2(Node2);
		for (int i=0;i<Node2Copy.getDomains().size();++i){
			Domain2 d=Node2Copy.get(i);
			int val1=d.getValeurs().getFirst();
			for (int n=i+1;n<Node2Copy.getDomains().size();++n){
				Domain2 d2=Node2Copy.get(n);
				int val2=d2.getValeurs().getFirst();
				//Test Colonne pas dans le m�me colonne
				if(val1==val2){
					return false; // si dans la m�me colonne
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
	 * Test si une affectation donn�e est valide. Version am�lior� sans v�rification inutiles.
	 * 
	 * @param Node2 = Node2 ou chacun des Domain2s est r�duit � une seule valeur
	 *  
	 * @return true si l'affectation est valide false sinon
	 */
	public boolean isValideV2(Node2 Node2){
		
		Node2 Node2Copy = new Node2(Node2);
		int lastVal=Node2.getDomains().size()-1;
		if (lastVal>0){ 
			Domain2 Domain2Last=Node2Copy.get(lastVal);
			int val = Domain2Last.getValeurs().getFirst(); 
			for (int i=0;i<Node2Copy.getDomains().size()-1;++i){
				Domain2 d=Node2Copy.get(i);
				int val2=d.getValeurs().getFirst();
				
				//Test Colonne pas dans le m�me colonne
				if(val2==val){
					return false; // si dans la m�me colonne
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
	 * Premi�re version du Backtracking pour le probl�me des queens. Fonction r�cursive
	 * 
	 * @param n = le Node
	 * @return int : le nombre de solution trouv�
	 * 
	 */
	public int backTrackingQueenPrune(Node2 n){
		Node2 copyNode= new Node2(n);
		Node2 copyNode2= new Node2();
		Domain2 Domain2= new Domain2();
		LinkedList<Integer> valeurs= new LinkedList<Integer>();
		LinkedList<Integer> tree= new LinkedList<Integer>();
		boolean solution=true;
		int compteur=0;
		int compteur2=0;
		int nbSolution=0;
		
		while((solution)&&(compteur<copyNode.getDomains().size())){
			//Si la taille du domain est de 1 alors on a plus besoin de travailler dessus on passe au suivant
			if(copyNode.get(compteur).getValeurs().size()==1){
				copyNode2.add(copyNode.get(compteur));
				++compteur;
			}else{
				solution=false; // Un des Domaines a encore plusieurs valeurs -> on est pas encore arriver � une solution
			}
			
		}
		if(solution){
			//Affichage prend trop de temper enlevez sauf pour debug
			//System.out.println("Une solution !");
			//System.out.println(n.toStringQueen());
			return ++nbSolution;
			
		}else{
			
			//On copy les valeurs du premier domaine de la liste qui � encore une taille sup�rieur � 1
			valeurs=copyNode.get(compteur).getValeurs();
			for (Integer val : valeurs){
				compteur2=0;
				tree.add(val);
				Domain2.setValeurs(tree);
				copyNode2.add(Domain2);
				if(isValideV2(copyNode2)){//Cette combinaison marche pour l'instant on avance
					for (int i=compteur+1;i<copyNode.getDomains().size();++i){ // Ajout des autres domaines
						copyNode2.add(copyNode.get(i));
						++compteur2;	
					}
					// On a reduit un domaine de plus � la taille de 1 on fait l'apelle recursif
					nbSolution+=backTrackingQueenPrune(copyNode2);
					
					for (int i=0;i<compteur2;++i){// On suprime les domaines ajout� dans le foreach pr�cedent
						copyNode2.removeLast();
					}
				}
				copyNode2.removeLast();// Enl�ve pour essayer la prochaine valeur
				tree.clear();				
			}
		}
		return nbSolution;
	}
	
	

		
	
	
	public static void main(String[] args) {
		BackTrackingQueen backQ= new BackTrackingQueen();
		
		Node2 n = new Node2();
		LinkedList<Integer> tree= new LinkedList<Integer>();
		double chrono=System.currentTimeMillis();
		
		int nbCase=13;// Les dimensions de l'�chequier
		
		for (int i=0;i<nbCase;++i){
			tree.add(i+1);
		}
		
		for (int i=0;i<nbCase;++i){
			Domain2 d= new Domain2();
			d.setValeurs(tree);
			n.add(d);
		}
		System.out.println("Taille de l'�ch�quier "+nbCase);
		
		
		System.out.println(backQ.backTrackingQueenPrune(n)+" solutions trouv�");
		chrono=System.currentTimeMillis()-chrono;
		chrono=chrono/1000;
		System.out.println("En "+chrono+" secondes");
		

	}

	
	
	

}
