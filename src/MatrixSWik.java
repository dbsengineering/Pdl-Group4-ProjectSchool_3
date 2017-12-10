/********************************************************************
 * 						Classe MatrixSWik.							*
 * 					Départ de l'application. 						*
 * 		Cette classe est le client dans le pattern command.			*
 * 	Permet d'initialiser l'IHM, le receveir, les commandes et 		*
 * 	l'observer.														*
 * 			Le programme permet de récupérer des données			*
 * 			sur wikidata et wikipédia suivant plusieurs critères 	*
 * 			que l'utilisateur donne. Ensuite le programme tri les 	*
 * 			données pertinentes et crée une matrice de				*
 * 			comparaison en format CSV. Ce fichier permet de faire 	*
 * 			des comparaison sur Open Compare						*
 *																	*
 *		School : .......... Istic									*
 *		Formation : ....... Master 1 MIAGE							*
 *		Lecture : ......... PDL										*
 *		Group : ........... 4										*
 *		Project : ......... 3										*
 *		Authors : ......... Cavron Jérémy, Makroum Siham,			*
 *							Aqasbi Ouahi Mohammed, 					*
 *							Lachkar Anas, El Baza Yassine			*
 *		DateStart : ....... 12/10/2017								*
 *		DateModify : ...... 03/10/2017								*
 *******************************************************************/


import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author CavronJ
 *
 */
public class MatrixSWik {

	// --- Déclaration des propriétéss ---
	private String word;
	private int nbResult;
	private int numKey;
	private Scanner scanInput; // Scanner pour une saisie

	private HashMap<String, String> mapIdProperties;
	// private Set<String> lstKeys = new HashSet<String>();

	/**
	 * Constructeur de la classe.
	 */
	public MatrixSWik() {
		this.mapIdProperties = new HashMap<String, String>();
		this.depart();
	}

