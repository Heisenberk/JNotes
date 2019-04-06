package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.ICommand;
import fr.uvsq.jnotes.exception.*;

/**
 * Classe qui permet de rechercher un element dans toutes les notes. 
 */
public class Search implements ICommand{
	/**
	 * arguments entrés par l'utilisateur
	 */
	private String[]args;
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
	public void execute() {
		try {
			function.search(this.args);
		}
		catch(SearchException e) {
			System.out.println(/*e*/e.getMessage());
		}
	}
	

	public ICommand setArgument(String[] args) {
		this.args = args;
		return this;
	}
}
