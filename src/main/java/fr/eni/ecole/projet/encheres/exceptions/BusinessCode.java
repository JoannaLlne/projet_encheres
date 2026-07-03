package fr.eni.ecole.projet.encheres.exceptions;

public class BusinessCode {
	
	// BO UTILISATEUR
	public static final String VALIDATION_UTILISATEUR_PSEUDO_UNIQUE= "validation.utilisateur.pseudo.unique";
	public static final String VALIDATION_UTILISATEUR_PSEUDO_BLANK= "validation.utilisateur.pseudo.blank";
	public static final String VALIDATION_UTILISATEUR_NOM_BLANK= "validation.utilisateur.nom.blank";
	public static final String VALIDATION_UTILISATEUR_PRENOM_BLANK= "validation.utilisateur.prenom.blank";
	public static final String VALIDATION_UTILISATEUR_EMAIL_BLANK= "validation.utilisateur.email.blank";
	public static final String VALIDATION_UTILISATEUR_PWD_BLANK= "validation.utilisateur.pwd.blank";
	public static final String VALIDATION_UTILISATEUR_PWD_LENGTH= "validation.utilisateur.pwd.length";
	public static final String VALIDATION_UTILISATEUR_PWD_PATTERN = "validation.utilisateur.pwd.pattern";
	
	public static final String BLL_UTILISATEUR_UPDATE_ERREUR ="bll.utilisateur.update.erreur";
}
