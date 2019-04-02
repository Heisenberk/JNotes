package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.CommandArg;

public class Delete implements CommandArg {
	private Function function;
	
	public Delete(Function function) {
		this.function = function;
	}
	
	public void execute(String[] args) {
		try {
			function.delete(args);
		}
		catch(Exception e) {
			System.out.println("ERREUR"); //A MODIFIER AVEC UNE EXCEPTION PERSO
		}
	}
}