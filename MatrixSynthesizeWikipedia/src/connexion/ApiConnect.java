/********************************************************************
 * 						Classe ApiConnect.							*
 * 					Permet de se connecter sur 						*
 * 					Wikidata ou Wikipedia.							*
 * 				La classe sert également de passerelle pour 		*
 * 				retourner les informations trouvées en ligne.		*
 * 				Cette classe à principalement 2 paramètres:			*
 * 				Un paramètre qui designe le nom de se qu'on veut	*
 * 				chercher. (exemple : String word; 					*
 * 				et dans l'exemple word = "France")					*
 * 				et un deuxième paramètre qui correspond à la clé.	*
 * 				(exemple : String key; -> Q16....)					*
 * 				elle devra avoir une fonction getKeys() qui 		*
 * 				renvoit la liste des clés trouvées					*
 * 				et retourne également des fichiers JSON				*
 *																	*
 *		School : .......... Istic									*
 *		Formation : ....... Master 1 MIAGE							*
 *		Lecture : ......... PDL										*
 *		Group : ........... 4										*
 *		Project : ......... 3										*
 *		Authors : ......... Cavron Jérémy, Saker Amine, 			*
 *							Makroum Siham, Aqasbi Ouahi Mohammed, 	*
 *							Lachkar Anas, Yassine					*
 *		DateStart : ....... 20/10/2017								*
 *		DateModify : ...... 20/10/2017								*
 *******************************************************************/
package connexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;



public class ApiConnect {
	
	//--- Déclaration des propriétées ---
	private String word; // Mot recherché
	private String key; // Clé recherchée
	private Set<String> lstKeys; // Liste de clée qui sera retournée
	private String arg; // Argument demandé
	
	//pop
	/**
	 * Constructeur de la classe.
	 * 
	 */
	public ApiConnect(){
		
	}

	
	//--- connexion ---
	
	// ici tout ce qu'il faut pour se connecter
	/**
	 * Procédure qui permet de télécharger les données Json à partir d'une liste de clées.
	 * Les données Json sont en forme de chaine de caractère et ajouter dans une liste.
	 * @param lstKeys : est la liste des clées.
	 */
	public ArrayList<String> downloadJsonFiles(Set<String> lstKeys) {
		ArrayList<String> lstJson = new ArrayList<String>();
		Iterator<String> itKeys = lstKeys.iterator();
		
		while(itKeys.hasNext()){
			try {
				String key = itKeys.next();
				URL url = new URL("https://www.wikidata.org/wiki/Special:EntityData/"
				+ key +".json");
				
				BufferedReader in = new BufferedReader(
		        new InputStreamReader(url.openStream()));

		        String inputLine ="";
		        String jsonLine = "";
		        while ((inputLine = in.readLine()) != null){
		            jsonLine += inputLine;
		        }
		        
		        lstJson.add(jsonLine);
		        in.close();
			} catch(IOException e){
				e.printStackTrace();
			}
		}
		if(!lstJson.isEmpty()){
			return lstJson;
		}
		return null;
	}
	
	//--- fin connexion ---
	
	
	
	//--- Test en mode standalone --
	/**
	 * Procédure qui permet de faire des tests en mode standalone
	 * cette procédure sera supprimée une fois le programme complet
	 * terminé.
	 * @param args
	 */
	public static void main(String[] args) throws MalformedURLException{
		Set<String> lst = new HashSet<String>();
		ArrayList<String> lstJ = new ArrayList<String>();
		lst.add("Q1225254");
		lst.add("Q142");
		lst.add("Q162569");
		ApiConnect apC = new ApiConnect();
		lstJ = apC.downloadJsonFiles(lst);
		
		
		//affichage résultat
		Iterator<String> itJ = lstJ.iterator();
		while(itJ.hasNext()){
			System.out.println(itJ.next());
		}
	}

	//--- Setters ---
	
	/**
	 * 
	 * @param search
	 */
	public void setSearch(String search) {
		this.word = search;
	}

	/**
	 * 
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * 
	 * @param arg
	 */
	public void setArg(String arg){
		this.arg = arg;
	}
	
	// --- Getters ---

	
	/**
	 * 
	 */
	public String toString(){
		return "Key : " + this.key
				+ " Search : " + this.word
				+ " arg : " + this.arg;
	}

}
