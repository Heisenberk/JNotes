package fr.uvsq.jnotes.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {
	
	private String pathStockage;
	private String nameAppExtern;
	
	public Config() throws IOException {
		init();
	}
	
	public Config(String pathFile)  throws IOException  {
		init(pathFile);
	}
	
	private void init() throws IOException {
		init("config/config-jnotes");
	}
	
	public void init(String pathFile) throws IOException {
		String line;
		Path inPath=Paths.get(pathFile);
		try (BufferedReader in = Files.newBufferedReader(inPath);){
			String path;
			line = in.readLine();
			pathStockage=line;
			// si l'utilisateur met par exemple /home/user/Bureau au lieu de /home/user/Bureau/ -> on rajoute un "/"
			if(pathStockage.charAt(pathStockage.length()-1)!='/') pathStockage+="/";
			line = in.readLine();
			nameAppExtern = line;
		}
	}
	
	public String getPathStockage() {
		return pathStockage;
	}

	public String getNameAppExtern() {
		return nameAppExtern;
	}
	

}
