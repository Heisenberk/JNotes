package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.Command;

/**
 * Classe qui permet de supprimer une note. 
 */
public class Listing implements Command {
	
	/**
	 * function representant ce qui va appeler la commande. 
	 */
	private Function function;
	
	/**
	 * Constructeur de Listing.
	 * @param function
	 */
	public Listing(Function function) {
		this.function = function;
	}
	
	/**
	 * Execute la commande. 
	 */
	public void execute() {
		function.listing();
	}
}