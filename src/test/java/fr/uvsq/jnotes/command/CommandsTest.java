package fr.uvsq.jnotes.command;

import static org.junit.Assert.*;
import org.junit.Test;

import fr.uvsq.jnotes.command.Interpretor;
import fr.uvsq.jnotes.exception.ArgumentException;

/**
 * Tests unitaires sur la classe Interpretor
 */
public class CommandsTest {

	/**
	 * Test sur l'appel à commande Edit.
	 */
    @Test
    public void testEdit(){
    	String args1[]= {"edit"};
    	String args2[]= {"e"};
    	Interpretor a1 = new Interpretor();
        EnumCommand test1=a1.detectCommand(args1);
        EnumCommand test2=a1.detectCommand(args2);
    	assertEquals(test1, EnumCommand.EDIT);
    	assertEquals(test2, EnumCommand.EDIT);
    }
    
    /**
     * Test sur l'appel à la commande List.
     */
    @Test
    public void testListing(){
    	String args1[]= {"list"};
    	String args2[]= {"ls"};
    	Interpretor a1 = new Interpretor();
    	EnumCommand test1=a1.detectCommand(args1);
    	EnumCommand test2=a1.detectCommand(args2);
    	assertEquals(test1, EnumCommand.LIST);
    	assertEquals(test2, EnumCommand.LIST);
    }
    
    /**
     * Test sur l'appel à la commande Delete. 
     */
    @Test (expected=ArgumentException.class)
    public void testDelete(){
    	String args1[]= {"delete"};
    	String args2[]= {"d"};
    	Interpretor a1 = new Interpretor();
    	EnumCommand test1=a1.detectCommand(args1);
    	EnumCommand test2=a1.detectCommand(args2);
    	assertEquals(test1, EnumCommand.DELETE);
    	assertEquals(test2, EnumCommand.DELETE);
    }
    
    /**
     * Test sur l'appel à la commande View.
     */
    @Test
    public void testView(){
    	String args1[]= {"view", "note"};
    	String args2[]= {"v", "note"};
    	Interpretor a1 = new Interpretor();
    	EnumCommand test1=a1.detectCommand(args1);
    	EnumCommand test2=a1.detectCommand(args2);
    	assertEquals(test1, EnumCommand.VIEW);
    	assertEquals(test2, EnumCommand.VIEW);
    }
    
    /**
     * Test sur l'appel à la commande Search.
     */
    @Test
    public void testSearch(){
    	String args1[]= {"search"};
    	String args2[]= {"s"};
    	Interpretor a1 = new Interpretor();
    	EnumCommand test1=a1.detectCommand(args1);
    	EnumCommand test2=a1.detectCommand(args2);
    	assertEquals(test1, EnumCommand.SEARCH);
    	assertEquals(test2, EnumCommand.SEARCH);
    }
    
    /**
     * Test sur l'appel à la commande Param.
     */
    @Test
    public void testParam(){
    	String args1[]= {"param"};
    	String args2[]= {"p"};
    	Interpretor a1 = new Interpretor();
    	EnumCommand test1=a1.detectCommand(args1);
    	EnumCommand test2=a1.detectCommand(args2);
    	assertEquals(test1, EnumCommand.PARAM);
    	assertEquals(test2, EnumCommand.PARAM);
    }
    
    /**
     * Test sur un appel a une commande inconnue. 
     */
    @Test
    public void testInvalide(){
    	String args1[]= {"invalide"};
    	Interpretor a1 = new Interpretor();
    	EnumCommand test1 = a1.detectCommand(args1);
    	assertEquals(test1, EnumCommand.COMMAND_INVALIDE);
    }
    

    
}
