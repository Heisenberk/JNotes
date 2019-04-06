package fr.uvsq.jnotes.note;

import java.util.Comparator;

public class ComparatorDate implements Comparator<Note>{

	@Override
	public int compare(Note o1, Note o2) {
		return o1.getDate().compareTo(o2.getDate());
	}

}