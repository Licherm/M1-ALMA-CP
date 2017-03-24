package forwardChecking;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ListIterator;

public class QueensV5 {
	private static final int NB_QUEENS = 15;
	private static final int DOM_SIZE = NB_QUEENS * NB_QUEENS;
	
	public static int branchAndPrune(LinkedList<Integer> node, ArrayList<Boolean> domain) {
		int sols = 0;
		int value; // valeur a pruner si valide
		int domStart = node.size() * NB_QUEENS;
		int domEnd = (node.size() + 1) * NB_QUEENS - 1;
		int domInd, valInd, diagInd; // valeurs temporaires pour les calculs du pruning
		
		LinkedList<Integer> workingNode = node;
		ArrayList<Boolean> workingDom = domain;
		LinkedList<Integer> pruneSave = new LinkedList<Integer>();
		// sauvegarde des valeur que l'on prune pour restaurer le noeud lors du changement de valeur
		
		ListIterator<Boolean> valIt = workingDom.listIterator(domStart);
		
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
					// surement améliorable pour qu'il s'arrête si un domaine se retrouve vide
					// mais ya pas de bonne solution qui m'est venue a l'esprit
					while (domInd < NB_QUEENS) {
						
						// une seule reine par colonne
						valInd = domInd * NB_QUEENS + node.getLast();
						if(workingDom.get(valInd)) {
							workingDom.set(valInd, false);
							pruneSave.add(valInd);
						}
						
						// diagonale gauche
						diagInd = valInd - (domInd - (node.size()-1)); 
						if(diagInd >= domInd * NB_QUEENS) {
							if(workingDom.get(diagInd)) {
								workingDom.set(diagInd, false);
								pruneSave.add(diagInd);
							}
						}
						
						// diagonale droite
						diagInd = valInd + (domInd - (node.size()-1)); 
						if(diagInd < (domInd + 1) * NB_QUEENS) {
							if(workingDom.get(diagInd)) {
								workingDom.set(diagInd, false);
								pruneSave.add(diagInd);
							}
						}
						++domInd;
					}
					// descente dans l'arbre de recherche
					sols += branchAndPrune(workingNode, workingDom);
					
					// backtracking
					for(int val: pruneSave) {
						workingDom.set(val, true);
					}
					pruneSave.clear();
					
				}
				workingNode.removeLast();
			}
		}
			
		return sols;
	}
	
	public static String printQueens(LinkedList<Integer> queens) {
		ListIterator<Integer> valIt = queens.listIterator();
		String printer = "";
		while(valIt.hasNext()) {
			printer += "ligne ";
			printer += (valIt.nextIndex()+1);
			printer += " colonne ";
			printer += (valIt.next()+1);
			printer += '\n';
		}
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
		// le domaine est une matrice de booleens représentant l'échiquier avec les cases encore possibles (true) ou pas;
		// elle est écrasée dans une arraylist pour avoir un seul index.
		
		for(int i = 0; i < DOM_SIZE; ++i) {
			domain.add(true);
		}
		
		double chrono=System.currentTimeMillis();

		System.out.println("ForwardChecking avec "+NB_QUEENS+" reines");
		sols = branchAndPrune(node, domain);
		System.out.println(sols+" solutions trouvées.");
		chrono=System.currentTimeMillis()-chrono;
		chrono=chrono/1000;
		System.out.println("En "+chrono+" secondes");
	}
}
