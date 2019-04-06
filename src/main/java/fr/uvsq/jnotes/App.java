package fr.uvsq.jnotes;

import fr.uvsq.jnotes.command.*;
import fr.uvsq.jnotes.function.Function;
import fr.uvsq.jnotes.function.Index;
import fr.uvsq.jnotes.note.Note;

/**
 * Enumération représentant le main de l'application. 
 * Utilisation du pattern Singleton pour construire une unique 
 * entité pour le main. 
 */
enum App {
	/**
	 * Variable d'environnement. 
	 */
	ENVIRONNEMENT;

	/**
	 * Methode run qui appelle les fonctions nécessaires à l'application. 
	 * @param args arguments en ligne de commande. 
	 */
	public void run(String[] args){
        ArgumentCommand arg = new ArgumentCommand();
        arg.setArgument(args);
		/*Index i = new Index();
		Note n =i.readNote("target/note_indexTest.adoc");
		System.out.println("Title:<"+n.getTitle()+">");
		System.out.println("author:<"+n.getAuthor()+">");
		System.out.println("date:<"+n.getDate()+">");
		System.out.println("context:<"+n.getContext()+">");
		System.out.println("project:<"+n.getProject()+">");*/
	}

	/**
	 * Methode main qui appelle l'unique variable d'environnement. 
	 * @param args arguments en ligne de commande. 
	 */
	public static void main(String[] args){
		ENVIRONNEMENT.run(args);
	}
}
