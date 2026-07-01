package fr.eni.ecole.projet.encheres.dal;

import java.util.List;

import fr.eni.ecole.projet.encheres.bo.ArticleAVendre;

public interface EnchereDAO {
	List<ArticleAVendre> findEncheresOuvertes();
    List<ArticleAVendre> findMesEncheresEnCours(String pseudo);
    List<ArticleAVendre> findMesEncheresGagnees(String pseudo);
    
    void save(Long noArticle, String pseudo, int montant);

}
