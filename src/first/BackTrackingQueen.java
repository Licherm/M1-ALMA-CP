package first;

import java.util.ArrayList;

public class BackTrackingQueen {
	
	
	/**
	 * @brief test si une affectation donnée est possible
	 * @param node = node ou chacun des domains est réduit à une seule valeur
	 *  
	 * @return true si l'affectation est valide false sinon
	 */
	public boolean isValide(Node node){
		
		ArrayList<Domain> domains = node.getDomains();
		
		for (int i=0;i<domains.size();++i){
			Domain d=domains.get(i);
			int val1=d.getValeurs().first();
			for (int n=i+1;n<domains.size();++n){
				Domain d2=domains.get(n);
				int val2=d2.getValeurs().first();
				
				//Test Colonne pas dans le même colonne
				if(val1==val2){
					return false; // si dans la même colonne
				}
				
				//Test la diagonale
				if (Math.abs(val1-val2)==n-i){
					return false; // même diagonales
				}
			}			
		}
		return true;
	}
	
	

}
