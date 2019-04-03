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

       assertNote(n,"Test1","Auteur inconnu",LocalDate.now(),"projet","work");
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

       assertNote(n,"Test1","Lopes Stephane",LocalDate.now(),"projet","work");
    }
    
    private static void assertNote(Note n, String title, String author,LocalDate date,String project,String context) {

        assertEquals(title, n.getTitle());

        assertEquals(author, n.getAuthor());

        assertEquals(date, n.getDate());

        assertEquals(project, n.getProject());
     
        assertEquals(context, n.getContext());

      }

}