package fr.uvsq.jnotes;

import java.util.ArrayList;
import java.util.List;

import fr.uvsq.jnotes.command.*;
import fr.uvsq.jnotes.function.Function;
import fr.uvsq.jnotes.function.Update;
import fr.uvsq.jnotes.note.Note;

/**
 * Enumération représentant le main de l'application. 
 * Utilisation du pattern Singleton pour construire une unique 
 * entité pour le main. 
 */
enum App {
	/**
	 * Variable d'environnement. 
	 */
	ENVIRONNEMENT;

	/**
	 * Methode run qui appelle les fonctions nécessaires à l'application. 
	 * @param args arguments en ligne de commande. 
	 */
	public void run(String[] args){
        ArgumentCommand arg = new ArgumentCommand();
        arg.setArgument(args);
		
		
		/*List<Integer> list=new ArrayList<Integer>();
		list.add(1);list.add(3);list.add(1);list.add(4);list.add(1);list.add(3);list.add(3);list.add(1);list.add(2);
		List <Integer> sublist=new ArrayList<Integer>();
		//sublist=null;
		List <Integer> cpy = new ArrayList<Integer>();
		for (int u=0; u<list.size(); u++) {
			cpy.add(list.get(u));
		}
		System.out.println("SIZE:"+cpy.size());
		int val;
		System.out.println(list);
		while(cpy.isEmpty()==false) {
			//sublist=null;
			val=cpy.get(0);
			sublist.add(val);
			System.out.println(val);
			cpy.remove(0);
			System.out.println("SIZE:"+cpy.size());
			for(int j=0;j<cpy.size();j++) {
				if (cpy.get(j)==val) {
					sublist.add(cpy.get(j));
					cpy.remove(j);
				}
			}
			System.out.println("SUBLIST:"+sublist);
			System.out.println("LIST:"+cpy);
		}*/
		
	}

	/**
	 * Methode main qui appelle l'unique variable d'environnement. 
	 * @param args arguments en ligne de commande. 
	 */
	public static void main(String[] args){
		ENVIRONNEMENT.run(args);
	}
}
