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
		apC.dlKeys(data);
		
		Map<String, String> map = new HashMap<String,String>();
		Set<String> lsK = new HashSet<String>();
		map = apC.getKeys();
		while(map.isEmpty()){
			map = apC.getKeys();
		}
		
		lsK = map.keySet();
		System.out.println(lsK.size());
		Iterator<String> it = lsK.iterator();
		
		/*while(it.hasNext()){
			String key = it.next();
			
			System.out.println("Key : " + key + ". Titre : " + map.get(key));
		}*/
		
		Thread[] threads = new Thread[lsK.size()];
		apC.dlJsonString(threads, lsK);
		for (Thread thread : threads) {
		    try {
				thread.join();//Attendre que tous les Threads soient terminées
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ArrayList<String> lstt = apC.getList();
		Iterator<String> itJ = lstt.iterator();
		int i = 1;
		while(itJ.hasNext()){
			BufferedWriter output = null;
	        try {
	            File file = new File(i+".json");
	            output = new BufferedWriter(new FileWriter(file));
	            output.write(itJ.next());
	        } catch ( IOException e ) {
	            e.printStackTrace();
	        } finally {
	          if ( output != null ) {
	            try {
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          }
	        }
	        i++;
			//System.out.println(itJ.next());
		}
		
		System.out.println(lstt.size());
		
	}

}
