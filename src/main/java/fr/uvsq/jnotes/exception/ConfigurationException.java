package fr.uvsq.jnotes.exception;

/**
 * Classe ConfigurationException qui est une exception lorsque la lecture du fichier de configuration rencontre un probleme.
 */
public class ConfigurationException extends RuntimeException{
	
	/**
     * Constructeur de ConfigurationException.
     */
    public ConfigurationException(){
        super("Impossible de lire le fichier de configuration. ");
    }

    /**
     * Constructeur de ConfigurationException.
     * @param message a afficher. 
     */
	public ConfigurationException(String message) {
		super("Impossible de lire le fichier de configuration \n"+message);
	}
}
