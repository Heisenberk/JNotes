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
	  
![mvn test](report/pictures/test.png)
			
mvn package compile le projet et lance les tests.
	  
	    			
![mvn package](report/pictures/mvnpackage.png)
				
java -jar target/jnotes-1.0.0-jar-with-dependencies.jar ex�cute le programme.
	  
	    			
![accueil](report/pictures/ecranaccueil.png)
			
Une fois l'application lanc�e, l'utilisateur a acc�s � diff�rentes fonctionnalit�s d�crites ci-dessous.

Help
--
En tapant help ou h, le terminal affiche la liste des commandes support�es par l'application jnotes.
    
![help](report/pictures/help.PNG)  

Edit
--
En tapant edit [nom de la note] ou e [nom de la note], un �cran apparait affichant la note.  
Si la note n'existait pas, elle est cr��e et pr�-remplie.  
  
![edit](report/pictures/edit.png)  

List
--
En tapant list ou ls, le terminal affiche la liste des notes contenues dans le dossier de notes configur� dans l'application.
    
![list](report/pictures/list.png)  

Delete
--
En tapant delete [nom de la note] ou d [nom de la note], le programme supprime la note et affiche un message.

![delete](report/pictures/delete.png)

View
--
En tapant view [nom de la note] ou v [nom de la note], la note est ouverte dans firefox.
   
![view](report/pictures/view.PNG)

Index
--
L'index est �galement consultable. Cet index a plusieurs tris : par ordre alphab�tique de titre, selon l'attribut contexte, selon l'attribut projet et enfin par mois.

![commande index](report/pictures/commandeIndex.PNG)

On obtiendra donc :

![index](report/pictures/index.PNG)

Search
--
En tapant search [condition1] [condition2] .. ou s [condition1] [condition2] .. , l'application recherche les notes qui respectent toutes les conditions.

Les conditions doivent �tre prot�g�es par des guillemets ".

Selon leur forme, elles permettent d'effectuer 3 types de recherches :

-Recherche par tag :
    La condition devient : 
           --     ":tag:valeur"
    [tag] prend les valeurs "author", "title", "context" ou "project"
    
Si [valeur] au moins un caractere special alors le programme recherchera les notes dont le champ [tag] est compl�tement �gal � [valeur].
Dans l'autre cas, on peut effectuer une recherche par mot qui interpretera les expressions r�guli�res.
    
-Recherche par date
    Il est possible de rechercher les notes selon leur date.
    Pour trouver une note ayant �t� r�dig�e entre [date1] - 1 jour et [date2] inclus, la requ�te est :
           --    ":date:[date1 TO date2]"
    Pour trouver une note a une date [date1] a un jour pr�s ([date1] et [date1] - 1jour):
           --    ":date:[date1 TO date1]"
    Pour trouver toutes les notes ayant �t� �crites � partir de [date1] - 1 incluse :
           --    ":date:[date1 TO *]"
    Et inversement pour les notes ayant �t� �crites jusqu'� [date2] incluse :
           --    ":date:[* TO date2]"
    On peut aussi rechercher les notes �crites � toute date, m�me si ls est plus efficace pour cela :
           --    ":date:[* to *]"

Une condition qui contient au moins un ':' sera interpr�t�e comme une recherche par tag ou par date.
                
-Recherche dans le corps
    La condition requise pour cette recherche peut s'�crire ainsi :
           --   "regex1 regex2 ..."
    Les espaces prennent ici le r�le de s�parateur entre chaque expression r�guli�re. 
    La port�e de la recherche dans le corps est le mot: Il n'est pas possible de rechercher des ensembles de mots s�par�s par des caract�res sp�ciaux ( ex: espace, virgule, etc )
    L'ordre dans l'argument ne donne pas d'indication sur l'ordre dans le document.
    
    
Les recherches ne sont pas cases sensitives.
Il est possible de combiner les conditions, le programme cherchera alors les fichiers qui respectent chacunes des conditions en entr�e. 

