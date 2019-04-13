package fr.uvsq.jnotes.function;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;

import fr.uvsq.jnotes.exception.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import fr.uvsq.jnotes.note.Note;
import fr.uvsq.jnotes.config.Config;

/**
 * Classe qui va Ãªtre appellÃ©e par toutes les fonctionnalitÃ©es de l'application : edit, delete, search, listing, parma et view. 
 * Elle contient toutes les mÃ©thodes nÃ©cessaires au bon fonctionement des ces fonctionalitÃ©es.
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
	 * MÃ©thode de Edit. 
	 * Si le fichier n'existe pas il est crÃ©er. 
	 * Le process attend que l'utilisateur ai finis d'Ã©diter la note.
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
	 * MÃ©thodes crÃ©ant une liste contenant l'ensemble des notes prÃ©sente dans le dossier.
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
	        if (liste.length==0) return "Aucune note trouvÃ©e. "+"\n";
		}
		else {
			 return "Aucune note trouvÃ©e. "+"\n";
		}
		return s;
	}
	
	/**
	 * MÃ©thode affichant le string produit par listingString.
	 */
	public void listing() {
		System.out.println(listingString());

	}
	
	public void index() {
		try {
			
			Process p=Runtime.getRuntime().exec("firefox config/index.adoc");
			p.waitFor();
			System.out.println("Visualisation de l'index JNotes ");
		}
		catch(IOException e) {
			throw new IndexException();
		}
		catch(Exception e) {
			throw new IndexException();
		}
	}
	
	/**
	 * MÃ©thodes crÃ©ant une liste contenant le chemin et le nom de la note supprimÃ©e
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
	 * MÃ©thode affichant le string produit par deleteString.
	 */
	public void delete(String[] args) throws DeleteException{
		System.out.println(deleteString(args));
		setChanged(); //notification pour la modification de l'index
		notifyObservers();
	}
	
	/**
	 * MÃ©thode affichant le chemin d'une note.
	 */
	public String findPathView(String args) {
		return c.getPathStockage()+"notes/"+args;
	}
	
	/**
	 * MÃ©thode de View. 
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
	
	// exemple search ":project: test" test2
		public String searchFile(String args[]) throws SearchException {
			
			File dossier=new File(c.getPathStockage()+"notes/");
			 String s="";
			if (dossier.exists() && dossier.isDirectory()){
				// lire les fichiers contenus dans le dossier 
		        String liste[] = dossier.list();      
		       
		        if (liste != null) {  
		        	boolean test=false;
		        	for (int i=0;i<liste.length;i++) {
		        		
		        		Path inPath = Paths.get(c.getPathStockage()+"notes/"+liste[i]);
		        		try(
		        				BufferedReader in = Files.newBufferedReader(inPath);
		        		){
		        			String line; int nbLine=1; 
		        			while((line=in.readLine())!=null) {
		        				for(int j=1;j<args.length;j++) {
		        					 if (line.contains(args[j])==true){
		        						s+="La note "+liste[i]+" contient \""+args[j]+"\" a la ligne "+nbLine+".\n";
		        						test=true;
		        					}
		        				}
		        				nbLine+=1;
		        			}
		        		} catch (Exception e) {
							throw new SearchException();
						}
		        		if (test==false) {
		        			s="Aucune note ne contient cette recherche. \n";
		        			return s;
		        		}
		        	}
		        	
		        }
		        if (liste.length==0) return "Aucune note trouvee. "+"\n";
			}
			else {
				 return "Aucune note trouvee. "+"\n";
			}
			return s;
			
		}
	
	/**
	 * MÃ©thode de Search. 
	 * Permet une recherche par mot clÃ©.
	 */
	// exemple search ":project: test" test2
	public void search(String args[]) throws SearchException{
		if (args.length==1) throw new ArgumentException();
		try {
			String s=searchFile(args);
			System.out.println(s);
		}
		catch(SearchException e) {
			throw e;
		}
		
	}
	
	/**
	 * MÃ©thode de Param. 
	 * Elle permet de visualiser les paramÃ¨tres de configurations.
	 * C'est Ã  dire l'application du dossier des notes et le chemin du dossier des notes.
	 */
	public void param(String args[]) throws ParamException{
		// si on tape "jnotes param/p" sans autre argument
		if (args.length<=1) {
			System.out.println("- Chemin du dossier contenant les notes JNotes : "+c.getPathStockage());
			System.out.println("- Application permettant d'Ã©diter les notes JNotes : "+c.getNameAppExtern());
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
					System.out.println("Chemin du dossier contenant les notes JNotes modifiÃ© : "+args[2]);
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
					System.out.println("Application utilisÃ©e pour l'Ã©dition des notes JNotes modifiÃ©e : "+args[2]);
					setChanged(); //notification pour la modification de l'index
					notifyObservers();
				}
				catch (IOException e) {
					throw new ParamException();
				}
				
			}
		}
		else throw new ParamException();
		
	}

}