package fr.uvsq.jnotes.function;

import java.io.IOException;
import java.io.File;

import fr.uvsq.jnotes.config.Config;

public class Function {
	Config c;
	
	public Function() {
		try {
			c=new Config();
		}
		catch(IOException e) {
			System.out.println("Function : "+ e);
		}
		
	}
	
	public void edit() {
		System.out.println("Edition de la note");
		
	}
	
	public void listing() {
		
		File dossier=new File(c.getPathStockage()+"notes/");
		if (dossier.exists() && dossier.isDirectory()){
			// lire les fichiers contenus dans le dossier 
	        String liste[] = dossier.list();      
	 
	        if (liste != null) {  
	        	System.out.println("Listing des notes : ");
	            for (int i = 0; i < liste.length; i++) {
	                System.out.println("- "+liste[i]);
	            }
	        }
	        if (liste.length==0) System.out.println("Aucune note trouvée. ");
		}
		else {
			System.out.println("Aucune note trouvée. ");
		}
	}
	
	public void delete() {
		System.out.println("Suppression des notes");
	}
	
	public void view(String[] args) throws Exception {
		
		// si on tape "jnotes view/v" sans autre argument
		if (args.length<=1) throw new Exception(); //creer une exception personnelle ArgumentException
		
		try {
			String absolutePath=null;
			File fichier=new File(c.getPathStockage()+"notes/"+args[1]);
			if(fichier.exists()) absolutePath=fichier.getAbsolutePath();
			else throw new Exception("Fichier inexistant"); //A CHANGER
			
			System.out.println("Visualisation de la note AsciiDoctor "+absolutePath);
			
			Runtime runtime = Runtime.getRuntime();
			runtime.exec("firefox "+absolutePath);
		}
		catch(IOException e) {
			System.out.println(e); //A CHANGER
		}
		catch(Exception e) {
			System.out.println(e); //A CHANGER
		}
		
		
	}
	
	public void search() {
		System.out.println("Note recherchée");
	}

}
