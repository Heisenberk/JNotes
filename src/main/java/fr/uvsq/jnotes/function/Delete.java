package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.CommandArg;
import fr.uvsq.jnotes.exception.DeleteException;

/**
 * Classe qui permet de supprimer une note. 
 */
public class Delete implements CommandArg {
	
	/**
	 * function representant ce qui va appeler la commande. 
	 */
	private Function function;
	
	/**
	 * Constructeur de Delete.
	 * @param function
	 */
	public Delete(Function function) {
		this.function = function;
	}
	
	/**
	 * Execute la commande. 
	 */
	public void execute(String[] args) {
		try {
			function.delete(args);
		}
		catch(DeleteException e) {
			System.out.println(e.getMessage());
		}
	}
}