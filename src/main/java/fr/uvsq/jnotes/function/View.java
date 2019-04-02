package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.CommandArg;

public class View implements CommandArg {
	private Function function;
	
	public View(Function function) {
		this.function = function;
	}
	
	public void execute(String[] args) {
		try {
			function.view(args);
		}
		catch(Exception e) {
			System.out.println("ERREUR"); //A MODIFIER AVEC UNE EXCEPTION PERSO
		}
	}
}