Voici un exemple simple de recherche. Pour cela, on cr�e pr�alablement une note par exemple :
    
![alternativetext](report/pictures/search_note.PNG)
  
On fait ensuite la recherche et on remarque que 2 notes contiennent l'�l�ment recherch� (dont la note qui vient d'�tre cr��e).

![alternativetext](report/pictures/search_context.PNG)
		
Param
--
En tapant param ou p, le terminal affiche les param�tres, c'ets � dire l'application d'�dition des notes et le chemin du dossier contenant les notes.
     
![alternativetext](report/pictures/param.png)

Param path
--
En tapant param path [nouveau chemin] ou p path [nouveau chemin], le chemin du dossier contenant les notes est modifi�.
     
![alternativetext](report/pictures/parampath.png)

Param app
--
En tapant param app [nom de l'application] ou p app [nom de l'application], l'application de modification des notes est modifi�.
      
![alternativetext](report/pictures/paramapp.png)

Quit
--
Taper quit permet de quitter l'application.
		


Manuel technique JNotes
==

Introduction
--

Ce manuel a pour objectif de d�crire les patterns utilis�s lors de l'impl�mentation de ce projet.
Plus pr�cisement, nous allons expliciter les raisons de leurs utilisations.

Packages 
--
Nous avons une s�rie de packages dans JNotes : 

- le package "note" contient limpl�mentation de la note ainsi que toutes les classes n�cessaires au pattern iterator. Ce patern permettra par la suite de trier ces notes selon un crit�re particulier.
- le package "utils" contient les classes qui seront manipul�s dans toutes les autres classes. Il contient des classes "bas niveau" permettant de manipuler plus facilement les fichiers par exemple. 
- le package "index" contient toutes les classes relatives � la manipulation de listes � l'aide de la biblioth�que de recherche par mots-cl�s Lucene.
- le package "config" contient toutes les classes relatives � la configuration de l'application (notamment le logiciel d'�dition de notes ou la localisation du dossier contenant les notes de JJNotes).
- le package "command" contient toutes les classes relatives aux classes ma�tresses de la gestion des commandes.
- le package "function" contient toutes les classes relatives aux fonctionnalit�s propos�s par l'application. Le coeur de JNotes est la classe Function va regrouper par le biais du pattern command toutes les d�clarations de m�thodes pour chaque commande. 
- le package "exception" contient toutes les exceptions lanc�es par l'application.

Patterns
--

Singleton
-

Le singleton est utilis� pour la classe App.
Le main est unique, ainsi l'utilisation du pattern singleton permet de facilement cr�er cette unicit�. 

Observer
-

Nous avons utilis� le pattern observer lors de l'�dition, la suppression d'une note ou bien lors de la modification de param�tres puisque le fichier index.adoc doi �tre mis � jour. Cette fonctionnalit� montre que le pattern observer est le plus appropri� car on reformera index.adoc � chaque mise � jour des notes. 

Command
-

Le pattern command est le coeur de l'application. Il permet de maintenir facilement les diff�rentes commandes propos�s par JNotes. En effet, chaque fonctionnalit� (Delete, Edit, Index, Listing, Param, Search, Update, View) est d�clar� dans une classe mais c'est la classe principale du pattern command (Function) qui les g�re toutes. Ainsi, 

Iterator
-

Afin de pouvoir trier correctement les notes selon les contextes, les projets ou m�me selon la date, il a �t� utile d'utiliser le pattern iterator. Cela a permis de trier 
facilement les notes selon un crit�re. 

Builder
-

Note.java utilise le pattern builder.  
Une note est d�termin� par son titre mais sa date, l'auteur, le projet et le contexte sont optionels.
Ainsi le pattern builder permet d'impl�menter ces attributs sous la forme d'options facilement.
De plus il y a de nombreux param�tres, ainsi le pattern builder permet d'�viter une multiplication du nombre de 
param�tres utilis�s.


