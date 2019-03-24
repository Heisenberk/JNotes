package fr.uvsq.jnotes.command;

import static org.junit.Assert.*;
import org.junit.Test;

import fr.uvsq.jnotes.command.ArgumentCommand;

public class ArgumentCommandTest {

    @Test
    public void testEdit(){
    	String args1[]= {"edit"};
    	String args2[]= {"e"};
    	ArgumentCommand a1 = new ArgumentCommand();
    	ArgumentCommand a2 = new ArgumentCommand();
        EnumCommand test1=a1.detectCommand(args1);
        EnumCommand test2=a1.detectCommand(args2);
    	assertEquals(test1, EnumCommand.EDIT);
    	assertEquals(test2, EnumCommand.EDIT);
    }
    
    @Test
    public void testListing(){
    	String args1[]= {"list"};
    	String args2[]= {"l"};
    	ArgumentCommand a1 = new ArgumentCommand();
    	ArgumentCommand a2 = new ArgumentCommand();
    	EnumCommand test1=a1.detectCommand(args1);
    	EnumCommand test2=a1.detectCommand(args2);
    	assertEquals(test1, EnumCommand.LIST);
    	assertEquals(test2, EnumCommand.LIST);
    }
    
    @Test
    public void testDelete(){
    	String args1[]= {"delete"};
    	String args2[]= {"d"};
    	ArgumentCommand a1 = new ArgumentCommand();
    	ArgumentCommand a2 = new ArgumentCommand();
    	EnumCommand test1=a1.detectCommand(args1);
    	EnumCommand test2=a1.detectCommand(args2);
    	assertEquals(test1, EnumCommand.DELETE);
    	assertEquals(test2, EnumCommand.DELETE);
    }
    
    @Test
    public void testView(){
    	String args1[]= {"view"};
    	String args2[]= {"v"};
    	ArgumentCommand a1 = new ArgumentCommand();
    	ArgumentCommand a2 = new ArgumentCommand();
    	EnumCommand test1=a1.detectCommand(args1);
    	EnumCommand test2=a1.detectCommand(args2);
    	assertEquals(test1, EnumCommand.VIEW);
    	assertEquals(test2, EnumCommand.VIEW);
    }
    
    @Test
    public void testSearch(){
    	String args1[]= {"search"};
    	String args2[]= {"s"};
    	ArgumentCommand a1 = new ArgumentCommand();
    	ArgumentCommand a2 = new ArgumentCommand();
    	EnumCommand test1=a1.detectCommand(args1);
    	EnumCommand test2=a1.detectCommand(args2);
    	assertEquals(test1, EnumCommand.SEARCH);
    	assertEquals(test2, EnumCommand.SEARCH);
    }
    
    @Test
    public void testInvalide(){
    	String args1[]= {"invalide"};
    	ArgumentCommand a1 = new ArgumentCommand();
    	EnumCommand test1=a1.detectCommand(args1);
    	assertEquals(test1, EnumCommand.COMMAND_INVALIDE);
    }
    
    @Test
    public void testSansArg(){
    	String args1[]= {};
    	ArgumentCommand a1 = new ArgumentCommand();
    	EnumCommand test1=a1.detectCommand(args1);
    	assertEquals(test1, EnumCommand.NO_COMMAND);
    }
    
}