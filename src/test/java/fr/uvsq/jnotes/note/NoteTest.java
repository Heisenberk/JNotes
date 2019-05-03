package fr.uvsq.jnotes.note;

import java.time.LocalDate;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests unitaires sur la classe Note.
 */
public class NoteTest {

	/**
	 * Test unitaire 1 sur le constructeur de Note. 
	 */
    @Test
    public void testConstructeur1(){
    	Note n = new Note	
    			.Builder("Test1")
    			.build();
    	 assertEquals(n.getTitle(), "Test1");
    	 assertEquals(n.getAuthor(), "Auteur inconnu");
    	 assertEquals(n.getDate(), LocalDate.now());
    	 assertEquals(n.getProject(), "project");
    	 assertEquals(n.getContext(), "work");
    }
    
    /**
     * Test unitaire 2 sur le constructeur de Note. 
     */
    @Test
    public void testConstructeur2(){
    	Note n = new Note	
    			.Builder("Test1")
    			.author("Lopes Stephane")
    			.build();
    	
    	 assertEquals(n.getTitle(), "Test1");
    	 assertEquals(n.getAuthor(), "Lopes Stephane");
    	 assertEquals(n.getDate(), LocalDate.now());
    	 assertEquals(n.getProject(), "project");
    	 assertEquals(n.getContext(), "work");
    }

}