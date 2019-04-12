package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.ICommand;
import fr.uvsq.jnotes.exception.EditException;

/**
 * Classe qui permet d'éditer une note. 
 */
public class Edit implements ICommand {
	
	/**
	 * arguments entrés par l'utilisateur
	 */
	private String[]args;
	
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
	public void execute() {
		try {
			function.edit(this.args);
		}
		catch(Exception e) {
			EditException e1 = new EditException();
			System.out.println(e1.getMessage());
		}
	}

	/**
	 * Donne à la commande les arguments auxquelles elle devra répondre;
	 */
	public ICommand setArgument(String[] args) {
		this.args = args;
		return this;
	}
}
