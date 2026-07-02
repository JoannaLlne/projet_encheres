package fr.eni.ecole.projet.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.ecole.projet.encheres.bo.ArticleAVendre;
import fr.eni.ecole.projet.encheres.bo.Utilisateur;
import fr.eni.ecole.projet.encheres.dal.ArticleDAO;
import fr.eni.ecole.projet.encheres.dal.EnchereDAO;
import fr.eni.ecole.projet.encheres.dal.UtilisateurDAO;

@Service
public class EnchereServiceImpl implements EnchereService {

	private EnchereDAO enchereDAO;
	private ArticleDAO articleDAO;
	private UtilisateurDAO utilisateurDAO;

	public EnchereServiceImpl(EnchereDAO enchereDAO, ArticleDAO articleDAO, UtilisateurDAO utilisateurDAO) {
	    this.enchereDAO = enchereDAO;
	    this.articleDAO = articleDAO;
	    this.utilisateurDAO = utilisateurDAO;
	}

    @Override
    public List<ArticleAVendre> findEncheresOuvertes() {
        return enchereDAO.findEncheresOuvertes();
    }

    @Override
    public List<ArticleAVendre> findMesEncheresEnCours(String pseudo) {
        return enchereDAO.findMesEncheresEnCours(pseudo);
    }

    @Override
    public List<ArticleAVendre> findMesEncheresGagnees(String pseudo) {
        return enchereDAO.findMesEncheresGagnees(pseudo);
    }

	@Override
	public void faireEnchere(Long noArticle, String pseudo, int montant) {
	    ArticleAVendre article = articleDAO.findById(noArticle);

	    if (!article.getVendeurs().isEmpty() && pseudo.equals(article.getVendeurs().get(0).getPseudo())) {
	        throw new IllegalArgumentException("Vous ne pouvez pas enchérir sur votre propre article.");
	    }

	    int montantActuel = (article.getPrixVente() != null) ? article.getPrixVente() : article.getPrixInitial();
	    if (montant <= montantActuel) {
	        throw new IllegalArgumentException("Votre offre doit être supérieure à " + montantActuel + " point(s).");
	    }

	    Utilisateur enchérisseur = utilisateurDAO.read(pseudo);
	    if (enchérisseur.getCredit() < montant) {
	        throw new IllegalArgumentException("Crédit insuffisant : vous avez " + enchérisseur.getCredit() + " point(s).");
	    }

	    enchereDAO.save(noArticle, pseudo, montant);
	    articleDAO.updatePrixVente(noArticle, montant);
	    utilisateurDAO.modifierCredit(pseudo, -montant);
		
	}
}
