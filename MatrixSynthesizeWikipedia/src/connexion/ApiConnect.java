/********************************************************************
 * 						Classe ApiConnect.							*
 * 					Permet de se connecter sur 						*
 * 					Wikidata ou Wikipedia.							*
 * 				La classe sert également de passerelle pour 		*
 * 				retourner les informations trouvées en ligne.		*
 * 				Cette classe à principalement 2 paramètres:			*
 * 				Un paramètre qui designe le nom de se qu'on veut	*
 * 				chercher. (exemple : String search; 				*
 * 				et dans l'exemple search = "France")				*
 * 				et un deuxième paramètre qui correspond à la clé.	*
 * 				(exemple : String key; -> Q16....)					*
 * 				elle devra avoir une fonction getKeys() qui 		*
 * 				renvoit la liste des clés trouvées					*
 * 				et 2 setters (voir méthodes)						*
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

import java.util.Set;

public class ApiConnect {
	
	//--- Déclaration des propriétées ---
	private String search; // Mot recherché
	private String key; // Clé recherchée
	private Set<String> lstKeys; // Liste de clée qui sera retournée
	
	/**
	 * Constructeur de la classe.
	 */
	public ApiConnect(){
		
	}
	
	//--- connexion ---
	

	//--- Setters ---
	
	/**
	 * 
	 * @param search
	 */
	public void setSearch(String search) {
		this.search = search;
	}

	/**
	 * 
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	// --- Getters ---

	/**
	 * 
	 * @return
	 */
	public String getSearch() {
		return this.search;
	}

	/**
	 * 
	 * @return
	 */
	public String getKey() {
		return this.key;
	}
	
	/**
	 * 
	 */
	public String toString(){
		return "Key : " + this.key
				+ "Search : " + this.search;
	}

}
