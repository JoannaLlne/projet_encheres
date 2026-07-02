package fr.eni.ecole.projet.encheres.bll;

import java.util.List;
import fr.eni.ecole.projet.encheres.bo.Adresse;
import fr.eni.ecole.projet.encheres.bo.Utilisateur;

public interface UtilisateurService {

	void modifierProfil(Utilisateur utilisateur);

	List<Utilisateur> consulterProfil();
	
	Utilisateur consulterProfilByPseudo(String pseudo);
	
	void publierUtilisateur(Utilisateur utilisateur);
	void publierAdresse(Adresse adresse);

	
}
