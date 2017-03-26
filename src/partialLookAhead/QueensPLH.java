package partialLookAhead;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ListIterator;

public class QueensPLH {
	private static final int NB_QUEENS = 13;
	private static final int DOM_SIZE = NB_QUEENS * NB_QUEENS;
	
	public static class Node {
		// le domaine est une matrice de booleens représentant l'échiquier avec les cases encore possibles (true) ou pas;
		// elle est écrasée dans une arraylist pour avoir un seul index.
		private ArrayList<Boolean> domains;
		private ArrayList<Integer> vars;
		private ArrayList<Integer> domSizes;
		private LinkedHashSet<Integer> unaffectedInds;
		private ArrayList<LinkedHashSet<Integer>> possibleVals;
		private int affCount;
		private int smallest;
		
		public Node() {
			LinkedHashSet<Integer> tmpVals;
			vars = new ArrayList<Integer>(NB_QUEENS);
			domSizes = new ArrayList<Integer>(NB_QUEENS);
			unaffectedInds = new LinkedHashSet<Integer>();
			domains = new ArrayList<Boolean>(DOM_SIZE);
			possibleVals = new ArrayList<LinkedHashSet<Integer>>();
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
				unaffectedInds.add(i);
			}
			for(int i = 0; i < NB_QUEENS; ++i) {
				tmpVals = new LinkedHashSet<Integer>();
				for(int j = 0; j < NB_QUEENS; j++) {
					tmpVals.add(j);
				}
				possibleVals.add(tmpVals);
			}
			affCount = 0;
			smallest = NB_QUEENS / 2;
		}
		
		public void incr (ArrayList<Integer> list, int index) {
			list.set(index, list.get(index) + 1);
		}
		
		public void decr (ArrayList<Integer> list, int index) {
			list.set(index, list.get(index) - 1);
		}
	}
	
	public static int branchAndPrune(Node node) {
		int sols = 0;
		int value; // valeur a pruner si valide
		int smallest = node.smallest; // index du plus petit domaine dans le tableau de tailles
		int domInd, valInd, diagInd; // valeurs temporaires pour les calculs du pruning
		int pruneInd; // valeurs temporaires pour le backtracking
		boolean stillValid, validDiag;
		
		ArrayList<Integer> workingNode = node.vars;
		ArrayList<Boolean> workingDom = node.domains;
		LinkedHashSet<Integer> unAffected = node.unaffectedInds;
		Iterator<Integer> unaffIt;
		
		// sauvegarde du nombre de valeurs restantes dans chaque domains, incrémenté au pruning décrémenté au backtracking
		ArrayList<Integer> workingSizes = node.domSizes;
		
		// sauvegarde des valeur que l'on prune pour restaurer le noeud lors du changement de valeur
		LinkedList<Integer> pruneSave = new LinkedList<Integer>();

		Iterator<Integer> valIt;
		ListIterator<Integer> pruneIt;
		
		valIt = node.possibleVals.get(smallest).iterator();
		
		while (valIt.hasNext()) {
			// récupération de la valeur a étudier (indice de colonne dans la matrice)
			value = valIt.next();
			
			// affectation de la premiere valeur du domaine
			workingNode.set(smallest, value);
			unAffected.remove(smallest);
			++node.affCount;
			
			if ( node.affCount == NB_QUEENS ) {
				// la combinaison est une solution
				//System.out.println("SOLUTION : "+printQueens(workingNode));
				++sols;
			} else {
				// pruning domains
				stillValid = true;
				domInd = 0;
				node.smallest = unAffected.iterator().next();
				unaffIt = unAffected.iterator();
				while(unaffIt.hasNext()) {
					domInd = unaffIt.next();
					valInd = domInd * NB_QUEENS + value;
					// meme colonne
					if(workingDom.get(valInd)) {
						workingDom.set(valInd, false);
						workingSizes.set(domInd, workingSizes.get(domInd)-1);
						pruneSave.add(valInd);
						node.possibleVals.get(domInd).remove(value);
					}
					
					diagInd = valInd - (domInd - smallest);
					validDiag = diagInd >= domInd * NB_QUEENS && diagInd < (domInd + 1) * NB_QUEENS;
					
					// premiere diagonale
					if(validDiag) {
						if(workingDom.get(diagInd)) {
							workingDom.set(diagInd, false);
							workingSizes.set(domInd, workingSizes.get(domInd)-1);
							pruneSave.add(diagInd);
							node.possibleVals.get(domInd).remove(diagInd % NB_QUEENS);
						}
					}
					
					diagInd = valInd + (domInd - smallest);
					validDiag = diagInd >= domInd * NB_QUEENS && diagInd < (domInd + 1) * NB_QUEENS;
					// deuxieme diagonale
					if(validDiag) {
						if(workingDom.get(diagInd)) {
							workingDom.set(diagInd, false);
							workingSizes.set(domInd, workingSizes.get(domInd)-1);
							pruneSave.add(diagInd);
							node.possibleVals.get(domInd).remove(diagInd % NB_QUEENS);
						}
					}
					
					if(workingSizes.get(domInd) == 0) {
						// un domaine a été réduit à zéro, on abandonne la branche en cours
						stillValid = false;
						node.smallest = smallest;
					} else if (workingSizes.get(domInd) < workingSizes.get(node.smallest)) {
						// sinon on vérifie si c'est le plus petit qu'on ai rencontré jusque la
						node.smallest = domInd;
					}
				}

				// descente dans l'arbre de recherche
				if (stillValid) {
					sols += branchAndPrune(node);
				}
				
				// backtracking - unpruning
				pruneIt = pruneSave.listIterator();
				while(pruneIt.hasNext()) {
					pruneInd = pruneIt.next();
					workingDom.set(pruneInd, true);
					workingSizes.set(pruneInd / NB_QUEENS, workingSizes.get(pruneInd / NB_QUEENS) + 1);
					node.possibleVals.get(pruneInd / NB_QUEENS).add(pruneInd % NB_QUEENS);
					pruneIt.remove();
				}
				
				
			}
			// backtracking - unbranching
			workingNode.set(smallest, -1);
			unAffected.add(smallest);
			node.smallest = smallest;
			--node.affCount;
		}
		
		return sols;
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
	public static String printDom(Node node) {
		String printer = "";
		for(int i = 0; i < node.domains.size(); ++i) {
			if(i % NB_QUEENS == 0 && i != 0){
				printer += '\n';
			}
			if (node.unaffectedInds.contains(i / NB_QUEENS)) {
				if(node.domains.get(i)) printer+='1';
				else printer += '0';
			} else {
				if(node.vars.get(i / NB_QUEENS) == i % NB_QUEENS ) printer+='O';
				else printer+='X';
			}
			
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
