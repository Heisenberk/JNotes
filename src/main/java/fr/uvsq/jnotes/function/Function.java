package fr.uvsq.jnotes.function;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import fr.uvsq.jnotes.exception.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import fr.uvsq.jnotes.note.Note;
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
	
	public Function(Config c) {
		this.c=c;
	}
	
	public Config getConfig() {
		return c;
	}
	
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
			String absolutePath1 = null;
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
	
	public void listing() {
		System.out.println(listingString());
	}
	
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
	
	public void delete(String[] args) throws DeleteException{
		System.out.println(deleteString(args));
	}
	
	public String path(String args) {
		return c.getPathStockage()+"notes/"+args;
	}
	
	public void view(String[] args) throws ViewException {
		// si on tape "jnotes view/v" sans autre argument
		if (args.length <= 1) 
			throw new ViewException("View n'a pas d'argument."); 
		try {
			String absolutePath = null;
			File fichier = new File(path(args[1]));
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
	 * Affiche un ensemble de chaines de caractères
	 * @param args arguments à afficher
	 */
	private void displayStrings(Iterable<String> strings){
		for(String string : strings) {
			System.out.print(string + ",  ");
		}
		System.out.println("\n");
	}
	
	//verifie si arg se situe dans le fichier, dans la liste de puces
	@SuppressWarnings("resource")
	private boolean fileContains(String fileName, String arg) {
		File file = new File(path(fileName));
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {System.out.println("Function Search : erreur à l'initialisation du buffer.");}
		try {
			in.readLine();
			in.readLine();
			in.readLine();
			String line = in.readLine();
			while( line != null) {
				if(line.contains(arg))
					return true;
				line = in.readLine();
			}
		} catch (IOException e) { System.out.println("Function Search : erreur pendant la lecture du fichier.");}
		return false;
	}

	//cherche dans le fichier si il a y a le couple :tag: value si oui true si non false
	@SuppressWarnings("resource")
	private boolean searchTag(String fileName, String tag, String value){
		//attention si le tag ou la valeur contient un espace
		File file = new File(path(fileName));
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {System.out.println("Function Search : erreur à l'initialisation du buffer.");}
		try {
			in.readLine();
			in.readLine();
			in.readLine();
			String line = in.readLine();
			while(line != null) {
				if(line.length() != 0){
					if(line.charAt(0) == '=') {
						line = in.readLine();
						continue;
					}
					String[] words = line.split(" ");
					if(line.charAt(0) == ':'){
						if(words.length < 2)
							return false;
						if(words[0].compareTo(":" + tag + ":") == 0) {
							String v = words[1];
							if(v.compareTo(value) == 0)
								return true;
						}
					}
				}
				line = in.readLine();
			}
		} catch (IOException e) { System.out.println("Function Search : erreur pendant la lecture du fichier.");}
		return false;
		
	}
		
	/**
	 * Indique si le fichier contient tous les mots en argument
	 * @param args arguments tapés en ligne de commande. 
	 * @param fileName le nom de la note
	 * @return false si le mot n'est pas contenu dans la note, true sinon.
	 */
	private boolean searchInFile(String fileName, String[] args) throws SearchException {
		//initialisé a false ; lu comme une table de conditions(args) sur chaque 
		//argument, false si pas verifiees
		String tag = "";
		for(int i = 1 ; i<args.length ; i++) {
			int length = args[i].length();
			if(args[i].charAt(0) == ':' && args[i].charAt(length - 1) == ':' && length > 2) {
				//si on a un argument de cette forme :*: alors on lit un tag
				tag = args[i].split(":")[1];
				continue;
			}
			if(tag != "") {
				if(!searchTag(fileName, tag, args[i]))
					return false;
				tag = "";
				continue;
			}
			if(!fileContains(fileName, args[i]))
				return false;
		}
		if(tag != "") //il manque un argument donc erreur
			throw new SearchException("Tag vide");
		return true;
	}
	
	
	/**
	 * Affiche dans le terminal la liste des notes qui répondent au critère de recherche 
	 * @param args tapés en ligne de commande. 
	 * @return renvoie la liste des notes concordantes.
	 * @throws SearchException quand il n'y a aucune mot à chercher
	 */
	public List<String> search(String args[]) throws SearchException {
		File dir=new File(path(""));
		if(args.length == 1)
			throw new SearchException("Aucun mot à chercher.");
		List<String> files = new ArrayList<String>();
		if (dir.exists() && dir.isDirectory()){
			for(String fileName : dir.list()) {
				if(searchInFile(fileName, args)) 
					files.add(fileName);
				}
			if(files.isEmpty())
				System.out.println("Aucun résultat");
			else {
				System.out.println("Résultat : ");
				displayStrings(files);
			}
			return files;
		}
		else
			new SearchException("Aucune note trouvée.");
		return null;

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
				Path outPath = Paths.get("config/config-jnotes");
				try(
						BufferedWriter out = Files.newBufferedWriter(outPath)
					){
					out.write(args[2]);
					out.newLine();
					out.write(c.getNameAppExtern());
					System.out.println("Chemin du dossier contenant les notes JNotes modifié : "+args[2]);
					c.setPathStockage(args[2]);
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
