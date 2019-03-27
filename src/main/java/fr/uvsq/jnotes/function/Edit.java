package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.Command;

public class Edit implements Command {
	private Function function;
	
	public Edit(Function function) {
		this.function = function;
	}
	
	public void execute() {
		function.edit();
	}
}
