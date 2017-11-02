/********************************************************************
 * 						Classe Controler.							*
 * 			Cette classe est le moteur du programme.				*
 * 		Permet d'instancier les différents objets du programme		*
 * 		et de faire la passerlle entre les différentes classes.		*
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
package controler;

import connexion.ApiConnect;
import utils.Matrice;

public class Controler {
	
	private ApiConnect apC;
	private Matrice mat; 
	
	/**
	 * Constructeur de la classe.
	 */
	public Controler(){
		apC = new ApiConnect();
		mat = new Matrice();
	}
	
	
	// --- Test en mode standalone --
	/**
	 * Procédure qui permet de faire des tests en mode standalone cette
	 * procédure sera supprimée une fois le programme complet terminé.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
