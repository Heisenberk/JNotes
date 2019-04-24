package fr.uvsq.jnotes.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import fr.uvsq.jnotes.exception.ConfigurationException;
import fr.uvsq.jnotes.index.Indexer;

/**
 * Classe Config qui va lire le fichier de configuration. 
 */
public class Config {
	
	/**
	 * pathStockage représente le chemin du dossier contenant les notes. 
	 */
	private String pathStockage;
	
	/**
	 * pathIndex représente le chemin du dossier contenant les doc de l'index lucene. 
	 */
	private String pathIndex;
	
	/**
	 * nameAppExtern représente l'application à utiliser pour editer une note.
	 */
	private String nameAppExtern;
	
	/**
	 * Constructeur Config
	 * @throws IOException
	 */
	public Config() throws ConfigurationException {
		this.pathIndex = "index";
		init();
	}
	
	/**
	 * Constructeur de Config avec un chemin en argument.
	 * @param pathFile chemin du fichier de configuration.
	 * @throws ConfigurationException si il y a un probleme de lecture du fichier de configuration.
	 */
	public Config(String pathFile)  throws ConfigurationException  {
		this.pathIndex = "index";
		init(pathFile);
	}
	
	public Config(String pathStockage, String nameAppExtern) {
		this.pathStockage = pathStockage;
		this.nameAppExtern = nameAppExtern;
		this.pathIndex = "index";
	}
	
	public Config(String pathStockage, String nameAppExtern, String pathIndex) {
		this.pathStockage = pathStockage;
		this.nameAppExtern = nameAppExtern;
		this.pathStockage = pathIndex;
	}
	
	/**
	 * Méthode init qui va initialiser les attributs de Config. 
	 * @throws ConfigurationException si il y a un probleme de lecture du fichier de configuration.
	 */
	private void init() throws ConfigurationException {
		init("config/config-jnotes");
	}
	
	/**
	 * Méthode init qui va initialiser les attributs de Config. 
	 * @param pathFile chemin du fichier de configuration.
	 * @throws ConfigurationException si il y a un probleme de lecture du fichier de configuration.
	 */
	public void init(String pathFile) throws ConfigurationException {
		Path inPath=Paths.get(pathFile);
		try (BufferedReader in = Files.newBufferedReader(inPath);){
			pathStockage = in.readLine();
			// si l'utilisateur met par exemple /home/user/Bureau au lieu de /home/user/Bureau/ -> on rajoute un "/"
			if(pathStockage.charAt(pathStockage.length()-1)!='/') pathStockage+="/";
			nameAppExtern = in.readLine();
			Indexer.indexer(pathIndex, pathStockage);
			//ne pas indexer à chaque fois
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new ConfigurationException(e.getMessage());
		}
	}
	
	/**
	 * Accesseur du PathStockage.
	 * @return pathStockage attribut de Config. 
	 */
	public String getPathStockage() {
		return pathStockage;
	}

	/**
	 * Accesseur du NameAppExtern. 
	 * @return nameAppExtern attribut de Config. 
	 */
	public String getNameAppExtern() {
		return nameAppExtern;
	}
	
	public String getPathIndex() {
		return this.pathIndex;
	}
	
	public void setPathStockage(String pathStockage) {
		this.pathStockage=pathStockage;
	}
	
	public void setNameAppExtern(String nameAppExtern) {
		this.nameAppExtern=nameAppExtern;
	}
	
	public void setPathIndex(String pathIndex) {
		this.pathIndex = pathIndex;
	}
	

}
