package fr.uvsq.jnotes.index;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.queryParser.ParseException;


public class TestIndex {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean search = true;
		File f = new File("index");
		System.out.println("exists ?"+f.exists());
		if (search) {
			String[] arguments2 = {"index", ":project: fraise mangue"};// vas trouver ceux qui contienne fraise ou mangue mais pas frocement les 2
			
			String[] arguments = {"index", "fraise mangue"};// vas trouver ceux qui contienne fraise ou mangue mais pas frocement les 2
			try {
				Searcher.main(arguments);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		else {
			String[] arguments = {"index", "notes"};
			try {
				System.out.println("arguments : "+arguments.length);
				Indexer.main(arguments);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
