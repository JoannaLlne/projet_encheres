package fr.eni.ecole.projet.encheres.dal;

import java.util.List;
import fr.eni.ecole.projet.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
	
	//CREATE READ UPDATE DELETE
	Utilisateur read(String pseudo);
	
	
	void createUtilisateur(Utilisateur utilisateur);
	void deleteUtilisateur(Utilisateur utilisateur);
	
	List<Utilisateur> findAll();
	boolean findPseudo(String pseudo);
	void update(Utilisateur utilisateur);
	
	void modifierCredit(String pseudo, int delta);

}
