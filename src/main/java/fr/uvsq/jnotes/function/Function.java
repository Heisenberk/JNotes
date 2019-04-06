package fr.uvsq.jnotes.function;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;

import fr.uvsq.jnotes.exception.*;

import java.io.BufferedWriter;
import java.io.File;

import fr.uvsq.jnotes.note.Note;
import fr.uvsq.jnotes.config.Config;

/**
 * Classe qui va être appellée par toutes les fonctionnalitées de l'application : edit, delete, search, listing, parma et view. 
 * Elle contient toutes les méthodes nécessaires au bon fonctionement des ces fonctionalitées.
 */
public class Function extends Observable {
	private Config c;
	
	//private Observer index;
	
	/**
	 * Constructeur vide de Function.
	 */
	public Function() {
		try {
			c=new Config();
		}
		catch(ConfigurationException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * Constructeur de Function.
	 * @param Config
	 */
	public Function(Config c) {
		this.c=c;
	}
	
	public Config getConfig() {
		return c;
	}
	
	/**
	 * Méthode de Edit. 
	 * Si le fichier n'existe pas il est créer. 
	 * Le process attend que l'utilisateur ai finis d'éditer la note.
	 */
	public void edit(String[] args) throws EditException, IOException, InterruptedException{
		
		// si on tape "jnotes edit/e" sans autre argument
		if (args.length<=1) throw new EditException(); 
		
		// si le fichier existe
		File f1=new File(c.getPathStockage()+"notes/"+args[1]);
		if  (f1.exists() && !f1.isDirectory()) {
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
		setChanged(); //notification pour modifier l'index
		notifyObservers();
	}
	
	/**
	 * Méthodes créant une liste contenant l'ensemble des notes présente dans le dossier.
	 * @return string s
	 */
	public String listingString() {
		String s="";
		File dossier=new File(c.getPathStockage()+"notes/");
		if (dossier.exists() && dossier.isDirectory()){
			// lire les fichiers contenus dans le dossier 
	        String liste[] = dossier.list();      
	 
	        if (liste != null) {  
	        	s+="Listing des notes : "+"\n";
	            for (int i = 0; i < liste.length; i++) {
	                s+="- "+liste[i]+"\n";
	            }
	        }
	        if (liste.length==0) return "Aucune note trouvée. "+"\n";
		}
		else {
			 return "Aucune note trouvée. "+"\n";
		}
		return s;
	}
	
	/**
	 * Méthode affichant le string produit par listingString.
	 */
	public void listing() {
		System.out.println(listingString());
	}
	
	/**
	 * Méthodes créant une liste contenant le chemin et le nom de la note supprimée
	 * @return string s
	 */
	public String deleteString(String[] args) throws DeleteException{
		String s="";
		// si on tape "jnotes delete/d" sans autre argument
		if (args.length<=1) throw new DeleteException(); 
						
		File fichier=new File(c.getPathStockage()+"notes/"+args[1]);
		if(fichier.exists()) {
			s+="Suppression de la note AsciiDoctor "+c.getPathStockage()+"notes/"+args[1]+"\n";
		}
		else throw new DeleteException();
							
		fichier.delete();
		return s;
	}
	
	/**
	 * Méthode affichant le string produit par deleteString.
	 */
	public void delete(String[] args) throws DeleteException{
		System.out.println(deleteString(args));
		setChanged(); //notification pour la modification de l'index
		notifyObservers();
	}
	
	/**
	 * Méthode affichant le chemin d'une note.
	 */
	public String findPathView(String args) {
		return c.getPathStockage()+"notes/"+args;
	}
	
	/**
	 * Méthode de View. 
	 * Affiche la note sur firefox
	 */
	public void view(String[] args) throws ViewException {
		
		// si on tape "jnotes view/v" sans autre argument
		if (args.length<=1) throw new ViewException(); //creer une exception personnelle ArgumentException
		
		try {
			String absolutePath=null;
			File fichier=new File(findPathView(args[1]));
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
	
	/**
	 * Méthode de Search. 
	 * Permet une recherche par mot clé.
	 */
	public void search(String args[]) {
		// A FAIRE PAR SARAH
		
	}
	
	/**
	 * Méthode de Param. 
	 * Elle permet de visualiser les paramètres de configurations.
	 * C'est à dire l'application du dossier des notes et le chemin du dossier des notes.
	 */
	public void param(String args[]) throws ParamException{
		// si on tape "jnotes param/p" sans autre argument
		if (args.length<=1) {
			System.out.println("- Chemin du dossier contenant les notes JNotes : "+c.getPathStockage());
			System.out.println("- Application permettant d'éditer les notes JNotes : "+c.getNameAppExtern());
		}
		else if (args.length>=3) {
			// si on tape "jnotes param/p path [chemin]"
			if (args[1].equals("path")) {
				Path outPath = Paths.get("config/config-jnotes");
				try(
						BufferedWriter out = Files.newBufferedWriter(outPath)
					){
					out.write(args[2]);
					out.newLine();
					out.write(c.getNameAppExtern());
					System.out.println("Chemin du dossier contenant les notes JNotes modifié : "+args[2]);
					c.setPathStockage(args[2]);
					setChanged(); //notification pour la modification de l'index
					notifyObservers();
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
					c.setNameAppExtern(args[2]);
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
