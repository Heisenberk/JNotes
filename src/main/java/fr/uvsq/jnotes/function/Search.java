package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.CommandArg;
import fr.uvsq.jnotes.exception.*;

/**
 * Classe qui permet de rechercher un element dans toutes les notes. 
 */
public class Search implements CommandArg{
	
	/**
	 * function representant ce qui va appeler la commande. 
	 */
	private Function function;
	
	/**
	 * Constructeur de Search.
	 * @param function
	 */
	public Search(Function function) {
		this.function = function;
	}
	
	/**
	 * Execute la commande. 
	 */
	public void execute(String[] args) {
		try {
			function.search(args);
		}
		catch(SearchException e) {
			System.out.println(e.getMessage());
		}
	}
}
