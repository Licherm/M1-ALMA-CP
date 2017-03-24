
package carreMagique;

import java.util.ArrayList;
import java.util.LinkedList;

import backTracking.BackTrackingQueen;
import domain.*;
import node.*;

public class BackTrackingCarre {





	/**
	 * @brief test si une affectation donnÃ©e est possible. Version amélioré sans vérification inutiles.
	 * @param Node3 = Node3 ou chacun des Domains est rÃ©duit Ã  une seule valeur
	 *  
	 * @return true si l'affectation est valide false sinon
	 */
	public boolean isValide(Node3 node3){

		Node3 nodeCopy = new Node3(node3);
		int lastVal=node3.getDomains().size()-1;
		int nbMagique = node3.getNbMagique();
		int nbCase= node3.getNbCase();
		if (lastVal>0){ 
			Domain3 domainLast=nodeCopy.get(lastVal);
			int val = domainLast.getValeurs().getFirst();

			int ligneLast=domainLast.getLigne();
			int colonneLast = domainLast.getColonne();

			int cptColonne=1;// Comtpeur qui nous indique si un ligne est remplie
			int cptLigne=1;// Pareil ligne
			int cptDiago=-1000;// Pareil diagonale. Si le compteur reste à -1000 on est pas sur un case des diagonales.
			int cptDiago2=-1000;

			//On va stocker les autres case qui sont sur la même ligne,colonne,diagonale dans ces ArrayList
			//pour enssuite faire les tests dessus
		
			int sommeLigne=val;
			int sommeColonne=val;
			int sommeDiago=val;
			int sommeDiago2=val;
			
			if(domainLast.getColonne()==domainLast.getLigne()){
				// On est sur une diagonale
				cptDiago=1;
			}
			if (domainLast.getColonne()==(nbCase-domainLast.getLigne()+1)){
				//l'autre diagonale

				cptDiago2=1;
			}
			if(cptDiago==1 || cptDiago2==1){
				for (int i=0;i<nodeCopy.getDomains().size()-1;++i){
					Domain3 d=nodeCopy.get(i);
					int val2=d.getValeurs().getFirst();

					if(val==val2){
						return false;
					}
					//Test même ligne
					if(ligneLast==d.getLigne()){
						sommeLigne+=val2;
						++cptLigne;
					}

					//Test même colonne
					if(colonneLast==d.getColonne()){
						sommeColonne+=val2;
						++cptColonne;
					}


					//Test la diagonale 1
					if (d.getColonne()==d.getLigne()){
						sommeDiago+=val2;
						++cptDiago;
					}

					if(d.getColonne()==(nbCase-d.getLigne()+1)){
						sommeDiago2+=val2;
						++cptDiago2;
					}

				}

			}else{// On s'enfou des diagonale 
				for (int i=0;i<nodeCopy.getDomains().size()-1;++i){
					Domain3 d=nodeCopy.get(i);
					int val2=d.getValeurs().getFirst();
					if(val==val2){
						return false;
					}
					//Test même ligne
					if(ligneLast==d.getLigne()){
						sommeLigne+=val2;
						++cptLigne;
					}

					//Test même colonne
					if(colonneLast==d.getColonne()){
						sommeColonne+=val2;
						++cptColonne;
					}
				}
			}

			
			//-------Ligne----------
			if(cptLigne==nbCase){
				if (sommeLigne!=nbMagique){
					return false;
				}
			}else{
				//On vérifie qu'on dépasse pas le nbMagique en faisant la somme de la ligne
				//(nbCase-cptLigne) pour être sur qu'on peut ne pas dépasser 
				//le nbMagique en ajoutant que des 1 dans les cases restantes de la ligne
				if((sommeLigne+(nbCase-cptLigne))>nbMagique){
					return false;
				}
				//On vérifie qu'on peut encore atteindre le nbMagique en rajoutant que des N^2
				// dans les cases restante de la ligne (N=nbCase)
				if(((nbCase-cptLigne)*Math.pow(nbCase, 2)+sommeLigne)<nbMagique){
					
					return false;
				}
			}
			
			// --------Colonne---------				
			
			if(cptColonne==nbCase){
				if (sommeColonne!=nbMagique){
					return false;
				}
			}else{
				//On vérifie qu'on dépasse pas le nbMagique en faisant la somme de la Colonne
				//(nbCase-cptColonne) pour être sur qu'on peut ne pas dépasser 
				//le nbMagique en ajoutant que des 1 dans les cases restantes de la Colonne
				if(sommeColonne+(nbCase-cptColonne)>nbMagique){


					return false;
				}
				//On vérifie qu'on peut encore atteindre le nbMagique en rajoutant que des N^2
				// dans les cases restante de la Colonne (N=nbCase)
				if(((nbCase-cptColonne)*Math.pow(nbCase, 2)+sommeColonne)<nbMagique){
					return false;
				}
			}
				//------Diagonale-----------
			if(cptDiago>=1){
				if(cptDiago==nbCase){
					if (sommeDiago!=nbMagique){
						return false;
					}
				}else{
					//On vérifie qu'on dépasse pas le nbMagique en faisant la somme de la diagonal
					//(nbCase-cptColonne) pour être sur qu'on peut ne pas dépasser 
					//le nbMagique en ajoutant que des 1 dans les cases restantes de la diagonal
					if((sommeDiago+(nbCase-cptDiago))>nbMagique){
						return false;
					}
					//On vérifie qu'on peut encore atteindre le nbMagique en rajoutant que des N^2
					// dans les cases restante de la diagonal (N=nbCase)
					if(((nbCase-cptDiago)*Math.pow(nbCase, 2)+sommeDiago)<nbMagique){
						return false;
					}
				}
				
				
			}
				//------Diagonale2-----------
			if(cptDiago2>=1){
				if(cptDiago2==nbCase){
					if (sommeDiago2!=nbMagique){
						return false;
					}
				}else{
					//On vérifie qu'on dépasse pas le nbMagique en faisant la somme de la diagonal
					//(nbCase-cptColonne) pour être sur qu'on peut ne pas dépasser 
					//le nbMagique en ajoutant que des 1 dans les cases restantes de la diagonal
					//System.out.println("ici = "+(nbCase-cptDiago2));
					if((sommeDiago2+(nbCase-cptDiago2))>nbMagique){
						return false;
					}
					//On vérifie qu'on peut encore atteindre le nbMagique en rajoutant que des N^2
					// dans les cases restante de la diagonal (N=nbCase)
					if((sommeDiago2+(nbCase-cptDiago2)*Math.pow(nbCase, 2))<nbMagique){
						return false;
					}
				}	
			}
			}	
		return true;

	}


