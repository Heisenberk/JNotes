package fr.uvsq.jnotes.note;

import java.util.Comparator;

/**
 * ComparatorProject permet de trier les notes en fonction de leurs projects. 
 */
public class ComparatorProject implements Comparator<Note>{

	/**
	 * Fonction de comparaison de note. 
	 */
	@Override
	public int compare(Note o1, Note o2) {
		return o1.getProject().compareTo(o2.getProject());
	}

}