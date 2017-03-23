package backTracking;

import java.util.LinkedList;
import java.util.ListIterator;

public class QueensV3 {
	private static final int NB_QUEENS = 10;
	
	public static int branchAndPrune(LinkedList<Integer> node) {
		int sols = 0;
		int value = node.peekLast(); 
		int size = node.size();
		LinkedList<Integer> newNode;

		while (value < NB_QUEENS) {

			if(verify(node)) {
				if ( size == NB_QUEENS ) {

					++sols;
				} else {				
					newNode = new LinkedList<Integer>();
					for(int val: node) {
						newNode.add(val);
					}
					newNode.add(0);

					sols += branchAndPrune(newNode);
				}
			}
			++value;
			node.removeLast();
			node.addLast(value);
		}
			
		return sols;
	}
	
	//test des contraintes.
	// n'effectue le test qu'avec le dernier domaine, les autres on déja été validés
	public static boolean verify(LinkedList<Integer> node) {
		ListIterator<Integer> valIt = node.listIterator();
		int newLine = node.size()-1;
		int newVal = node.peekLast();
		int i = 0;
		int val, line;
		if (newLine > 0) {
			while(i < newLine) {
				++i;
				line = valIt.nextIndex();
				val = valIt.next();
				if (val == newVal) {
					return false;
				}
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
		node.add(0);
		double chrono=System.currentTimeMillis();

		System.out.println("BackTracking sur "+NB_QUEENS+" reines");
		sols = branchAndPrune(node);
		System.out.println(sols+" solutions trouvées.");
		chrono=System.currentTimeMillis()-chrono;
		chrono=chrono/1000;
		System.out.println("En "+chrono+" secondes");
	}
}
