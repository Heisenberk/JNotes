package fr.uvsq.jnotes.exception;

/**
 * Classe IndexException appelee lors d'un probleme de visualisation de l'index.
 */
public class IndexException extends RuntimeException{
	
	/**
     * Constructeur de EditException.
     */
    public IndexException(){
        super("IndexException : Impossible de visualiser l'index JNotes. ");
    }
}