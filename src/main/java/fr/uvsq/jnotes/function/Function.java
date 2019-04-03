package fr.uvsq.jnotes.function;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import exception.ConfigurationException;
import exception.DeleteException;
import exception.EditException;
import exception.ParamException;
import exception.ViewException;

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
	
	public void edit(String[] args) throws EditException, IOException, InterruptedException{
		
		// si on tape "jnotes edit/e" sans autre argument
		if (args.length<=1) throw new EditException(); 
		
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
	
	public void delete(String[] args) throws DeleteException{
		// si on tape "jnotes delete/d" sans autre argument
		if (args.length<=1) throw new DeleteException(); //creer une exception personnelle ArgumentException
				
		try {
			String absolutePath=null;
			File fichier=new File(c.getPathStockage()+"notes/"+args[1]);
			if(fichier.exists()) absolutePath=fichier.getAbsolutePath();
			else throw new Exception("Fichier inexistant"); //A CHANGER
					
			System.out.println("Suppression de la note AsciiDoctor "+absolutePath);
					
			fichier.delete();
		}
		catch(Exception e) {
			throw new DeleteException();
		}
	}
	
	public void view(String[] args) throws ViewException {
		
		// si on tape "jnotes view/v" sans autre argument
		if (args.length<=1) throw new ViewException(); //creer une exception personnelle ArgumentException
		
		try {
			String absolutePath=null;
			File fichier=new File(c.getPathStockage()+"notes/"+args[1]);
			if(fichier.exists()) absolutePath=fichier.getAbsolutePath();
			else throw new ViewException();
			
			Process p=Runtime.getRuntime().exec("firefox "+absolutePath);
			p.waitFor();
			System.out.println("Visualisation de la note AsciiDoctor "+absolutePath);
		}
		catch(IOException e) {
			throw new ViewException();
		}
		catch(Exception e) {
			throw new ViewException();
		}
		
		
	}
	
	public void search(String args[]) {
		// A FAIRE PAR SARAH
		
	}
	
	public void param(String args[]) throws ParamException{
		// si on tape "jnotes param/p" sans autre argument
		if (args.length<=1) {
			System.out.println("- Chemin du dossier contenant les notes JNotes : "+c.getPathStockage());
			System.out.println("- Application permettant d'éditer les notes JNotes : "+c.getNameAppExtern());
		}
		else if (args.length>=3) {
			// si on tape "jnotes param/p path [chemin]"
			if (args[1].equals("path")) {
				System.out.println("coucou");
				Path outPath = Paths.get("config/config-jnotes");
				try(
						BufferedWriter out = Files.newBufferedWriter(outPath)
					){
					out.write(args[2]);
					out.newLine();
					out.write(c.getNameAppExtern());
					System.out.println("Chemin du dossier contenant les notes JNotes modifié : "+args[2]);
				}
				catch (IOException e) {
					throw new ParamException();
				}
			}
			// si on tape "jnotes param/p app [application]"
			else if (args[1].equals("app")) {
				Path outPath = Paths.get("config/config-jnotes");
				try(
						BufferedWriter out = Files.newBufferedWriter(outPath)
					){
					out.write(c.getPathStockage());
					out.newLine();
					out.write(args[2]);
				}
				catch (IOException e) {
					throw new ParamException();
				}
				System.out.println("Application utilisée pour l'édition des notes JNotes modifiée : "+args[2]);
			}
		}
		else throw new ParamException();
		
	}

}
