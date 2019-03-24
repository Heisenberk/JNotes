package fr.uvsq.jnotes;
import java.time.LocalDate;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests unitaires sur la classe Note.
 */
public class NoteTest {

    @Test
    public void testConstructeur1(){
    	Note n = new Note	
    			.Builder("Test1")
    			.build();
    	 assertEquals(n.getTitle(), "Test1");
    	 assertEquals(n.getAuthor(), "Caumes Clément, Gonthier Maxime, Merimi Mehdi, Pho Sarah");
    	 assertEquals(n.getDate(), LocalDate.now());
    	 assertEquals(n.getProject(), "inf201");
    	 assertEquals(n.getContext(), "work");
    }
    
    @Test
    public void testConstructeur2(){
    	Note n = new Note	
    			.Builder("Test1")
    			.author("Lopes Stephane")
    			.build();
    	 assertEquals(n.getTitle(), "Test1");
    	 assertEquals(n.getAuthor(), "Lopes Stephane");
    	 assertEquals(n.getDate(), LocalDate.now());
    	 assertEquals(n.getProject(), "inf201");
    	 assertEquals(n.getContext(), "work");
    }

}