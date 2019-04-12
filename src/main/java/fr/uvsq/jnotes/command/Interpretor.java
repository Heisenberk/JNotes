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
			else if ((sargs.equals("param"))||(sargs.equals("p"))) {
				return EnumCommand.PARAM;
			}
			else if ((sargs.equals("index"))||(sargs.equals("i"))) {
				return EnumCommand.INDEX;
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
		
		// si l'utilisateur ne tape aucun argument. 
		if (detectArg==EnumCommand.NO_COMMAND) {
			ScannerCommand saisie = new ScannerCommand();
			saisie.afficheCommandes();
			try {
				saisie.saisie();
			}
			catch(SaisieException e) {
				System.out.println(e.getMessage());
			}
			
		}
		// si l'utilisateur tape un argument. 
		else {
			try {
				if (detectArg==EnumCommand.EDIT) {
					command = new Edit(function).setArgument(args);
					 storeAndExecute(command);
				}
				else if (detectArg==EnumCommand.LIST) {
					command = new Listing(function);
					 storeAndExecute(command);
				}
				else if (detectArg==EnumCommand.DELETE) {
					command = new Delete(function).setArgument(args);
					 storeAndExecute(command);
				}
				else if (detectArg==EnumCommand.VIEW) {
					command = new View(function).setArgument(args);
					 storeAndExecute(command);
				}
				else if (detectArg==EnumCommand.SEARCH) {
					command = new Search(function).setArgument(args);
					 storeAndExecute(command);
				}
				else if (detectArg ==EnumCommand.PARAM) {
					command=new Param(function).setArgument(args);
					storeAndExecute(command);
				}
				else if (detectArg ==EnumCommand.INDEX) {
					command=new Index(function);
					storeAndExecute(command);
				}
				else throw new CommandeInconnuException();
			}
			catch(CommandeInconnuException e) {
				System.out.println(e.getMessage());
			}
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
