package fr.uvsq.jnotes.command;

/**
 * Classe permettant d'effectuer les différentes commandes de JNotes. 
 * Nécessaire pour le pattern Command. 
 */
public class Switch {

   /**
    * Constructeur de Switch
    */
   public Switch() {}

   /**
    * Exécute la commande sans argument demandé. 
    * @param cmd à réaliser. 
    */
   public void storeAndExecute(Command cmd) {
      cmd.execute();        
   }
   
   /**
    * Exécute la commande avec argument demandé. 
    * @param cmd commande à réaliser. 
    * @param args arguments de la commande. 
    */
   public void storeAndExecute(CommandArg cmd, String[] args) {
	   cmd.execute(args);
   }
}