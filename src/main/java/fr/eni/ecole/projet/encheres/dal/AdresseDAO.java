package fr.eni.ecole.projet.encheres.dal;

import java.util.List;

import fr.eni.ecole.projet.encheres.bo.Adresse;
import fr.eni.ecole.projet.encheres.bo.Utilisateur;

public interface AdresseDAO {
	List<Adresse> findAll();
	void createAddress(Adresse adresse);

}
