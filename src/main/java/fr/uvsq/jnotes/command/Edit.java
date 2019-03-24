package fr.uvsq.jnotes.command;

public class Edit implements Command {
	private Function function;
	
	public Edit(Function function) {
		this.function = function;
	}
	
	public void execute() {
		function.edit();
	}
}
