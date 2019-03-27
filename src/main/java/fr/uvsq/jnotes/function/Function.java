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
	        if (liste.length==0) System.out.println("Aucune note trouvée2. ");
		}
		else {
			System.out.println("Aucune note trouvée1. ");
		}
	}
	
	public void delete() {
		System.out.println("Suppression des notes");
	}
	
	public void view() {
		System.out.println("Note vue");
	}
	
	public void search() {
		System.out.println("Note recherchée");
	}

}
