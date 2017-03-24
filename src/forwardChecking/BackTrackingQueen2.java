package forwardChecking;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;

import backTracking.BackTrackingQueen;
import domain.Domain;
import domain.Domain2;
import node.Node;
import node.Node2;

public class BackTrackingQueen2 {


	
	
	/**
	 * @brief test si une affectation donnée est possible. Version am�lior� sans v�rification inutiles.
	 * @param Node2 = Node2 ou chacun des Domain2s est réduit à une seule valeur
	 *  
	 * @return true si l'affectation est valide false sinon
	 */
	public boolean isValideV2(Node2 node){
		
		Node2 Node2Copy = new Node2(node);
		if (node.getDomains().size()-1>0){ 
			Domain2 Domain2Last=Node2Copy.get(node.getDomains().size()-1);
			int val = Domain2Last.getValeurs().getFirst();
			int lastValLigne=Domain2Last.getLigne();
			for (int i=0;i<Node2Copy.getDomains().size()-1;++i){
				Domain2 d=Node2Copy.get(i);
				int val2=d.getValeurs().getFirst();
				int ligneActuelle=d.getLigne();				
				//Test Colonne pas dans le m�me colonne
				if(val2==val){
					return false; // si dans la m�me colonne
				}
				//Test la diagonale
				if (Math.abs(val2-val)==(lastValLigne-ligneActuelle)){
					return false; // sur la diagonale
				}
							
			}
		}
		return true;
		
	}
	
	/**
	 * @brief Forward check les domains non encore fix�
	 * @param LastValAdd = derni�re valeur attribu�e dans le prune
	 *  @param index = pour la diagonal indique le nombre de ligne d'�cart
	 *  @param domain = le domain qu'on veut prune
	 * @return Domain2 = le domain
	 */
	public Domain2 fowardCheck(int lastValAdd,int index, Domain2 domain){
		
		Domain2 domainMod = new Domain2(); // Le domain modifi� renvoy� par la fonction
		boolean check = true;
		for (int val : domain.getValeurs() ){
			check=true;
			if(val==lastValAdd){
				check=false;
			}
			// TODO : ce if provoque le bug trouver pourquoi
			if(Math.abs(val-lastValAdd)==index){
				check =false;
			}			
			if (check){
				domainMod.addLast(val);
			}
		}
		domainMod.setLigne(domain.getLigne());
		return domainMod;
	}
	
	
	
	/**
	 *  Traitement du probl�me des queens avec forward checking.
	 *  
	 * @param n = le Node2
	 * @return int : le nombre de solution trouv�
	 * 
	 */
	public int backTrackingQueenPrune2(Node2 n){
		Node2 copyNode= new Node2(n);
		Node2 copyNode2= new Node2();
		Node2 copyNode3= new Node2();
		Domain2 domain2= new Domain2();
		LinkedList<Integer> valeurs= new LinkedList<Integer>();
		LinkedList<Integer> tree= new LinkedList<Integer>();
		boolean solution=true;
		int compteur=0;
		int compteur2=0;
		int compteur3=0;// Utile pendant le foward check
		int nbSolution=0;
		int ligne=0;
		while((solution)&&(compteur<copyNode.getDomains().size())){
			//Si la taille du Domain2 est de 1 alors on a plus besoin de travailler dessus on passe au suivant
			//copyNode.getDomains().get(compteur).getValeurs().size()==1
			if(copyNode.get(compteur).getValeurs().size()==1){
				copyNode2.add(copyNode.get(compteur));
				++compteur;
			}else{
				solution=false; // Un des Domaines a encore plusieur valeurs -> on est pas encore arriver � une solution
			}
			
		}
		if(solution){
			//Affichage prend trop de temper enlevez sauf pour debug
			//System.out.println("Une solution !");
			//System.out.println(n.toStringQueen());
			return ++nbSolution;
			
		}else{
			
			//On copy les valeurs du premier Domaine de la liste qui � encore une taille sup�rieur � 1
			copyNode3.setDomains(new ArrayList<Domain2>(copyNode2.getDomains()));
			valeurs=copyNode.get(compteur).getValeurs();
			ligne=copyNode.getLigneAt(compteur);//On oublie pas de set la ligne
			for (Integer val : valeurs){
				boolean valide = true; // Passe � false si l'un des domaines est r�duit � 0 par le forward check
				boolean valide2 = true;
				compteur2=0;
				compteur3=0;
				tree.add(val);
				domain2.setValeurs(tree);
				domain2.setLigne(ligne);
				copyNode2.add(domain2);
				copyNode3.add(domain2);
				int i=compteur+1;
				if(isValideV2(copyNode2)){//Cette combinaison marche pour l'instant on avance
					//for (int i=compteur+1;i<copyNode.getDomains().size();++i){
					while ((i<copyNode.getDomains().size())&&(valide)&&(valide2)){
						//Je foward check ici
						//copyNode.getLigneAt(i)-ligne = le nombre de ligne d'�cart pour la diagonal
						Domain2 domainTempo =fowardCheck(val,copyNode.getLigneAt(i)-ligne,copyNode.get(i)); 
						if(domainTempo.getValeurs().size()<=0){
							valide = false;							
						}else{
							copyNode2.add(domainTempo);
							// On doit utiliser copyNode3 car isValide ne support pas des Node avec des variables
							//ayant un domiane d'une cardinalit� sup�rieur � 1
							copyNode3.add(domainTempo);
							++compteur2;
							// If obligatoire sinon on valide des mauvaises solutions car les domaines de taille 1 
							//ne sont pas check au d�but de la fonction
							if((domainTempo.getValeurs().size()==1)){
									valide2=isValideV2(copyNode3);
									++compteur3;
								}else{
									copyNode3.removeLast();// On veut pas de domaine de size >1 dans copyNode3
								}
							
							++i;
						}
						
					}
					if(valide&&valide2){
						nbSolution+=backTrackingQueenPrune2(copyNode2);
					}
					
					// On a reduit un Domaine de plus � la taille de 1 on fait l'apelle recursif
					
					for (int p=0;p<compteur2;++p){// On suprime les Domain2es ajouter dans le foreach pr�cedent
						copyNode2.removeLast();
					}
					
					for(int p=0;p<compteur3;++p){
						copyNode3.removeLast();
					}
				}
				copyNode2.removeLast();// Enl�ve pour essayer la prochaine valeur
				tree.clear();				
			}
		}
		return nbSolution;
	}
	
	

		
	
	
	public static void main(String[] args) {
		BackTrackingQueen2 backQ= new BackTrackingQueen2();
		//Domain2 d= new Domain2();
		Node2 n = new Node2();
		LinkedList<Integer> tree= new LinkedList<Integer>();
		double chrono=System.currentTimeMillis();
		
		int nbCase=15;// Les dimensions de l'�chequier
		
		for (int i=0;i<nbCase;++i){
			tree.add(i+1);
		}
		
		for (int i=0;i<nbCase;++i){
			Domain2 d= new Domain2();

			d.setValeurs(tree);
			d.setLigne(i+1);
			n.add(d);
		}
		System.out.println("Taille de l'�ch�quier "+nbCase);
		
		
		System.out.println(backQ.backTrackingQueenPrune2(n)+" solutions trouv�");
		chrono=System.currentTimeMillis()-chrono;
		chrono=chrono/1000;
		System.out.println("En "+chrono+" secondes");
		

	}


}
