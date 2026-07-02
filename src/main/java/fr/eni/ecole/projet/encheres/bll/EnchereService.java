package fr.eni.ecole.projet.encheres.bll;

import java.util.List;

import fr.eni.ecole.projet.encheres.bo.ArticleAVendre;

public interface EnchereService {
	List<ArticleAVendre> findEncheresOuvertes();
    List<ArticleAVendre> findMesEncheresEnCours(String pseudo);
    List<ArticleAVendre> findMesEncheresGagnees(String pseudo);
    
    void faireEnchere(Long noArticle, String pseudo, int montant);

}
