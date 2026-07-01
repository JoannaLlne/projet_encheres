package fr.eni.ecole.projet.encheres.dal;

import java.util.List;

import fr.eni.ecole.projet.encheres.bo.ArticleAVendre;

public interface ArticleDAO {
	List<ArticleAVendre> findAll(String nomArticle, Long categorieId);
	List<ArticleAVendre> findWithFilters(String nomArticle, Long categorieId, String pseudo, String typeFiltre, String statutAchat, String statutVente);
	List<ArticleAVendre> findMesVentesEnCours(String pseudo);
	List<ArticleAVendre> findMesVentesNonDebutees(String pseudo);
	List<ArticleAVendre> findMesVentesTerminees(String pseudo);

	ArticleAVendre findById(Long id);
	
	Long save(ArticleAVendre article, Long categorieId, Long adresseId, String pseudoVendeur);
	void updatePhoto(Long articleId, String filename);
	void updatePrixVente(Long articleId, int prixVente);
	

}
