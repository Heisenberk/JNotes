package fr.uvsq.jnotes.command;

public class ArgumentCommand {
	private Function function;
	private Command command; 
	
	private Switch swit;
	
	public ArgumentCommand() {
		function = new Function();
		swit = new Switch();
	}
	
	public EnumCommand detectCommand(String[] args) {
		if (args.length==0) {
			return EnumCommand.NO_COMMAND;
		}
		else {
			String sargs=args[0];
			if ((sargs.equals("edit"))||(sargs.equals("e"))) {
				return EnumCommand.EDIT;
			}
			else if ((sargs.equals("list"))||(sargs.equals("l"))) {
				return EnumCommand.LIST;
			}
			else if ((sargs.equals("delete"))||(sargs.equals("d"))) {
				return EnumCommand.DELETE;
			}
			else if ((sargs.equals("view"))||(sargs.equals("v"))) {
				return EnumCommand.VIEW;
			}
			else if ((sargs.equals("search"))||(sargs.equals("s"))) {
				return EnumCommand.SEARCH;
			}
			return EnumCommand.COMMAND_INVALIDE;
		}
	}
	
	public void setArgument(String[] args) {
		EnumCommand detectArg=detectCommand(args);
		
		if (detectArg==EnumCommand.NO_COMMAND) {
			//dedie le travail a l'interpreteur
		}
		else {
			if (detectArg==EnumCommand.EDIT) {
				command = new Edit(function);
				 swit.storeAndExecute(command);
			}
			else if (detectArg==EnumCommand.LIST) {
				command = new Listing(function);
				 swit.storeAndExecute(command);
			}
			else if (detectArg==EnumCommand.DELETE) {
				command = new Delete(function);
				 swit.storeAndExecute(command);
			}
			else if (detectArg==EnumCommand.VIEW) {
				command = new View(function);
				 swit.storeAndExecute(command);
			}
			else if (detectArg==EnumCommand.SEARCH) {
				command = new Search(function);
				 swit.storeAndExecute(command);
			}
			else {
				System.out.println("message non reconnu");
			}
		}
	}
}
