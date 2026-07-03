package fr.eni.ecole.projet.encheres.bll;

import java.util.List;

import fr.eni.ecole.projet.encheres.bo.ArticleAVendre;

public interface ArticleService {
	List<ArticleAVendre> findAll(String nomArticle, Long categorieId);
	List<ArticleAVendre> findMesVentesEnCours(String pseudo);
	List<ArticleAVendre> findMesVentesNonDebutees(String pseudo);
	List<ArticleAVendre> findMesVentesTerminees(String pseudo);
	ArticleAVendre findById(Long id);
	Long save(ArticleAVendre article, Long categorieId, Long adresseId, String pseudoVendeur);
	void updatePhoto(Long articleId, String filename);
	List<ArticleAVendre> findWithFilters(String nomArticle, Long categorieId, String pseudo, String typeFiltre,
			String statutAchat, String statutVente);
	
	}


