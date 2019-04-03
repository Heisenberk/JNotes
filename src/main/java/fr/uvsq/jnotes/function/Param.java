package fr.uvsq.jnotes.function;

import exception.ParamException;
import fr.uvsq.jnotes.command.CommandArg;

/**
 * Classe qui permet de rechercher un element dans toutes les notes. 
 */
public class Param implements CommandArg{
	
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
	public void execute(String[] args) {
		try {
			function.param(args);
		}
		catch(ParamException e) {
			System.out.println(e.getMessage());
		}
	}
}