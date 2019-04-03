package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.CommandArg;
import fr.uvsq.jnotes.exception.EditException;

/**
 * Classe qui permet d'éditer une note. 
 */
public class Edit implements CommandArg {
	
	/**
	 * function representant ce qui va appeler la commande. 
	 */
	private Function function;
	
	/**
	 * Constructeur de Edit.
	 * @param function
	 */
	public Edit(Function function) {
		this.function = function;
	}
	
	/**
	 * Execution de la commande. 
	 * @param args arguments de edit. 
	 */
	public void execute(String[] args) {
		try {
			function.edit(args);
		}
		catch(Exception e) {
			EditException e1 = new EditException();
			System.out.println(e1.getMessage());
		}
	}
}
