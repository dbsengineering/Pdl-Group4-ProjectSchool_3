
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import connexion.ApiConnect;
import utils.ConsoleColors;

public class MatrixSWik {

	public MatrixSWik() {
		init();
		startProg();
	}

	private void init() {
		System.out.println(Intro());
	}

	private void startProg() {
		String word;
		String key ="";
		String repContinu = "o";

		Scanner scanInput = new Scanner(System.in);
		Map<String, String> map = new HashMap<String, String>();
		Set<String> lsK = new HashSet<String>();
		Set<String> lstKeys = new HashSet<String>();
		ApiConnect apC = new ApiConnect();

		do {
			System.out.println("Veuillez entrez un critère de recherche : " + ConsoleColors.GREEN);
			word = scanInput.nextLine();
			System.out.println(ConsoleColors.YELLOW + "Veuillez patienter !" + ConsoleColors.WHITE);
			if (!word.equals("")) {

				apC.dlKeys(word, "fr");
				// apC.dlKeys(word, "en");
				
				map = apC.getKeys();
				
				while (map.isEmpty()) {
					map = apC.getKeys();
				}
				String[] lstTemp = new String[map.size()];
				lsK = map.keySet();
				Iterator<String> it = lsK.iterator();
				System.out.println("Résultat pour : " + word);
				int i = 0;
				while (it.hasNext()) {
					key = it.next();
					lstTemp[i] = key;
					System.out.println("[ " + ConsoleColors.PURPLE + i + ConsoleColors.WHITE + " ] - " + key + " ⇨ "
							+ map.get(key));
					i++;
				}
				
				//Demander à l'utilisateur qu'elle id il veut comparer
				do{
					System.out.println(ConsoleColors.YELLOW + "Veuillez choisir un résultat parmi "
							+ "cette liste avec l'id ( " + ConsoleColors.PURPLE + "0 , 1 , " + ConsoleColors.YELLOW
							+ "etc..). Ou taper " + ConsoleColors.PURPLE + "'q'" + ConsoleColors.YELLOW + " pour quitter."
							+ ConsoleColors.GREEN);
					key = scanInput.nextLine();
					//Test si la valeur rentrée est une valeur entière et une valeur comprise entre 0 et le nombre de clées
					//disponibles ou une valeur nulle
					
				}while(key.equals(""));
				
				/*}while(key.equals("") || !(Integer.valueOf(key) instanceof Integer) 
						|| Integer.valueOf(key) < 0 || Integer.valueOf(key) > lsK.size()-1);*/
				if(key.equals("q") || key.equals("Q")){
					System.out.println(ConsoleColors.YELLOW+"Au revoir !" + ConsoleColors.WHITE);
					System.exit(0);
				}
				lstKeys.add(lstTemp[Integer.valueOf(key)]);
				
				//Récupération des descriptions
				//int gg = scanInput.nextInt();
				
			} else {
				System.out.println(ConsoleColors.RED + "Vous devez entrer un mot ou un caractère de recherche !"
						+ ConsoleColors.WHITE);
			}
			// Voulez-vous continuer ?
			System.out.println(ConsoleColors.YELLOW + "Voulez vous continuer ?" + ConsoleColors.WHITE);
			repContinu = scanInput.nextLine();
			map.clear();
			lsK.clear();
		} while (!repContinu.equals("n") || repContinu.equals("N"));
		scanInput.close();// Fermeture de la saisie


		Thread[] threads = new Thread[lstKeys.size()];
		apC.dlJsonString(threads, lstKeys);
		for (Thread thread : threads) {
			try {
				thread.join();// Attendre que tous les Threads soient terminées
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		ArrayList<String> lstt = apC.getList();
		Iterator<String> itJ = lstt.iterator();
		int i = 1;
		while (itJ.hasNext()) {
			System.out.println(itJ.next());
			/*
			 * BufferedWriter output = null; try { File file = new
			 * File(i+".json"); output = new BufferedWriter(new
			 * FileWriter(file)); output.write(itJ.next()); } catch (
			 * IOException e ) { e.printStackTrace(); } finally { if ( output !=
			 * null ) { try { output.close(); } catch (IOException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); } } } i++;
			 */
			// System.out.println(itJ.next());
		}

		// System.out.println(lstt.size());
	}

	/**
	 * Fonction message d'intro
	 * 
	 * @return Intro : de type chaîne de caractères.
	 */
	private String Intro() {
		return ConsoleColors.YELLOW + "----------------------------------------------------------\n"
				+ ConsoleColors.YELLOW_UNDERLINED + "Matrix Synthesize Wiki\n\n" + ConsoleColors.RESET
				+ ConsoleColors.YELLOW + "Permet de créer une matrice de comparaison\n"
				+ "suivant des mots recherchés.\n" + "Le programme génère un fichier CSV d'éléments qu'on souhaite\n"
				+ "comparer. (Vous devez être connecté sur internet).\n\n" + ConsoleColors.RESET + ConsoleColors.YELLOW
				+ "----------------------------------------------------------\n" + ConsoleColors.WHITE;
	}

	/**
	 * Procédure de démarrage du programme
	 * 
	 * @param args
	 *            : arguments venant du terminal. Type liste de chaînes de
	 *            caractères.
	 */
	public static void main(String[] args) {
		MatrixSWik wik = new MatrixSWik();
	}

}
