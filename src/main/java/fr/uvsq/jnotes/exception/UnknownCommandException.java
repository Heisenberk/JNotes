package fr.uvsq.jnotes.exception;

/**
 * Classe UnknownCommandException qui est une exception lorsque la commande est inconnu pour l'application.
 */
public class UnknownCommandException extends RuntimeException{
	
	/**
     * Constructeur de UnknownCommandException.
     */
    public UnknownCommandException(){
        super("Commande inconnue");
    }
    
    /**
     * Constructeur de UnknownCommandException.
     * @param message a afficher. 
     */
    public UnknownCommandException(String message){
        super("Commande inconnue : " + message);
    }
}
