package fr.uvsq.jnotes.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import fr.uvsq.jnotes.exception.SaisieException;

/**
 * Classe ScannerCommand qui va lire les commandes de l'utilisateur qui n'a pas mis d'arguments au depart. 
 */
public class ScannerCommand {
	
	/**
	 * Constructeur de ScannerCommand qui initialise le scanner. 
	 */
	public ScannerCommand() {
	}
	

	
	/**
	 * Methode saisie qui va interpreter les commandes de l'utilisateur qui n'a pas mis d'arguments au depart. 
	 */
	public void saisie() throws SaisieException {
		boolean arret = false;

    	Interpretor interpretor = new Interpretor();
		String commande;
		System.out.println("Taper help ou h pour voir l'aide.");
		try(BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
			
			// *************************************
			// Continue tant que l'utilisateur n'a pas ecrit la commande 'quit'
			// *************************************
			while (!arret) {
				
				System.out.print(">");
				
				commande = in.readLine();
				if (commande.length() == 0)
					continue;

				String function = commande.split(" +")[0];
				String[] arguments;
				
				if (!commande.contains("\"")) 
					arguments = commande.split(" ");
					
				else 
					arguments = commande.split(
				    			"(^" + function + " *$)|(^" + function + 
				    			" +\")|(\" +\")|(\" *$)");//    
			   
			    String[] args = new String[arguments.length];

			    args[0] = function;
			    if(arguments.length > 1) {
				    for(int i = 1 ; i < arguments.length ; i++)
				    	args[i] = arguments[i];
			    }
			    if (commande.equals("quit")) 
			    	arret = true;
			    else 
			        interpretor.detectCommand(args);
				
			}
		} catch(IOException e) {
			throw new SaisieException();
		}
	}
	
	private static boolean contains(String s, char c) {
		for (int i = 0 ; i < s.length() ; i++)
			if (s.charAt(i) == c)
				return true;
		return false;
	}
}
