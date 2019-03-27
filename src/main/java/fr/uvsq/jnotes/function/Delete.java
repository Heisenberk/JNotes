package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.Command;

public class Delete implements Command {
	private Function function;
	
	public Delete(Function function) {
		this.function = function;
	}
	
	public void execute() {
		function.delete();
	}
}