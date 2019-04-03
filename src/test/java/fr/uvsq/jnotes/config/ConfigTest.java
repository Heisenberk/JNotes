package fr.uvsq.jnotes.config;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.ConfigurationException;

/**
 * Tests unitaires sur la classe Config.
 */
public class ConfigTest {
	
	/**
	 * Initialisation d'un premier fichier test de configuration. 
	 */
	@Before
	public void initialize(){
		Path outPath = Paths.get("config-test");
		try (BufferedWriter out = Files.newBufferedWriter(outPath);){
			out.write("./");
			out.newLine();
			out.write("nano");
			out.newLine();
		}
		catch(IOException e) {
			System.out.println("Erreur Before ConfigTest");
		}
	}
	
	
	/**
	 * Test sur la bonne lecture du fichier de configuration. 
	 */
	@Test
	public void testInit(){
		Config c=null;
		try {
			c=new Config("config-test");
		}
		catch(ConfigurationException e) {
			System.out.println("Erreur TestInit ConfigTest");
		}
		assertEquals(c.getPathStockage(), "./");
		assertEquals(c.getNameAppExtern(), "nano");
	}
	
	/**
	 * Suppression du fichier test de configuration. 
	 */
	@After
	public void finalize() {
		 File f=new File("config-test");
	     f.delete();
	}

}
