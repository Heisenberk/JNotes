package fr.uvsq.jnotes.function;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import exception.ConfigurationException;

import java.io.BufferedWriter;
import java.io.File;

import fr.uvsq.jnotes.Note;
import fr.uvsq.jnotes.config.Config;

public class Function {
	Config c;
	
	public Function() {
		try {
			c=new Config();
		}
		catch(ConfigurationException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public void edit(String[] args) throws Exception{
		
		// si on tape "jnotes delete/d" sans autre argument
		if (args.length<=1) throw new Exception(); //creer une exception personnelle ArgumentException
		
		//try {
			// si le fichier existe
			File f1=new File(c.getPathStockage()+"notes/"+args[1]);
			if (f1.exists() && !f1.isDirectory()) {
				String commande_fichier_existant="xterm -e "+c.getNameAppExtern()+" "+c.getPathStockage()+"notes/"+args[1];
				Process p1=Runtime.getRuntime().exec(commande_fichier_existant);
				p1.waitFor();
				System.out.println("Edition de la nouvelle note "+c.getPathStockage()+"notes/"+args[1]);
			}
			
			// si le fichier n'existe pas
			else {
				//convertit "ma premiere note" en "ma_premiere_note.adoc"
				System.out.println("Fichier non existant");
				String absolutePath1=null;
				String name=""; String nameWithout="";
				for(int i=1;i<args.length;i++) {
					if (i!=1) name+="_";
					if (i!=1) nameWithout+=" ";
					name+=args[i];
					nameWithout+=args[i];
				}
				name+=".adoc";
				Note note=new Note
						.Builder(nameWithout)
						.build();
				
				Path outPath=Paths.get(c.getPathStockage()+"notes/"+name);
				try(
					BufferedWriter out = Files.newBufferedWriter(outPath)
				){
					note.create(out);
				}
				catch(Exception e) {
					System.out.println("erreur"); //ACHANGER
				}
				
				String commande_fichier_inexistant="xterm -e "+c.getNameAppExtern()+" "+c.getPathStockage()+"notes/"+name;
				Process p2=Runtime.getRuntime().exec(commande_fichier_inexistant);
				p2.waitFor();
				System.out.println("Edition de la nouvelle note "+c.getPathStockage()+"notes/"+name);
					
			}
		
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
	
	public void delete(String[] args) throws Exception{
		// si on tape "jnotes delete/d" sans autre argument
			if (args.length<=1) throw new Exception(); //creer une exception personnelle ArgumentException
				
				try {
					String absolutePath=null;
					File fichier=new File(c.getPathStockage()+"notes/"+args[1]);
					if(fichier.exists()) absolutePath=fichier.getAbsolutePath();
					else throw new Exception("Fichier inexistant"); //A CHANGER
					
					System.out.println("Suppression de la note AsciiDoctor "+absolutePath);
					
					fichier.delete();
				}
				catch(Exception e) {
					System.out.println(e); //A CHANGER
				}
	}
	
	public void view(String[] args) throws Exception {
		
		// si on tape "jnotes view/v" sans autre argument
		if (args.length<=1) throw new Exception(); //creer une exception personnelle ArgumentException
		
		try {
			String absolutePath=null;
			File fichier=new File(c.getPathStockage()+"notes/"+args[1]);
			if(fichier.exists()) absolutePath=fichier.getAbsolutePath();
			else throw new Exception("Fichier inexistant"); //A CHANGER
			
			Process p=Runtime.getRuntime().exec("firefox "+absolutePath);
			p.waitFor();
			System.out.println("Visualisation de la note AsciiDoctor "+absolutePath);
		}
		catch(IOException e) {
			System.out.println(e); //A CHANGER
		}
		catch(Exception e) {
			System.out.println(e); //A CHANGER
		}
		
		
	}
	
	public void search(String args[]) {
		System.out.println("Note recherchée"+args[1]);
		// A FAIRE PAR SARAH
		
	}

}
