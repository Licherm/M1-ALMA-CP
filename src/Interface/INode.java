package Interface;

import java.util.ArrayList;

import backTracking.Domain;

public interface INode {

	void replace(IDomain old, IDomain newD);

	void add(Domain d);

	void removeLast();

	Domain get(int index);

	ArrayList<Domain> getDomains();

	void setDomains(ArrayList<Domain> domains);

	/**
	 * @brief to string pour le problème des dames
	 * @return String : la string pour l'affichage
	 */
	String toStringQueen();

}