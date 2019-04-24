package fr.uvsq.jnotes.exception;

/**
 * Classe EditException.
 */
public class IndexException extends RuntimeException{
	
	/**
     * Constructeur de EditException.
     */
    public IndexException(){
        super("IndexException : Impossible de visualiser l'index JNotes. ");
    }
}