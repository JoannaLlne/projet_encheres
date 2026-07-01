package fr.eni.ecole.projet.encheres.bll.contexte;

import fr.eni.ecole.projet.encheres.bo.Utilisateur;

public interface ContexteService {
	Utilisateur charger(String pseudo);

}
