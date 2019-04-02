package fr.uvsq.jnotes.exception;
/**
 * Classe CommandeInconnuException qui est une exception lorsque la commande est inconnu pour l'application.
 */
public class CommandeInconnuException extends RuntimeException{
	
	/**
     * Constructeur de CommandeInconnuException.
     */
    public CommandeInconnuException(){
        super("Commande de l'utilisateur inconnu. ");
    }
    
    /**
     * Accesseur du message a afficher.
     */
    public String getMessage() {
    	return "Commande de l'utilisateur inconnu. ";
    }
}
