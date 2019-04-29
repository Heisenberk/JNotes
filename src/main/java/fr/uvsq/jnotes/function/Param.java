package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.ICommand;
import fr.uvsq.jnotes.exception.*;

/**
 * Classe qui permet de rechercher un element dans toutes les notes. 
 */
public class Param implements ICommand{
	
	/**
	 * arguments entres par l'utilisateur
	 */
	private String[] args;
	
	/**
	 * function representant ce qui va appeler la commande. 
	 */
	private Function function;
	
	/**
	 * Constructeur de Search.
	 * @param function
	 */
	public Param(Function function) {
		this.function = function;
	}
	
	/**
	 * Execute la commande. 
	 */
	public void execute() {
		try {
			function.param(this.args);
		}
		catch(ParamException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Donne a la commande les arguments auxquelles elle devra répondre.
	 */
	public ICommand setArgument(String[] args) {
		this.args = args;
		return this;
	}
}