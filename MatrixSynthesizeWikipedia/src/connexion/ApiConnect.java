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
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Set;

public class ApiConnect {
	
	//--- Déclaration des propriétées ---
	private String word; // Mot recherché
	private String key; // Clé recherchée
	private Set<String> lstKeys; // Liste de clée qui sera retournée
	private String arg; // Argument demandé
	
	
	/**
	 * Constructeur de la classe.
	 * @param arg : est la clée recherchée.
	 */
	public ApiConnect(String arg){
		this.arg = arg;
	}

	
	//--- connexion ---
	
	// ici tout ce qu'il faut pour se connecter
	public void runKey() {
		try {
			URL oracle = new URL("https://www.wikidata.org/wiki/Special:EntityData/"+this.arg+".json");
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(oracle.openStream()));

	        String inputLine;
	        while ((inputLine = in.readLine()) != null)
	            System.out.println(inputLine);
	        in.close();
		}
		catch(UnknownHostException ex) {
			ex.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//--- fin connexion ---
	
	
	
	//--- Test en mode standalone --
	/**
	 * Procédure qui permet de faire des tests en mode standalone
	 * cette procédure sera supprimée une fois le programme complet
	 * terminé.
	 * @param args
	 */
	public static void main(String[] args){
		new ApiConnect("Q142").runKey();
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
