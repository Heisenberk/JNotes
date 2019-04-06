package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.ICommand;
import fr.uvsq.jnotes.exception.DeleteException;

/**
 * Classe qui permet de supprimer une note. 
 */
public class Delete implements ICommand {
	
	/**
	 * arguments entrés par l'utilisateur
	 */
	private String[]args;
	
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
	public void execute() {
		try {
			function.delete(this.args);
		}
		catch(DeleteException e) {
			System.out.println(e.getMessage());
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