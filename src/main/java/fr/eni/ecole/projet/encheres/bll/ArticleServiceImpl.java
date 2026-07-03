package fr.eni.ecole.projet.encheres.bll;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import fr.eni.ecole.projet.encheres.bo.ArticleAVendre;
import fr.eni.ecole.projet.encheres.dal.ArticleDAO;
import fr.eni.ecole.projet.encheres.dal.ArticleVendu;

@Service
	public class ArticleServiceImpl implements ArticleService {
	
	private JdbcTemplate jdbcTemplate;
	 private ArticleDAO articleDAO;

	 public ArticleServiceImpl(JdbcTemplate jdbcTemplate, ArticleDAO articleDAO) {
		    this.jdbcTemplate = jdbcTemplate;
		    this.articleDAO = articleDAO;
		}
	 
	 @Override
	    public List<ArticleAVendre> findAll(String nomArticle, Long categorieId) {
	        return articleDAO.findAll(nomArticle, categorieId);
	    }

	    @Override
	    public ArticleAVendre findById(Long id) {
	        return articleDAO.findById(id);
	    }

	    @Override
	    public Long save(ArticleAVendre article, Long categorieId, Long adresseId, String pseudoVendeur) {
	        return articleDAO.save(article, categorieId, adresseId, pseudoVendeur);
	    }

	    @Override
	    public void updatePhoto(Long articleId, String filename) {
	        articleDAO.updatePhoto(articleId, filename);
	    }

	    @Override
	    public List<ArticleAVendre> findMesVentesEnCours(String pseudo) {
	        return articleDAO.findMesVentesEnCours(pseudo);
	    }

	    @Override
	    public List<ArticleAVendre> findMesVentesNonDebutees(String pseudo) {
	        return articleDAO.findMesVentesNonDebutees(pseudo);
	    }

	    @Override
	    public List<ArticleAVendre> findMesVentesTerminees(String pseudo) {
	        return articleDAO.findMesVentesTerminees(pseudo);
	    }
	    
	    @Override
	    public List<ArticleAVendre> findWithFilters(String nomArticle, Long categorieId, String pseudo, String typeFiltre, String statutAchat, String statutVente) {
	        return articleDAO.findWithFilters(nomArticle, categorieId, pseudo, typeFiltre, statutAchat, statutVente);
	    }
	}


