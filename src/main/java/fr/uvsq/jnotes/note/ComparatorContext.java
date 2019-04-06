package fr.uvsq.jnotes.note;

import java.util.Comparator;

public class ComparatorContext implements Comparator<Note>{

	@Override
	public int compare(Note o1, Note o2) {
		return o1.getContext().compareTo(o2.getContext());
	}

}
