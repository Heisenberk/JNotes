package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.CommandArg;

public class Edit implements CommandArg {
	private Function function;
	
	public Edit(Function function) {
		this.function = function;
	}
	
	public void execute(String[] args) {
		try {
			function.edit(args);
		}
		catch(Exception e) {
			System.out.println("ERREUR"); //A MODIFIER AVEC UNE EXCEPTION PERSO
		}
	}
}
