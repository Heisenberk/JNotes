package fr.uvsq.jnotes.command;

public class Delete implements Command {
	private Function function;
	
	public Delete(Function function) {
		this.function = function;
	}
	
	public void execute() {
		function.delete();
	}
}