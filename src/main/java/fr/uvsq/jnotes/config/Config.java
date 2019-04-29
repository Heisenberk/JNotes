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
	 * pathStockage represente le chemin du dossier contenant les notes. 
	 */
	private String pathStockage;
	
	/**
	 * pathIndex represente le chemin du dossier contenant les docs de l'index lucene. 
	 */
	private String pathIndex;
	
	/**
	 * nameAppExtern represente l'application a utiliser pour editer une note.
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
	
	/**
	 * Constructeur de Config avec un chemin des notes et l'application pour les editer en argument.
	 * @param pathStockage chemin du dossier des notes. 
	 * @param nameAppExtern nom de l'application qui va editer les notes. 
	 */
	public Config(String pathStockage, String nameAppExtern) {
		this.pathStockage = pathStockage;
		this.nameAppExtern = nameAppExtern;
		this.pathIndex = "index";
	}
	
	/**
	 * Constructeur de Config avec un chemin des notes et l'application pour les editer en argument.
	 * @param pathStockage chemin du dossier des notes. 
	 * @param nameAppExtern nom de l'application qui va editer les notes. 
	 * @param pathIndex chemin du dossier contenant les docs de l'index lucene.
	 */
	public Config(String pathStockage, String nameAppExtern, String pathIndex) {
		this.pathStockage = pathStockage;
		this.nameAppExtern = nameAppExtern;
		this.pathIndex = pathIndex;
	}
	
	/**
	 * Methode init qui va initialiser les attributs de Config. 
	 * @throws ConfigurationException s'il y a un probleme de lecture du fichier de configuration.
	 */
	private void init() throws ConfigurationException {
		init("config/config-jnotes");
	}
	
	/**
	 * Methode init qui va initialiser les attributs de Config. 
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
			
			//ne pas indexer à chaque fois
			Indexer.indexer(pathIndex, pathStockage);
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
	
	/**
	 * Accesseur du pathIndex.
	 * @return chemin de l'index lucene.
	 */
	public String getPathIndex() {
		return this.pathIndex;
	}
	
	/**
	 * Setter du pathStockage. 
	 * @param pathStockage represente le chemin du stockage des notes. 
	 */
	public void setPathStockage(String pathStockage) {
		this.pathStockage=pathStockage;
	}
	
	/**
	 * Setter du nameAppExtern. 
	 * @param nameAppExtern represente le nom de l'application pour l'edition des notes. 
	 */
	public void setNameAppExtern(String nameAppExtern) {
		this.nameAppExtern=nameAppExtern;
	}
	
	/**
	 * Setter du pathIndex. 
	 * @param pathIndex represente le chemin lucene. 
	 */
	public void setPathIndex(String pathIndex) {
		this.pathIndex = pathIndex;
	}
}
