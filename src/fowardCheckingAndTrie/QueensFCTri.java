package fowardCheckingAndTrie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ListIterator;

public class QueensFCTri {
	private static final int NB_QUEENS = 4;
	private static final int DOM_SIZE = NB_QUEENS * NB_QUEENS;
	
	public static class Node {
		// le domaine est une matrice de booleens représentant l'échiquier avec les cases encore possibles (true) ou pas;
		// elle est écrasée dans une arraylist pour avoir un seul index.
		private ArrayList<Boolean> domains;
		private ArrayList<Integer> vars;
		private ArrayList<Integer> domSizes;
		private ArrayList<Integer> firstValInds;
		private ArrayList<Integer> lastValInds;
		private LinkedHashSet<Integer> unaffectedInds;
		private int affCount;
		
		public Node() {
			vars = new ArrayList<Integer>(NB_QUEENS);
			domSizes = new ArrayList<Integer>(NB_QUEENS);
			firstValInds = new ArrayList<Integer>(NB_QUEENS);
			lastValInds = new ArrayList<Integer>(NB_QUEENS);
			unaffectedInds = new LinkedHashSet<Integer>();
			domains = new ArrayList<Boolean>(DOM_SIZE);
			for(int i = 0; i < DOM_SIZE; ++i) {
				domains.add(true);
			}
			for(int i = 0; i < NB_QUEENS; ++i) {
				domSizes.add(NB_QUEENS);
			}
			for(int i = 0; i < NB_QUEENS; ++i) {
				vars.add(-1);
			}
			for(int i = 0; i < NB_QUEENS; ++i) {
				firstValInds.add(0);
			}
			for(int i = 0; i < NB_QUEENS; ++i) {
				lastValInds.add(0);
			}
			for(int i = 0; i < NB_QUEENS; ++i) {
				unaffectedInds.add(i);
			}
			affCount = 0;
		}
	}
	
	public static int branchAndPrune(Node node) {
		int sols = 0;
		int value; // valeur a pruner si valide
		int smallest; // index du plus petit domaine dans le tableau de tailles
		int smallestSize;
		int domStart, domEnd; //index de debut et fin du domaine sur lequel on travaille dans la matrice de domaines
		int domInd, valInd, diagInd; // valeurs temporaires pour les calculs du pruning
		boolean stillValid, validDiag;
		boolean newAff = true;
		
		smallest = node.affCount == 0 ? NB_QUEENS / 2 : findSmallest(node.domSizes);
		smallestSize = node.domSizes.get(smallest);
		
		domStart = smallest * NB_QUEENS;
		domEnd = (smallest + 1) * NB_QUEENS - 1;
		
		ArrayList<Integer> workingNode = node.vars;
		ArrayList<Boolean> workingDom = node.domains;
		LinkedHashSet<Integer> unAffected = node.unaffectedInds;
		
		// sauvegarde du nombre de valeurs restantes dans chaque domains, incrémenté au pruning décrémenté au backtracking
		ArrayList<Integer> workingSizes = node.domSizes;
		
		// sauvegarde des valeur que l'on prune pour restaurer le noeud lors du changement de valeur
		LinkedList<Integer> pruneSave = new LinkedList<Integer>();
		// sauvegarde des valeurs que l'on branch vu qu'il y a un cuisinage partiel en amont.
		LinkedList<Integer> branchSave = new LinkedList<Integer>();
		
		// on se positionne sur le bon domaine
		ListIterator<Boolean> valIt = workingDom.listIterator(domStart);
		ListIterator<Integer> pruneIt;
		
		while (newAff) {
			newAff = false;
			domStart = smallest * NB_QUEENS + node.firstValInds.get(smallest);
			domEnd = (smallest + 1) * NB_QUEENS - 1 - node.lastValInds.get(smallest);
			valIt = workingDom.listIterator(domStart);
			
			while (valIt.hasNext() && valIt.nextIndex() <= domEnd) {
				value = valIt.nextIndex() % NB_QUEENS;
				// récupération de la valeur a étudier (indice de colonne dans la matrice)
				if (valIt.next()) {
					// affectation de la premiere valeur du domaine
					workingNode.set(smallest, value);
					workingSizes.set(smallest, 1);
					unAffected.remove(smallest);
					branchSave.add(smallest);
					
					++node.affCount;
					
					if ( node.affCount == NB_QUEENS ) {
						// la combinaison est une solution
						System.out.println(printQueens(workingNode));
						++sols;
					} else {
						// pruning domains
						domInd = 0;
						// s'arrete si un domaine vide est généré lors du pruning
						stillValid = true;
						while (domInd < NB_QUEENS && stillValid) {
							if (workingSizes.get(domInd) > 1 && domInd != smallest) {
								// on ne branch pas les domaines dans l'ordre des variables,
								// il faut donc sauter celui en cours et ceux qui sont déja attribués.
							
								// une seule reine par colonne
								valInd = domInd * NB_QUEENS + value;
								if(workingDom.get(valInd)) {
									workingDom.set(valInd, false);
									workingSizes.set(domInd, workingSizes.get(domInd)-1);
									pruneSave.add(valInd);
								}
								
								diagInd = valInd - (domInd - smallest);
								validDiag = diagInd >= domInd * NB_QUEENS && diagInd < (domInd + 1) * NB_QUEENS;
								
								// diagonale gauche
								if(validDiag) {
									if(workingDom.get(diagInd)) {
										workingDom.set(diagInd, false);
										workingSizes.set(domInd, workingSizes.get(domInd)-1);
										pruneSave.add(diagInd);
									}
								}
								
								diagInd = valInd + (domInd - smallest);
								validDiag &= diagInd >= domInd * NB_QUEENS && diagInd < (domInd + 1) * NB_QUEENS;
								// diagonale droite
								if(validDiag) {
									if(workingDom.get(diagInd)) {
										workingDom.set(diagInd, false);
										workingSizes.set(domInd, workingSizes.get(domInd)-1);
										pruneSave.add(diagInd);
									}
								}
								
								if(workingSizes.get(domInd) == 0) {
									// un domaine a été réduit à zéro, on abandonne la branche en cours
									stillValid = false;
								}
								
								if(workingSizes.get(domInd) == 1 ) {
									// un domaine a été réduit à 1, on peut l'affecter et pruner dessus -> partialCookAhead
									newAff = true;
									
									smallest = domInd;
								}
							}
							++domInd;
						}
						// descente dans l'arbre de recherche
						if (stillValid && !newAff) {
							sols += branchAndPrune(node);
						}
						
						// backtracking - unpruning
						if ( !newAff ) {
						pruneIt = pruneSave.listIterator();
							while(pruneIt.hasNext()) {
								valInd = pruneIt.next();
								workingDom.set(valInd, true);
								workingSizes.set(valInd / NB_QUEENS, workingSizes.get(valInd / NB_QUEENS) + 1);
								pruneIt.remove();
							}
						}
					}
					// backtracking - unbranching
					if ( !newAff ) {
						workingNode.set(smallest, -1);
						workingSizes.set(smallest, smallestSize);
						unAffected.add(smallest);
						--node.affCount;
					}
				}
			}
			
		}
		
		return sols;
	}
	
