import java.util.Scanner;

import connexion.ApiConnect;

/********************************************************************
 * 						Classe MatrixSWik							*
 * 					Départ de l'application.						*
 * 				Cette classe permet également l'affichage 			*
 * 			console. En d'autres termes, sert d'interface entre 	*
 * 			l'utilisateur et le programme							*
 *																	*
 *		School : .......... Istic									*
 *		Formation : ....... Master 1 MIAGE							*
 *		Lecture : ......... PDL										*
 *		Group : ........... 4										*
 *		Project : ......... 3										*
 *		Authors : ......... Cavron Jérémy, Saker Amine, 			*
 *							Makroum Siham, Aqasbi Ouahi Mohammed, 	*
 *							Lachkar Anas, Yassine					*
 *		DateStart : ....... 12/10/2017								*
 *		DateModify : ...... 2/11/2017								*
 *******************************************************************/


public class MatrixSWik {
	
	/**
	 * Procédure de démarrage du programme
	 * @param args : arguments venant du terminal. Type liste de chaînes de caractères.
	 */
	public static void main(String[] args){
		String data;
		System.out.println("Veuillez entrez un critère de recherche : ");
		Scanner scanInput = new Scanner(System.in);
		
		data = scanInput.nextLine();
		scanInput.close();
		
		ApiConnect apC = new ApiConnect();
		apC.dlKeys(data, "fr");
		//System.out.println(data);
		
	}

}
