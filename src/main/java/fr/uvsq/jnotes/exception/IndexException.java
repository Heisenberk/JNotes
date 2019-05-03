package fr.uvsq.jnotes.exception;

/**
 * Classe IndexException.
 */
public class IndexException extends RuntimeException{
	
	/**
     * Constructeur de IndexException.
     */
    public IndexException(){
        super("Impossible d'indexer");
    }

	/**
     * Constructeur de IndexException avec message.
     * @param message le message a afficher
     */
    public IndexException(String message){
        super("Impossible d'indexer : " + message);
    }
        
}