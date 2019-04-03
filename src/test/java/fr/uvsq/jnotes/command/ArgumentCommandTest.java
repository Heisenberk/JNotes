package fr.uvsq.jnotes.command;

import static org.junit.Assert.*;
import org.junit.Test;

import fr.uvsq.jnotes.command.ArgumentCommand;

/**
 * Tests unitaires sur la classe ArgumentCommand
 */
public class ArgumentCommandTest {

	/**
	 * Test sur l'appel à commande Edit.
	 */
    @Test
    public void testEdit(){
    	String args1[]= {"edit"};
    	String args2[]= {"e"};
    	ArgumentCommand a1 = new ArgumentCommand();
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
    	ArgumentCommand a1 = new ArgumentCommand();
    	EnumCommand test1=a1.detectCommand(args1);
    	EnumCommand test2=a1.detectCommand(args2);
    	assertEquals(test1, EnumCommand.LIST);
    	assertEquals(test2, EnumCommand.LIST);
    }
    
    /**
     * Test sur l'appel à la commande Delete. 
     */
    @Test
    public void testDelete(){
    	String args1[]= {"delete"};
    	String args2[]= {"d"};
    	ArgumentCommand a1 = new ArgumentCommand();
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
    	String args1[]= {"view"};
    	String args2[]= {"v"};
    	ArgumentCommand a1 = new ArgumentCommand();
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
    	ArgumentCommand a1 = new ArgumentCommand();
    	EnumCommand test1=a1.detectCommand(args1);
    	EnumCommand test2=a1.detectCommand(args2);
    	assertEquals(test1, EnumCommand.SEARCH);
    	assertEquals(test2, EnumCommand.SEARCH);
    }
    
    /**
     * Test sur un appel a une commande inconnue. 
     */
    @Test
    public void testInvalide(){
    	String args1[]= {"invalide"};
    	ArgumentCommand a1 = new ArgumentCommand();
    	EnumCommand test1=a1.detectCommand(args1);
    	assertEquals(test1, EnumCommand.COMMAND_INVALIDE);
    }
    
    /**
     * Test sur un appel a aucune commande. 
     */
    @Test
    public void testSansArg(){
    	String args1[]= {};
    	ArgumentCommand a1 = new ArgumentCommand();
    	EnumCommand test1=a1.detectCommand(args1);
    	assertEquals(test1, EnumCommand.NO_COMMAND);
    }
    
}