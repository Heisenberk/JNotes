package fr.uvsq.jnotes.function;

import fr.uvsq.jnotes.command.Command;

public class View implements Command {
	private Function function;
	
	public View(Function function) {
		this.function = function;
	}
	
	public void execute() {
		function.view();
	}
}
