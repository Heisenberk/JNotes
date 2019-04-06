package fr.uvsq.jnotes.note;

import java.util.Comparator;

public class ComparatorProject implements Comparator<Note>{

	@Override
	public int compare(Note o1, Note o2) {
		return o1.getProject().compareTo(o2.getProject());
	}

}