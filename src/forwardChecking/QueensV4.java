package forwardChecking;

import java.util.LinkedList;
import java.util.ListIterator;

public class QueensV4 {
	private static final int NB_QUEENS = 15;
	
	public static int branchAndPrune(LinkedList<Integer> node, LinkedList<Integer> domain) {
		int sols = 0;
		int value; // valeur a pruner si valide
		
		LinkedList<Integer> newNode; 
		LinkedList<Integer> newDom;
		ListIterator<Integer> valIt = domain.listIterator();
		
		while (valIt.hasNext()) {
			value = valIt.next();
			// affectation de la premiere valeur du domaine
			node.add(value);
			if(verify(node)) {
				//la combinaison est valide
				if ( node.size() == NB_QUEENS ) {
					// la combinaison est une solution
					++sols;
				} else {
					// nouveau noeaud sur lequel on va travailler 
					newNode = new LinkedList<Integer>();					
					for(int val: node) {
						newNode.add(val);
					}
					// domaine de la variable sur laquelle on va travailler
					newDom = new LinkedList<Integer>();					
					for(int val: domain) {
						// forward checking, on n'ajoute pas la valeur nouvellement attribuée.
						if(val != value) newDom.add(val);
					}
					// on descend d'un cran dans l'arbre de recherche
					sols += branchAndPrune(newNode, newDom);
				}
			}
			// nettoyage de la valeur invalidante
			node.removeLast();
		}
			
		return sols;
	}
	
	//test des contraintes.
	// n'effectue le test qu'avec le dernier domaine, les autres on déja été validés
	public static boolean verify(LinkedList<Integer> node) {
		ListIterator<Integer> valIt = node.listIterator();
		int newLine = node.size()-1; // -1 car je commence avec la colonne/ligne 0
		int newVal = node.peekLast();
		int i = 0;
		int val, line;
		if (newLine > 0) {
			// s'il n'y a qu'une variable attribuée, on valide
			while(i < newLine) {
				++i;
				line = valIt.nextIndex();
				val = valIt.next();
				if (Math.abs(val - newVal) == newLine - line) {
					// |Xi - Xj| == |j - i| -> les dames sont sur la même diagonale.
					return false;
				}
				
			}
		}
		return true;
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

	public static void main(String[] args) {
		int sols = 0;
		LinkedList<Integer> node = new LinkedList<Integer>();
		LinkedList<Integer> domain = new LinkedList<Integer>();
		for(int i = 0; i < NB_QUEENS; ++i) {
			domain.add(i);
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
