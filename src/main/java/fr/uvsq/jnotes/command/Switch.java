package fr.uvsq.jnotes.command;

import java.util.List;
import java.util.ArrayList;

/**
 * Classe permettant d'effectuer les différentes commandes de JNotes. 
 * Nécessaire pour le pattern Command. 
 */
public class Switch {

	/**
	 * Liste des commandes possibles. 
	 */
   private List<Command> history = new ArrayList<Command>();

   /**
    * Constructeur de Switch
    */
   public Switch() {}

   /**
    * Exécute la commande demandé. 
    * @param cmd à réaliser. 
    */
   public void storeAndExecute(Command cmd) {
      this.history.add(cmd); // optional 
      cmd.execute();        
   }
}