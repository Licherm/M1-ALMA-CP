package forwardChecking;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class QueensV6 {
	private static final int NB_QUEENS = 6;
	private static final int DOM_SIZE = NB_QUEENS * NB_QUEENS;
	
	public static int branchAndPrune(LinkedList<Integer> node, ArrayList<Boolean> domain, ArrayList<Integer> domSizes) {
		int sols = 0;
		int value; // valeur a pruner si valide
		int domStart = node.size() * NB_QUEENS;
		int domEnd = (node.size() + 1) * NB_QUEENS - 1;
		int domInd, valInd, diagInd; // valeurs temporaires pour les calculs du pruning
		boolean stillValid;
		
		LinkedList<Integer> workingNode = node;
		ArrayList<Boolean> workingDom = domain;
		LinkedList<Integer> pruneSave = new LinkedList<Integer>();
		// sauvegarde des valeur que l'on prune pour restaurer le noeud lors du changement de valeur
		
		ArrayList<Integer> workingSizes = domSizes;
		// sauvegarde du nombre de valeurs restantes dans chaque domains, incrémenté au pruning décrémenté au backtracking
		
		ListIterator<Boolean> valIt = workingDom.listIterator(domStart);
		ListIterator<Integer> pruneIt;
		
		while (valIt.hasNext() && valIt.nextIndex() <= domEnd) {
			value = valIt.nextIndex() % NB_QUEENS;
			// récupération de la valeur a étudier (indice de colonne dans la matrice)
			if (valIt.next()) {
				workingNode.add(value);
				// affectation de la premiere valeur du domaine
				if ( workingNode.size() == NB_QUEENS ) {
					// la combinaison est une solution
					//System.out.println(printQueens(workingNode));
					++sols;
				} else {
					// pruning domains
					domInd = workingNode.size();
					// s'arrete si un domaine vide est généré lors du pruning
					stillValid = true;
					while (domInd < NB_QUEENS && stillValid) {
						
						// une seule reine par colonne
						valInd = domInd * NB_QUEENS + node.getLast();
						if(workingDom.get(valInd)) {
							workingDom.set(valInd, false);
							workingSizes.set(domInd, workingSizes.get(domInd)-1);
							pruneSave.add(valInd);
						}
						
						// diagonale gauche
						diagInd = valInd - (domInd - (node.size()-1)); 
						if(diagInd >= domInd * NB_QUEENS) {
							if(workingDom.get(diagInd)) {
								workingDom.set(diagInd, false);
								workingSizes.set(domInd, workingSizes.get(domInd)-1);
								pruneSave.add(diagInd);
							}
						}
						
						// diagonale droite
						diagInd = valInd + (domInd - (node.size()-1)); 
						if(diagInd < (domInd + 1) * NB_QUEENS) {
							if(workingDom.get(diagInd)) {
								workingDom.set(diagInd, false);
								workingSizes.set(domInd, workingSizes.get(domInd)-1);
								pruneSave.add(diagInd);
							}
						}
						
						if(workingSizes.get(domInd) == 0) {
							stillValid = false;
						}
						
						++domInd;
					}
					// descente dans l'arbre de recherche
					if (stillValid) {
						sols += branchAndPrune(workingNode, workingDom, domSizes);
					}
					
					// backtracking
					pruneIt = pruneSave.listIterator();
					while(pruneIt.hasNext()) {
						valInd = pruneIt.next();
						workingDom.set(valInd, true);
						workingSizes.set(valInd / NB_QUEENS, workingSizes.get(valInd / NB_QUEENS) + 1);
						pruneIt.remove();
					}
				}
				workingNode.removeLast();
			}
		}
			
		return sols;
	}
	
	public static String printQueens(LinkedList<Integer> queens) {
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
		LinkedList<Integer> node = new LinkedList<Integer>();
		ArrayList<Boolean> domain = new ArrayList<Boolean>(DOM_SIZE);
		ArrayList<Integer> domSizes = new ArrayList<Integer>(NB_QUEENS);
		// le domaine est une matrice de booleens représentant l'échiquier avec les cases encore possibles (true) ou pas;
		// elle est écrasée dans une arraylist pour avoir un seul index.
		
		for(int i = 0; i < DOM_SIZE; ++i) {
			domain.add(true);
		}
		for(int i = 0; i < NB_QUEENS; ++i) {
			domSizes.add(NB_QUEENS);
		}
		
		double chrono=System.currentTimeMillis();

		System.out.println("ForwardChecking avec "+NB_QUEENS+" reines");
		sols = branchAndPrune(node, domain, domSizes);
		System.out.println(sols+" solutions trouvées.");
		chrono=System.currentTimeMillis()-chrono;
		chrono=chrono/1000;
		System.out.println("En "+chrono+" secondes");
	}
}
