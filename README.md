*Ce markdown contient un manuel utilisateur suivi d'un manuel technique*

Manuel utilisateur JNotes
==
Auteurs : Cl�ment Caumes - Mehdi Merimi - Sarah Ngoc-Mai Pho - Maxime Gonthier  
--


Introduction
--

L'application "JNotes" est un outil permettant la gestion de notes utilisant asciidoctor.
Elle permet � l'utilisateur de modifier, supprimer, lister ou voir des notes. Elle contient �galement une fonction de recherche et un index triant les notes par ordre alphab�tique.
Ce guide a �t� con�u pour faciliter l'utilisation de l'application.

  
Lancement de l'application
--

mvn test lance l'ensemble des tests junit.
	  
	    		
![alternativetext](report/manuel_utilisateur/Captures/test.png)
			
mvn package compile le projet et lance les tests.
	  
	    			
![alternativetext](report/manuel_utilisateur/Captures/mvnpackage.png)
				
java -jar target/jnotes-1.0.0-jar-with-dependencies.jar execute le programme.
	  
	    			
![alternativetext](report/manuel_utilisateur/Captures/ecranacceuil.png)
			
Une fois l'application lanc� l'utilisateurs a acces � diff�rentes fonctionnalit�es d�crite si dessous.

Edit
--
En tapant edit [nom de la note] ou e [nom de la note] un �cran apparait affichant la note.  
Si la note n'existait pas elle est cr�er et pr� remplis.  
  
    
![alternativetext](report/manuel_utilisateur/Captures/edit.png)  

Liste
--
  
    
![alternativetext](report/manuel_utilisateur/Captures/list.png)  

Delete
--
En tapant delete [nom de la note] ou d [nom de la note] le programme supprime la note et affiche un message.
  
    
![alternativetext](report/manuel_utilisateur/Captures/delete.png)

View
--
En tapant view [nom de la note] ou v [nom de la note] la note est ouverte dans firefox.
  
    
![alternativetext](report/manuel_utilisateur/Captures/view2.png)

L'index est �galement consultable. Il est possible de le trier, par contexte par exemple.

![alternativetext](report/manuel_utilisateur/Captures/index_part1.png)


![alternativetext](report/manuel_utilisateur/Captures/index_part2.png)

Search
--
En tapant search [condition] ou s [condition] l'application recherche les notes qui respectent 
  
    
![alternativetext](report/manuel_utilisateur/Captures/search0.png)
  
    
![alternativetext](report/manuel_utilisateur/Captures/search1.png)
		

Param
--
En tapant param ou p, le terminal affiche les param�tres, c'ets � dire l'application d'�dition des notes et le chemin du dossier contenant les notes.
  
    
![alternativetext](report/manuel_utilisateur/Captures/param.png)

Param path
--
En tapant param path [nouveau chemin] ou p path [nouveau chemin], le chemin du dossier contenant les notes est modifi�.
  
    
![alternativetext](report/manuel_utilisateur/Captures/parampath.png)

Param app
--
En tapant param app [nom de l'application] ou p app [nom de l'application], l'application de modification des notes est modifi�.
  
    
![alternativetext](report/manuel_utilisateur/Captures/paramapp.png)

Quit
--
Taper quit permet de quitter l'application.
		


Manuel technique JNotes
==

Introduction
--

Ce manuel a pour objectif de d�crire les pattern utilis�s lors de l'impl�mentation de ce projet.
Plus pr�cisement nous allons expliciter les raisons de leurs utilisation.

Singleton
--

Le singleton est utilis� dans App.java.
Le main est unique, ainsi l'utilisation du pattern singleton permet de facilement cr�er cette unicit�.

Observer
--

Function.java utilise le pattern observer.
L'objectif est de modifier l'index directement lors de la cr�ation ou la suppresion d'une note.
Ainsi lorsqu'une note change d'�tat, l'index en est averti et il se met automatiquement a jour.

Command
--

Le pattern command est utilis� dans ICommand.java, Interpretor.java et ScannerCommand.java

Builder
--

Note.java utilise le pattern builder.  
Une note est d�termin� par son titre mais sa date, l'auteur, le projet et le contexte sont optionels.
Ainsi le pattern builder permet d'impl�menter ces attributs sous la forme d'options facilement.
De plus il y a de nombreux param�tres, ainsi le pattern builder permet d'�viter une multiplication du nombre de 
param�tres utilis�s.