	public static int findSmallest(ArrayList<Integer> list, LinkedHashSet<Integer> indexes) {
		// retourne l'index plus petit int > 0 dans list. (en regardant seulement des index contenus dans indexes)
		int min, small;
		int tmp1, tmp2;
		int ind;
		int minInd;
		int tmpMin;
		Iterator<Integer> it = indexes.iterator();
		if ( indexes.size() > 1 ) {
			// s'il y a au moins deux domains non-attribués
			if ( indexes.size() % 2 == 0) {
				tmp1 = it.next();
				tmp2 = it.next();
				ind = 2;
			}
			else {
				tmp1 = it.next();
				tmp2 = tmp1;
				ind = 1;
			}
			if (list.get(tmp1) < list.get(tmp2)) {
				min = list.get(tmp1);
				minInd = tmp1;
			} else {
				min = list.get(tmp2);
				minInd = tmp2;
			}
			if (min == 2) return minInd;
			for (int i = ind; i < indexes.size(); i = i + 2) {
				tmp1 = it.next();
				tmp2 = it.next();
				if ( list.get(tmp1) < list.get(tmp2)) {
					small = list.get(tmp1);
					tmpMin = tmp1;
				}
				else {
					small = list.get(tmp2);
					tmpMin = tmp2;
				}
				if ( small < min && small > 1 ) {
					min = small;
					minInd = tmpMin;
				}
				if (min == 2) return minInd;
			}
		}
		else {
			// sinon on retourne l'index du seul domaine attribuable;
			minInd = it.next();
		}

		return minInd;
	}
	
	public static int findSmallest(ArrayList<Integer> list) {
		// retourne l'index plus petit int > 1 dans list.
		int min, small;
		int ind;
		int minInd;
		int tmpMin;
		if ( list.get(0) < list.get(1) ) {
			min = list.get(0);
			minInd = 0;
		} else {
			min = list.get(1);
			minInd = 1;
		}
		if (min <= 1) {
			min = NB_QUEENS;
			minInd = NB_QUEENS / 2;
		}
		if (list.size() % 2 == 0) {
			ind = 2;
		} else {
			ind = 1;
		}
		for (int i = ind; i < list.size(); i = i + 2) {
			if ( list.get(i) < list.get(i+1)) {
				small = list.get(i);
				tmpMin = i;
			}
			else {
				small = list.get(i+1);
				tmpMin = i+1;
			}
			if ( small < min && small > 1 ) {
				min = small;
				minInd = tmpMin;
			}
		}
		return minInd;
	}
	
	public static String printQueens(ArrayList<Integer> queens) {
		ListIterator<Integer> valIt = queens.listIterator();
		String printer = "{ ";
		while(valIt.hasNext()) {
			printer += (valIt.next()+1);
			if(valIt.hasNext()) printer += ", ";
		}
		printer += " }";
		return printer;
	}
	
	// debug
	public static String printDom(ArrayList<Boolean> domains) {
		String printer = "";
		for(int i = 0; i < domains.size(); ++i) {
			if(i % 4 == 0){
				printer += '\n';
			}
			
			if(domains.get(i)) printer+='1';
			else printer += '0';
		}
		
		return printer;
	}

	public static void main(String[] args) {
		int sols = 0;
		Node node = new Node();
		
		double chrono=System.currentTimeMillis();

		System.out.println("ForwardChecking avec "+NB_QUEENS+" reines");
		sols = branchAndPrune(node);
		System.out.println(sols+" solutions trouvées.");
		chrono=System.currentTimeMillis()-chrono;
		chrono=chrono/1000;
		System.out.println("En "+chrono+" secondes");
	}
}