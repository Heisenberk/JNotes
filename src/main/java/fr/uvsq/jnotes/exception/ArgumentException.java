package fr.uvsq.jnotes.exception;

/**
 * Classe ArgumentException qui est une exception lorsque la commande est inconnu pour l'application.
 */
public class ArgumentException extends RuntimeException{
	
	/**
     * Constructeur de SearchException.
     */
    public ArgumentException(){
        super("Manque des arguments a cette commande. ");
    }
    
    /**
     * Accesseur du message a afficher.
     */
    public String getMessage() {
    	return "Manque des arguments a cette commande. ";
    }
}