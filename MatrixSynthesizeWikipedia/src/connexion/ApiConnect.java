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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONObject;


public class ApiConnect extends Thread {
	
	//--- Déclaration des propriétées ---
	private String word; // Mot recherché
	private String key; // Clé recherchée
	private Set<String> lstKeys; // Liste de clée qui sera retournée
	private ArrayList<String> lstJson;
	private String arg; // Argument demandé
	
	//static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	
	//pip
	/**
	 * Constructeur de la classe.
	 * 
	 */
	public ApiConnect(){
		this.lstJson = new ArrayList<String>();
	}

	public synchronized void ajout(String jsonString){
		this.lstJson.add(jsonString);
	}
	
	
	public void dlKeys(Thread[] threads, Set<String> lstWords){
		Iterator<String> itWords = lstWords.iterator();
		int i = 0;
		while(itWords.hasNext()){
			String word = itWords.next();
			threads[i] = new Thread(new Runnable(){
				public void run(){
					
				}
			});
		}
	}
	
	/**
	 * 
	 * @param threads
	 * @param lstKeys
	 */
	public void dlJsonString(Thread[] threads, Set<String> lstKeys){
		Iterator<String> itKeys = lstKeys.iterator();
		int i = 0;
		while (itKeys.hasNext()) {
			String key = itKeys.next();
			threads[i] = new Thread(new Runnable() {
				public void run() {
					String url = "https://www.wikidata.org/wiki/Special:EntityData/" + key + ".json";
					try {
						URL obj = new URL(url);
						HttpURLConnection con = (HttpURLConnection) obj.openConnection();
						// optional default is GET
						con.setRequestMethod("GET");
						// add request header
						con.setRequestProperty("User-Agent", "Mozilla/5.0");
						int responseCode = con.getResponseCode();
						BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
						String inputLine;
						StringBuffer response = new StringBuffer();
						while ((inputLine = in.readLine()) != null) {
							response.append(inputLine);
						}
						in.close();
						// print in String
						//notify();
						//System.out.println("notify()");
						//System.out.println(response.toString());
						// Read JSON response and print
						//JSONObject myResponse = new JSONObject(response.toString());
						ajout(response.toString());
					} catch (Exception e) {
						System.out.println("problème de récupération !");
					}
				}
			});
			threads[i].start();
			i++;
		}
	}
	
	//--- Test en mode standalone --
	/**
	 * Procédure qui permet de faire des tests en mode standalone
	 * cette procédure sera supprimée une fois le programme complet
	 * terminé.
	 * @param args
	 */
	public static void main(String[] args) {
		Set<String>lst = new HashSet<String>();
		
		lst.add("Q1225254");
		lst.add("Q142");
		lst.add("Q162569");
		lst.add("Q1917170");
		
		Iterator<String> it = lst.iterator();
		
		ApiConnect apC = new ApiConnect();
		Thread[] threads = new Thread[lst.size()];
		apC.dlJsonString(threads, lst);
		
		for (Thread thread : threads) {
		    try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Iterator<String> itJ = apC.getList().iterator();
		
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
		
		//ApiConnect apC = new ApiConnect();
		
		/*try {
			while(it.hasNext()){
				new GetJson().run(it.next());
				//apC.call_me(it.next());
			}
	        } catch (Exception e) {
	         e.printStackTrace();
	       }*/
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
	public ArrayList<String> getList(){
		return this.lstJson;
	}
	
	/**
	 * 
	 */
	public String toString(){
		return "Key : " + this.key
				+ " Search : " + this.word
				+ " arg : " + this.arg;
	}

}
