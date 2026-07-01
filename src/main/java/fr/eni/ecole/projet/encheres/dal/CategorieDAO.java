package fr.eni.ecole.projet.encheres.dal;

import java.util.List;
import fr.eni.ecole.projet.encheres.bo.Categorie;

public interface CategorieDAO {
    List<Categorie> findAll();
}