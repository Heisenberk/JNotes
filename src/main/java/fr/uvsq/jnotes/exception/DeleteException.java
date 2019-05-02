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

	/**Constructeur de DeleteException avec message.
	 * @param message message à afficher
	 */
	public DeleteException(String message) {
		super("Impossible de supprimer une note : " + message);
	}
}