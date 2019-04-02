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


public class ConfigTest {
	@Before
	public void initialize1(){
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
	
	@Before
	public void initialize2(){
		Path outPath = Paths.get("config-test2");
		try (BufferedWriter out = Files.newBufferedWriter(outPath);){
			out.write(".");
			out.newLine();
			out.write("nano");
			out.newLine();
		}
		catch(IOException e) {
			System.out.println("Erreur Before ConfigTest");
		}
	}
	
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
	
	@Test
	public void testInit2(){
		Config c=null;
		try {
			c=new Config("config-test2");
		}
		catch(ConfigurationException e) {
			System.out.println("Erreur TestInit ConfigTest");
		}
		assertEquals(c.getPathStockage(), "./");
		assertEquals(c.getNameAppExtern(), "nano");
	}
	
	@After
	public void finalize1() {
		 File f=new File("config-test");
	     f.delete();
	}
	
	@After
	public void finalize2() {
		 File f=new File("config-test2");
	     f.delete();
	}

}