	/**
	 *  Backtracking pour le problème des carre magique. Fonction récursive.
	 * 
	 * @param n = le Node3
	 * @return int : le nombre de solution trouvé
	 * @warning la fonction fait elle même l'affichage et la compléxité est très mauvaise
	 * 
	 */
	public int pruneCarreBT(Node3 n){
		Node3 copyNode= new Node3(n);
		Node3 copyNode2= new Node3();
		Domain3 domain3= new Domain3();
		LinkedList<Integer> valeurs= new LinkedList<Integer>();
		LinkedList<Integer> tree= new LinkedList<Integer>();
		boolean solution=true;
		int compteur=0;
		int compteur2=0;
		int nbSolution=0;

		while((solution)&&(compteur<copyNode.getDomains().size())){
			//Si la taille du Domain3 est de 1 alors on a plus besoin de travailler dessus on passe au suivant
			//copyNode.getDomains().get(compteur).getValeurs().size()==1
			if(copyNode.get(compteur).getValeurs().size()==1){
				copyNode2.add(copyNode.get(compteur));
				++compteur;
			}else{
				solution=false; // Un des Domain3es a encore plusieur valeurs -> on est pas encore arriver à une solution
			}

		}
		if(solution){
			//Affichage prend trop de temper enlevez sauf pour debug
			//System.out.println("Une solution !");
			//System.out.println(n.toStringCarreMagique()+'\n');
			return ++nbSolution;

		}else{

			//On copy les valeurs du premier Domain3e de la liste qui à encore une taille supérieur à 1
			valeurs=copyNode.get(compteur).getValeurs();
			copyNode2.setNbCase(n.getNbCase());
			copyNode2.setNbMagique(n.getNbMagique());
			
			domain3.setColonne(copyNode.get(compteur).getColonne());
			domain3.setLigne(copyNode.get(compteur).getLigne());
			for (Integer val : valeurs){
				compteur2=0;
				tree.add(val);
				domain3.setValeurs(tree);
				copyNode2.add(domain3);
				if(isValide(copyNode2)){//Cette combinaison marche pour l'instant on avance
					for (int i=compteur+1;i<copyNode.getDomains().size();++i){ // Ajout des autres Domain3es
						copyNode2.add(copyNode.get(i));
						++compteur2;	
					}
					// On reduit un Domain3e de plus à la taille de 1 on fait l'apelle recursif
					nbSolution+=pruneCarreBT(copyNode2);

					for (int i=0;i<compteur2;++i){// On suprime les Domain3es ajouter dans le foreach précedent
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
		BackTrackingCarre backQ= new BackTrackingCarre();

		Node3 n = new Node3();
		LinkedList<Integer> tree= new LinkedList<Integer>();
		double chrono=System.currentTimeMillis();

		int nbCase=4;// Les dimensions de l'échequier

		n.setNbCase(nbCase);
		n.calculeNbMagique(nbCase);
		for (int i=0;i<Math.pow(nbCase,2);++i){
			tree.add(i+1);
		}
		System.out.println("nbMagique = "+ n.getNbMagique());
		for (int i=0;i<nbCase;++i){
			for(int p=0;p<nbCase;++p){
				Domain3 d= new Domain3();
				d.setValeurs(tree);
				d.setLigne(i+1);
				d.setColonne(p+1);
				n.add(d);
			}

		}
		
		System.out.println("Taille du carre "+nbCase);


		System.out.println(backQ.pruneCarreBT(n)+" solutions trouvé");
		chrono=System.currentTimeMillis()-chrono;
		chrono=chrono/1000;
		System.out.println("En "+chrono+" secondes");


	}

}