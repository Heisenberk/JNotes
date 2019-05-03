package fr.uvsq.jnotes.command;

import fr.uvsq.jnotes.exception.*;
import fr.uvsq.jnotes.function.*;
import fr.uvsq.jnotes.utils.Helper;

/**
 * Enumeration regroupant toutes les commandes possibles de l'application. 
 */
enum EnumCommand {
	NO_COMMAND ,EDIT, LIST, DELETE, VIEW, SEARCH, PARAM, INDEX, COMMAND_INVALIDE, HELP;
}

/**
 * Classe permettant de transformer la demande de l'utilisateur en commande. 
 * Necessaire pour le pattern Command. 
 */
public class Interpretor {
	
	/**
	 * function represente les etapes a effectuer pour la commande en question. 
	 */
	private Function function;
	
	/**
	 * command represente la commande choisie par l'utilisateur. 
	 */
	private ICommand command; 
	
	/**
	 * update permet de mettre a jour a chaque modification sur les notes. 
	 */
	private Update update;
	
	/**
	 * Constructeur de Interpretor. 
	 */
	public Interpretor() {
		function = new Function();
		update = new Update();
		function.addObserver(update);
	}
		
	/**
	 * detectCommand va renvoyer la commande choisie par l'utilisateur. 
	 * @param args arguments tapes par l'utilisateur. 
	 * @return EnumCommand representant la commande. 
	 */
	public EnumCommand detectCommand (String[] args) {
		//***************************************************
		// S'il n'y a pas d'arguments on utilise le scanner. 
		//***************************************************
		if (args.length == 0) {
			ScannerCommand saisie = new ScannerCommand();
			//saisie.afficheCommandes();
			try {
				saisie.saisie();
			}
			catch(SaisieException e) {
				System.out.println(e);
			}
			return EnumCommand.NO_COMMAND;
		}
		//*************************************************
		// Sinon on interprète la commande directement. 
		//***************************************************
		else {
			Helper.toLowerCase(args);
			String sargs=args[0];
			if (sargs.equals("test")){
				return EnumCommand.NO_COMMAND;
			}
			if ((sargs.equals("edit"))||(sargs.equals("e"))) {
				System.out.println("edit");
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
			} else if ((sargs.equals("help"))||(sargs.equals("h"))) {
				command=new Help(function);
				storeAndExecute(command);
				return EnumCommand.HELP;
			} 
			System.out.println("Commande non reconnue");
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
