package utils;

public abstract class Message {
	/*
	public static final String INTRO = ConsoleColors.YELLOW + "----------------------------------------------------------\n"
			+ ConsoleColors.YELLOW_UNDERLINED + "Matrix Synthesize Wiki\n\n" + ConsoleColors.RESET
			+ ConsoleColors.YELLOW + "Permet de créer une matrice de comparaison\n"
			+ "suivant des mots recherchés.\n" + "Le programme génère un fichier CSV d'éléments qu'on souhaite\n"
			+ "comparer. (Vous devez être connecté sur internet)"
			+ "Programme développé par M1 MIAGE Groupe 4 (2017-2018).\n\n" + ConsoleColors.RESET + ConsoleColors.YELLOW
			+ "----------------------------------------------------------\n" + ConsoleColors.WHITE;
	
	public static final String ENTERC = ConsoleColors.YELLOW+"Veuillez entrez un critère de recherche : " + ConsoleColors.GREEN;
	
	public static final String PATIENT = ConsoleColors.YELLOW + "Veuillez patienter !" + ConsoleColors.WHITE;
	
	public static final String CHOIXK = ConsoleColors.YELLOW + "Veuillez choisir un résultat parmi "
			+ "cette liste avec l'id ( " + ConsoleColors.PURPLE + "0 , 1 , " + ConsoleColors.YELLOW
			+ "etc..). Ou taper " + ConsoleColors.PURPLE + "'q'" + ConsoleColors.YELLOW + " pour quitter."
			+ ConsoleColors.GREEN;
			*/
	public static final String INTRO = "----------------------------------------------------------\n"
			+ "Matrix Synthesize Wiki\n\n"
			+ "Permet de créer une matrice de comparaison\n"
			+ "suivant des mots recherchés.\n" + "Le programme génère un fichier CSV d'éléments qu'on souhaite\n"
			+ "comparer. (Vous devez être connecté sur internet). "
			+ "Programme développé par M1 MIAGE Groupe 4 (2017-2018).\n\n"
			+ "----------------------------------------------------------\n";
	
	public static final String ENTERC = "Veuillez entrez un critère de recherche : ";
	
	public static final String PATIENT = "Veuillez patienter !";
	
	public static final String CHOIXK = "\nVeuillez choisir un résultat parmi "
			+ "cette liste avec l'id ( 0 , 1 , etc..). Ou taper 'q' pour quitter.";
}
