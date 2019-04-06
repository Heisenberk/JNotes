package fr.uvsq.jnotes.function;

import java.util.Observable;
import java.util.Observer;

public class Index implements Observer{

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Function){       
			Function f = (Function) o;
			System.out.println("Modification de l'index effectuee. ");
			// faire la lecture et l'analyse des notes
        } 
		
	}

}
