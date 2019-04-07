package fr.uvsq.jnotes.exception;

/**
 * Classe DeleteException.
 */
public class DeleteException extends RuntimeException{
	
	/**
     * Constructeur de DeleteException.
     */
    public DeleteException(){
        super("Impossible de supprimer une note. ");
    }
    
    public DeleteException(String string) {
		super(string);
	}

}