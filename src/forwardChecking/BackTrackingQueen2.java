package forwardChecking;

import java.util.LinkedList;
import java.util.TreeSet;

import backTracking.BackTrackingQueen;
import backTracking.Domain;
import backTracking.Node;

public class BackTrackingQueen2 {


	/**
	 * @brief test si une affectation donnée est possible
	 * @param Node2 = Node2 ou chacun des Domains est réduit à une seule valeur
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
	 * @brief test si une affectation donnée est possible. Version am�lior� sans v�rification inutiles.
	 * @param Node2 = Node2 ou chacun des Domain2s est réduit à une seule valeur
	 *  
	 * @return true si l'affectation est valide false sinon
	 */
	public boolean isValideV2(Node2 node){
		
		Node2 Node2Copy = new Node2(node);
		int lastVal=node.getDomains().size()-1;
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
			
			if(Math.abs(lastValAdd-val)==index){
				check =false;
			}
			
			if (check){
				domainMod.addLast(val);
			}
		}
		
		
		
		return domainMod;
	}
	
	
	
	/**
	 * @brief Premi�re version du Backtracking pour le probl�me des queens. Fonction r�cursive
	 * @param n = le Node2
	 * @return int : le nombre de solution trouv�
	 * @warning la fonction fait elle m�me l'affichage et la compl�xit� est tr�s mauvaise
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
		int nbSolution=0;
		
		while((solution)&&(compteur<copyNode.getDomains().size())){
			//Si la taille du Domain2 est de 1 alors on a plus besoin de travailler dessus on passe au suivant
			//copyNode.getDomains().get(compteur).getValeurs().size()==1
			if(copyNode.get(compteur).getValeurs().size()==1){
				copyNode2.add(copyNode.get(compteur));
				copyNode3.add(copyNode.get(compteur));
				++compteur;
			}else{
				solution=false; // Un des Domain2es a encore plusieur valeurs -> on est pas encore arriver � une solution
			}
			
		}
		if(solution){
			System.out.println("Une solution !");
			System.out.println(n.toStringQueen());
			return ++nbSolution;
			
		}else{
			
			//On copy les valeurs du premier Domaine de la liste qui � encore une taille sup�rieur � 1
			valeurs=copyNode.get(compteur).getValeurs();
			for (Integer val : valeurs){
				boolean valide = true; // Passe � false si l'un des domaines est r�duit � 0 par le forward check
				boolean valide2 = true;
				compteur2=0;
				tree.add(val);
				domain2.setValeurs(tree);
				copyNode2.add(domain2);
				copyNode3.add(domain2);
				int i=compteur+1;
				if(isValideV2(copyNode2)){//Cette combinaison marche pour l'instant on avance
					//for (int i=compteur+1;i<copyNode.getDomains().size();++i){
					while ((i<copyNode.getDomains().size())&&(valide)&&(valide2)){
						//Je foward check ici
						//copyNode2.add(copyNode.get(i));
						Domain2 domainTempo =fowardCheck(val, compteur2+1,copyNode.get(i)); // compteur2+1 le nombrede ligne d'�cart
						if(domainTempo.getValeurs().size()<=0){
							valide = false;							
						}else{
							copyNode2.add(domainTempo);
							copyNode3.add(domainTempo);
							if((domainTempo.getValeurs().size()==1)){
								valide2=isValideV2(copyNode3);
								}
							++compteur2;
							++i;
							copyNode3.removeLast();
						}
						
					}
					if(valide&&valide2){
						nbSolution+=backTrackingQueenPrune2(copyNode2);
					}
					
					// On a reduit un Domaine de plus � la taille de 1 on fait l'apelle recursif
					
					for (int p=0;p<compteur2;++p){// On suprime les Domain2es ajouter dans le foreach pr�cedent
						copyNode2.removeLast();
					}
				}
				copyNode2.removeLast();// Enl�ve pour essayer la prochaine valeur
				//copyNode3.removeLast();
				tree.clear();				
			}
		}
		return nbSolution;
	}
	
	

		
	
	
	public static void main(String[] args) {
		BackTrackingQueen2 backQ= new BackTrackingQueen2();
		Domain2 d= new Domain2();
		Node2 n = new Node2();
		LinkedList<Integer> tree= new LinkedList<Integer>();
		double chrono=System.currentTimeMillis();
		
		int nbCase=5;// Les dimensions de l'�chequier
		
		for (int i=0;i<nbCase;++i){
			tree.add(i+1);
		}
		
		for (int i=0;i<nbCase;++i){
			d.setValeurs(tree);
			n.add(d);
		}
		System.out.println("Taille de l'�ch�quier "+nbCase);
		
		
		System.out.println(backQ.backTrackingQueenPrune2(n)+" solutions trouv�");
		chrono=System.currentTimeMillis()-chrono;
		chrono=chrono/1000;
		System.out.println("En "+chrono+" secondes");
		

	}


}
