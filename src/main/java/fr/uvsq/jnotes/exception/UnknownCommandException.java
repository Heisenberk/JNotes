package fr.uvsq.jnotes.exception;

/**
 * Classe CommandeInconnuException qui est une exception lorsque la commande est inconnu pour l'application.
 */
public class UnknownCommandException extends RuntimeException{
	
	/**
     * Constructeur de CommandeInconnuException.
     */
    public UnknownCommandException(){
        super("Commande inconnue");
    }
    
    public UnknownCommandException(String message){
        super("Commande inconnue : " + message);
    }
}
