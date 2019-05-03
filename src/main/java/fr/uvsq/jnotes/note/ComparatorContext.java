package fr.uvsq.jnotes.note;

import java.util.Comparator;

/**
 * ComparatorContext permet de trier les notes en fonction de leurs contextes. 
 */
public class ComparatorContext implements Comparator<Note>{

	/**
	 * Fonction de comparaison de note. 
	 */
	@Override
	public int compare(Note o1, Note o2) {
		return o1.getContext().compareTo(o2.getContext());
	}

}
