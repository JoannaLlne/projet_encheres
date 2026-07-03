package fr.eni.ecole.projet.encheres.bll;

import java.util.List;
import fr.eni.ecole.projet.encheres.bo.Categorie;

public interface CategorieService {
    List<Categorie> findAll();
}
