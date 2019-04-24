package fr.uvsq.jnotes.command;

import fr.uvsq.jnotes.exception.*;
import fr.uvsq.jnotes.function.*;
/**
 * Enumeration regroupant toutes les commandes possibles de l'application. 
 */
enum EnumCommand {
	NO_COMMAND ,EDIT, LIST, DELETE, VIEW, SEARCH, PARAM, INDEX, COMMAND_INVALIDE;
}
/**
 * Classe permettant de transformer la demande de l'utilisateur en commande. 
 * Nécessaire pour le pattern Command. 
 */
public class Interpretor {
	
	/**
	 * function représente les étapes à effectuer pour la commande en question. 
	 */
	private Function function;
	
	/**
	 * command représente la commande choisie par l'utilisateur. 
	 */
	private ICommand command; 
	
	private Update update;
	
	/**
	 * Constructeur de ArgumentCommand. 
	 */
	public Interpretor() {
		function = new Function();
		update = new Update();
		function.addObserver(update);
	}
		
	public EnumCommand detectCommand (String[] args) {
		if (args.length==0) {
			ScannerCommand saisie = new ScannerCommand();
			saisie.afficheCommandes();
			try {
				saisie.saisie();
			}
			catch(SaisieException e) {
				System.out.println("prout prout" + e);
			}
			return EnumCommand.NO_COMMAND;
		}
		else {
			String sargs=args[0];
			if (sargs.equals("test")){
				return EnumCommand.NO_COMMAND;
			}
			if ((sargs.equals("edit"))||(sargs.equals("e"))) {
				command = new Edit(function).setArgument(args);
				storeAndExecute(command);
				return EnumCommand.EDIT;
			}
			else if ((sargs.equals("list"))||(sargs.equals("ls"))) {
				command = new Listing(function);
				storeAndExecute(command);
				return EnumCommand.LIST;
			}
			else if ((sargs.equals("delete"))||(sargs.equals("d"))) {
				command = new Delete(function).setArgument(args);
				storeAndExecute(command);
				return EnumCommand.DELETE;
			}
			else if ((sargs.equals("view"))||(sargs.equals("v"))) {
				command = new View(function).setArgument(args);
				storeAndExecute(command);
				return EnumCommand.VIEW;
			}
			else if ((sargs.equals("search"))||(sargs.equals("s"))) {
				command = new Search(function).setArgument(args);
				storeAndExecute(command);
				return EnumCommand.SEARCH;
			}
			else if ((sargs.equals("param"))||(sargs.equals("p"))) {
				command=new Param(function).setArgument(args);
				storeAndExecute(command);
				return EnumCommand.PARAM;
			}
			else if ((sargs.equals("index"))||(sargs.equals("i"))) {
				command=new Index(function);
				storeAndExecute(command);
				return EnumCommand.INDEX;
			} 
			return EnumCommand.COMMAND_INVALIDE;
		}
	}
	   /**
	    * Exécute la commande sans argument demandé. 
	    * @param cmd à réaliser. 
	    */
	   public void storeAndExecute(ICommand cmd) {
	      cmd.execute();        
	   }
}
