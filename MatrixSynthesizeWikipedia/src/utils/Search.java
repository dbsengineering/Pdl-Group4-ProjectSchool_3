/********************************************************************
 * 						Classe Search.								*
 * 					Permet de trier les différents éléments			*
 * 					trouvés dans les fichiers JSON. 				*
 * 				Elle reçoit 2 paramètres							*
 * 					principaux. 1 pour le mot rechercher			*
 * 					(exemple : String recherche; -> "France")		*
 * 					Et un deuxième pour la clé. (exemple : 			*
 * 					String key; -> "Q16...")						*
 * 					la classe recevera également une liste de clés  *
 * 					et devra posséder un getLstKey() et				*
 * 					un getWord()									*
 * 					Il faut filter les langues, anglais, français	*
 * 					etc...											*
 * 					Cette classe doit retourner une liste			*
 * 					de tous les éléments pertients qu'on trouve 	*
 * 					dans les fichiers JSON							*
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
package utils;

import java.util.Set;

public class Search {
	
	//--- Déclaration des Propriétés ---
	private String search; // Mot recherché
	private String key; // Clé recherchée
	private Set<String> lstKeys; // Liste de clées 
	
	/**
	 * Constructeur de la classe
	 * @param search : mot recherché. De type String.
	 */
	public Search(String search){
		
	}

}
