package fr.uvsq.jnotes.command;

import fr.uvsq.jnotes.function.*;

/**
 * Classe permettant de transformer la demande de l'utilisateur en commande. 
 * Nécessaire pour le pattern Command. 
 */
public class ArgumentCommand {
	
	/**
	 * function représente les étapes à effectuer pour la commande en question. 
	 */
	private Function function;
	
	/**
	 * command représente la commande choisie par l'utilisateur. 
	 */
	private Command command; 
	
	private CommandArg commandArg;
	
	/**
	 * swit permet d'effectuer la commande. 
	 */
	private Switch swit;
	
	/**
	 * Constructeur de ArgumentCommand. 
	 */
	public ArgumentCommand() {
		function = new Function();
		swit = new Switch();
	}
	
	/**
	 * Permet d'interpréter ce que veut l'utilisateur et renvoie la commande demandée. 
	 * @param args tapés en ligne de commande. 
	 * @return renvoie la commande à effectuer.
	 */
	public EnumCommand detectCommand(String[] args) {
		if (args.length==0) {
			return EnumCommand.NO_COMMAND;
		}
		else {
			String sargs=args[0];
			if ((sargs.equals("edit"))||(sargs.equals("e"))) {
				return EnumCommand.EDIT;
			}
			else if ((sargs.equals("list"))||(sargs.equals("ls"))) {
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
	
	/**
	 * Permet d'interpréter ce que veut l'utilisateur et effectue la commande demandée.
	 * @param args tapés en ligne de commande. 
	 */
	public void setArgument(String[] args) {
		EnumCommand detectArg=detectCommand(args);
		
		if (detectArg==EnumCommand.NO_COMMAND) {
			ScannerCommand saisie = new ScannerCommand();
			saisie.afficheCommandes();
			saisie.saisie();
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
				commandArg = new Delete(function);
				 swit.storeAndExecute(commandArg, args);
			}
			else if (detectArg==EnumCommand.VIEW) {
				commandArg = new View(function);
				 swit.storeAndExecute(commandArg, args);
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