	/**
	 * Procédure de départ de l'application.
	 */
	private void depart() {

		this.word = "";
		this.nbResult = 0;
		this.scanInput = new Scanner(System.in);

		String urlWbSearch = "https://www.wikidata.org/w/api.php?action=wbsearchentities&language=fr&format=json";
		String urlEntityData = "https://www.wikidata.org/wiki/Special:EntityData/";
		String type = "&search=";
		String limit = "&limit=";
		HashMap<String, String> mapKeyQ = new HashMap<String, String>();
		JSONObject jsonObj = null;
		String headerCsv = "Titre,Description";
		String dataCsv = "";
		String titleMatrix = "Export-Matrice";
		Set<String>lstSearch = new HashSet<String>();

		System.out.println(utils.Message.INTRO);// Intro message...

		// Boucle sur la recherche de critères et choix de résultats
		do {
			System.out.println(utils.Message.ENTERC);// Entrez un mot...

			// Entrer un mot à rechercher
			this.word = scanInput.next();
			lstSearch.add(this.word);

			// Entrer un nombre pour savoir combien de résultats à afficher
			while (this.nbResult <= 0) {
				System.out.println("\nCombien de résultats voulez-vous ?");
				try {
					this.nbResult = scanInput.nextInt();
				}catch(Exception e) {
					this.nbResult = -1;
				}
			}

			connexion.GestionnaireAPI wikidataAPI = null;
			JSONArray jsonArraySearch = null;

			System.out.println(utils.Message.PATIENT);// Patientez..

			// Boucle sur la récupération des élements de wikidata recherchés
			while (jsonArraySearch == null) {
				try {
					wikidataAPI = new connexion.GestionnaireAPI(urlWbSearch,
							type + URLEncoder.encode(this.word, "UTF-8") + limit + this.nbResult);
					String stringResult = wikidataAPI.getJSON();
					jsonObj = new JSONObject(stringResult);
					jsonArraySearch = ((JSONArray) jsonObj.get("search"));
					if (jsonArraySearch.length() == 0) {
						jsonArraySearch = null;
					}
				} catch (IOException | JSONException e2) {
					e2.printStackTrace();
				}
				if (jsonArraySearch == null)
					System.out.println("Aucun résultat trouvé pour le critère : " + this.word
							+ ", veuillez saisir un nouveau critère : ");
			}

			// Afficher les résultats des mots recherchés
			System.out.println("\nRésultat(s) pour '" + word + "' : ");
			HashMap<Integer, String[]> mapKeyTemp = new HashMap<Integer, String[]>();

			try {
				for (int i = 0; i < jsonArraySearch.length(); i++) {
					JSONObject item = jsonArraySearch.getJSONObject(i);

					String display = i + " - " + (String) item.get("title");

					// Ajout des clés dans la map
					String[] idLabel = { (String) item.get("id"), (String) item.get("label") };
					mapKeyTemp.put(i, idLabel);

					display += " ⇨  " + (String) item.get("label") + " : ";

					if (!item.isNull("description")) {
						display += item.get("description");
					} else {
						display += "Not description";
					}

					System.out.println(display);
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			// Boucle tant qu'on entre pas un chiffre entre 0 et n (size map)
			do {
				// choix des clés
				System.out.println(utils.Message.CHOIXK);
				try {
					this.numKey = scanInput.nextInt();
					mapKeyQ.put(mapKeyTemp.get(this.numKey)[0], mapKeyTemp.get(this.numKey)[1]);
				}catch(Exception e) {
					this.numKey = -1;
				}
				

			} while (this.numKey < 0 && this.numKey > mapKeyTemp.size());

			System.out.println("\nVoulez vous continuer ? O/N");
			this.word = scanInput.next();
			
			this.nbResult = 0;//remise à zéro du nombre de résultats à rechercher

		} while (!this.word.toUpperCase().equals("N"));// On reste dans la boucle tant qu'on ne rentre pas 'N'

		scanInput.close();

		// Téléchargement
		System.out.println("Téléchargement des champs à comparer. Veuillez patientez...");

		connexion.GestionnaireAPI wikidataAPI = null;
		JSONObject jsonObjEntities = null;

		//HashMap<String,String> mapEntity = new HashMap<String, String>();
		HashMap<Integer, ArrayList<String>> mapProperties = new HashMap<Integer, ArrayList<String>>();// Map des numéro
																										// de propriétés

		Set<String> lst = new HashSet<String>();
		lst = mapKeyQ.keySet();
		
		//Récupération des propriétés identiques des listes
		mapProperties = getLstProperties(lst, urlEntityData);
		// -------------------------------------------------------------------------
		// Création liste d'éléments semblables
		ArrayList<String> lstProperties = new ArrayList<String>();
		lstProperties = getIdentiLst(mapProperties);

		
		// -------------------------------------------------------------------------
		System.out.println("\n" + lstProperties.size() + " Propriétées comparables entre les critères recherchés."
				+ "\nTéléchargement en cours. Patientez...");

		// Téléchargement et affichage es noms de propriétés

		Iterator<String> itLstIdProp = lstProperties.iterator();

		while (itLstIdProp.hasNext()) {
			String idProp = itLstIdProp.next();
			try {

				// Récupération des noms des propriétés
				JSONObject jsonObjProp = null;
				JSONObject jsonObjEntitiesProp = null;
				JSONObject jsonObjPProp = null;
				JSONObject jsonObjLabelProp = null;
				JSONObject jsonObjLabFrProp = null;
				wikidataAPI = new connexion.GestionnaireAPI(urlEntityData,
						URLEncoder.encode(idProp, "UTF-8") + ".json");
				String stringResultProp = wikidataAPI.getJSON();
				jsonObjProp = new JSONObject(stringResultProp);

				// Récupération de l'entité propriété
				jsonObjEntitiesProp = (JSONObject) jsonObjProp.get("entities");

				jsonObjPProp = (JSONObject) jsonObjEntitiesProp.get((String) jsonObjEntitiesProp.names().get(0));

				jsonObjLabelProp = (JSONObject) jsonObjPProp.get("labels");
				jsonObjLabFrProp = (JSONObject) jsonObjLabelProp.get("fr");

				mapIdProperties.put(idProp, (String)jsonObjLabFrProp.get("value"));
				headerCsv += ","+ (String)jsonObjLabFrProp.get("value");

			} catch (IOException | JSONException e2) {
				e2.printStackTrace();
			}
		}
		// -------------------------------------------------------------------------
		// -------------------------------------------------------------------------
		// -------------------------------------------------------------------------
		
		// Téléchargement des données

		// Boucle sur la récupération des éléments de wikidata recherchés
		Iterator<String> it = lst.iterator();
		while (it.hasNext()) {

			String key = it.next();
			
			
				try {
					JSONObject jsonKey = null;
					JSONObject jsonLabel = null;
					JSONObject jsonSubLabel = null;
					JSONObject jsonDescription = null;
					JSONObject jsonSubDesc = null;
					JSONObject jsonProps = null;
					

					wikidataAPI = new connexion.GestionnaireAPI(urlEntityData,
							URLEncoder.encode(key, "UTF-8") + ".json");
					String stringResult = wikidataAPI.getJSON();
					jsonObj = new JSONObject(stringResult);

					// Récupération de l'entité
					jsonObjEntities = (JSONObject) jsonObj.get("entities");

					// Récupération du subEntity
					jsonKey = (JSONObject) jsonObjEntities.get(key);

					// Récupération du label
					jsonLabel = (JSONObject) jsonKey.get("labels");
					jsonSubLabel = (JSONObject) jsonLabel.get("fr");
					dataCsv += jsonSubLabel.getString("value");
					System.out.println("\n"+jsonSubLabel.getString("value")+"\n");

					// Récupération de la description
					jsonDescription = (JSONObject) jsonKey.get("descriptions");
					jsonSubDesc = (JSONObject) jsonDescription.get("fr");
					dataCsv += ","+jsonSubDesc.getString("value");


					// Récupération des propriétés
					jsonProps = (JSONObject) jsonKey.get("claims");

					
					/// ------------------------------------------------------------------------------
					/// ------------------------------------------------------------------------------
					itLstIdProp = lstProperties.iterator();

					while (itLstIdProp.hasNext()) {
						
						JSONArray jsonArrProps = null;
						JSONObject jsonData = null;
						JSONObject jsonMainSnak = null;
						JSONObject jsonDataValue = null;
						JSONObject jsonValue = null;
						
						String IdProp = itLstIdProp.next();
						
						jsonArrProps = (JSONArray) jsonProps.get(IdProp);
						jsonData = (JSONObject) jsonArrProps.getJSONObject(0);
						jsonMainSnak = (JSONObject) jsonData.get("mainsnak");
						
						String value = "";
						

						
						//Si on a le noeud datavalue
						if(jsonMainSnak.has("datavalue")) {
							jsonDataValue = (JSONObject) jsonMainSnak.get("datavalue");
							
							if(jsonDataValue.has("value")) {
								
								//Action suivant les types récupéré
								switch((String)jsonDataValue.get("type")) {
									case "string":
										value = String.valueOf(jsonDataValue.get("value"));
										break;
										
									case "wikibase-entityid":
										jsonValue = (JSONObject)jsonDataValue.get("value");
										if(jsonValue.has("numeric-id")) {
											value = String.valueOf(jsonValue.get("numeric-id"));
										}
										break;
										
									case "time":
										jsonValue = (JSONObject)jsonDataValue.get("value");
										if(jsonValue.has("time")) {
											value = String.valueOf(jsonValue.get("time"));
										}
										break;
									case "globecoordinate":
										jsonValue = (JSONObject)jsonDataValue.get("value");
										if(jsonValue.has("latitude") && jsonValue.has("longitude")) {
											value += "lat : "+String.valueOf(jsonValue.get("latitude")) 
											+ " / lon : "+String.valueOf(jsonValue.get("longitude"));
										}
										break;
									case "monolingualtext":
										jsonValue = (JSONObject)jsonDataValue.get("value");
										if(jsonValue.has("text")) {
											value = String.valueOf(jsonValue.get("text"));
										}
										break;
									case "multilingualtext":
										jsonValue = (JSONObject)jsonDataValue.get("value");
										if(jsonValue.has("text")) {
											value = String.valueOf(jsonValue.get("text"));
										}
										break;
									case "quantity":
										jsonValue = (JSONObject)jsonDataValue.get("value");
										if(jsonValue.has("amount")) {
											value = String.valueOf(jsonValue.get("amount"));
										}
										break;
									default:
										value = "0";
		
								}
		
								System.out.println(IdProp + " : " + mapIdProperties.get(IdProp) + " : "+ value);
							
								dataCsv += "," + value;
							}
						}
					}
					
					
					/// ------------------------------------------------------------------------------
					/// ------------------------------------------------------------------------------
					/// ------------------------------------------------------------------------------
				
				} catch (IOException | JSONException e2) {
					e2.printStackTrace();
				}
				dataCsv += "\n";
			}

			// Traitement
			System.out.println("\nCréation du fichier CSV en cours...");
			
			headerCsv += "\n";
			createfile.GestionnaireCSV gestionnaire = new createfile.GestionnaireCSV();
			try{
				gestionnaire.addHeader(titleMatrix, headerCsv, dataCsv);
				System.out.println("##################################################");
				System.out.println("Génération du csv '" + titleMatrix + ".csv' OK");
			} catch (Exception e){
				System.out.println("\nErreur lors de la génération du CSV");
	}
		// -------------------------------------------------------------------------
		// Fin programme
	}

	/**
	 * Fonction qui peremt de comparer les éléments de plusieurs listes. et de
	 * retourner une lsite d'éléments identiques à toutes les listes.
	 * 
	 * @param mapLst : HashMap<Integer, ArrayList<String>>
	 * @return ArrayList<String>
	 */
	private ArrayList<String> getIdentiLst(HashMap<Integer, ArrayList<String>> mapLst) {
		// Récupération de la première liste
		ArrayList<String> common = new ArrayList<String>(mapLst.get(0));
		if(mapLst.size() == 1) {
			return common;
		}

		// Boucle sur la hashMap
		for (ArrayList<String> lst : mapLst.values()) {
			common.retainAll(lst);
		}
		return common;
	}

	/**
	 * Fonction qui retourne une hashmap de listes des propriétée des entités.
	 * 
	 * @param lstKey : SetList des id des entités
	 * @param urlEntityData : url pour télécharger les fichiers json
	 * @return HashMap<Integer, ArrayList<String>>
	 */
	private HashMap<Integer, ArrayList<String>> getLstProperties(Set<String> lstKey, String urlEntityData){
		Iterator<String> it = lstKey.iterator();
		HashMap<Integer, ArrayList<String>> mapProperties = new HashMap<Integer,ArrayList<String>>();
		int k = 0;

		// Boucle sur la récupération des éléments de wikidata recherchés
		while (it.hasNext()) {

			String key = it.next();
			System.out.println(key);
			ArrayList<String> lstNameProp = new ArrayList<String>();
			JSONObject jsonObjEntities = null;
			JSONObject jsonObj = null;

			while (jsonObjEntities == null) {
				try {
					JSONObject jsonKey = null;
					JSONObject jsonProps = null;
					connexion.GestionnaireAPI wikidataAPI = null;

					wikidataAPI = new connexion.GestionnaireAPI(urlEntityData,
							URLEncoder.encode(key, "UTF-8") + ".json");
					String stringResult = wikidataAPI.getJSON();
					jsonObj = new JSONObject(stringResult);

					// Récupération de l'entité
					jsonObjEntities = (JSONObject) jsonObj.get("entities");

					// Récupération du subEntity
					jsonKey = (JSONObject) jsonObjEntities.get(key);

					// Récupération des propriétés
					jsonProps = (JSONObject) jsonKey.get("claims");

					/// ------------------------------------------------------------------------------
					
					for (int i = 0; i < jsonProps.names().length(); i++) {
						// Création de liste de propriétés
						lstNameProp.add(jsonProps.names().getString(i));
					}
				} catch (IOException | JSONException e2) {
					e2.printStackTrace();
				}
				if (jsonObjEntities == null) {
					System.out.println("Aucun résultat trouvé pour le critère : ");
				}
				//ajouter à la HashMap
				mapProperties.put(k, lstNameProp);
			}
			k++;
		}
		return mapProperties;
	}

	/**
	 * Program Start procedure.
	 * 
	 * @param args
	 *            : arguments
	 */
	public static void main(String[] args) {
		new MatrixSWik();
	}
}
