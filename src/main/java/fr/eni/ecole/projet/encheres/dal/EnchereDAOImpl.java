package fr.eni.ecole.projet.encheres.dal;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.projet.encheres.bo.ArticleAVendre;

@Repository
public class EnchereDAOImpl implements EnchereDAO {

    private JdbcTemplate jdbcTemplate;

    public EnchereDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ArticleAVendre> findEncheresOuvertes() {
        String sql = "SELECT a.no_article AS id, a.nom_article AS nom, " +
                "a.date_debut_encheres AS dateDebutEncheres, " +
                "a.date_fin_encheres AS dateFinEncheres, " +
                "a.statut_enchere AS statut, " +
                "a.prix_initial AS prixInitial, " +
                "a.prix_vente AS prixVente, " +
                "a.photo AS photo, " +
                "u.pseudo AS pseudo, u.nom AS nomVendeur, u.prenom AS prenomVendeur " +
                "FROM articles_a_vendre a " +
                "INNER JOIN utilisateurs u ON u.pseudo = a.id_utilisateur " +
                "WHERE a.statut_enchere = 1";
        return jdbcTemplate.query(sql, new ArticleVendu());
    }

    @Override
    public List<ArticleAVendre> findMesEncheresEnCours(String pseudo) {
        String sql = "SELECT DISTINCT a.no_article AS id, a.nom_article AS nom, " +
                "a.date_debut_encheres AS dateDebutEncheres, " +
                "a.date_fin_encheres AS dateFinEncheres, " +
                "a.statut_enchere AS statut, " +
                "a.prix_initial AS prixInitial, " +
                "a.prix_vente AS prixVente, " +
                "a.photo AS photo, " +
                "u.pseudo AS pseudo, u.nom AS nomVendeur, u.prenom AS prenomVendeur " +
                "FROM articles_a_vendre a " +
                "INNER JOIN utilisateurs u ON u.pseudo = a.id_utilisateur " +
                "INNER JOIN encheres e ON e.no_article = a.no_article " +
                "WHERE a.statut_enchere = 1 AND e.id_utilisateur = ?";
        return jdbcTemplate.query(sql, new ArticleVendu(), pseudo);
    }

    @Override
    public List<ArticleAVendre> findMesEncheresGagnees(String pseudo) {
        String sql = "SELECT a.no_article AS id, a.nom_article AS nom, " +
                "a.date_debut_encheres AS dateDebutEncheres, " +
                "a.date_fin_encheres AS dateFinEncheres, " +
                "a.statut_enchere AS statut, " +
                "a.prix_initial AS prixInitial, " +
                "a.prix_vente AS prixVente, " +
                "a.photo AS photo, " +
                "u.pseudo AS pseudo, u.nom AS nomVendeur, u.prenom AS prenomVendeur " +
                "FROM articles_a_vendre a " +
                "INNER JOIN utilisateurs u ON u.pseudo = a.id_utilisateur " +
                "INNER JOIN encheres e ON e.no_article = a.no_article " +
                "WHERE a.statut_enchere IN (2, 3) " +
                "AND e.id_utilisateur = ? " +
                "AND e.montant_enchere = (SELECT MAX(e2.montant_enchere) FROM encheres e2 WHERE e2.no_article = a.no_article)";
        return jdbcTemplate.query(sql, new ArticleVendu(), pseudo);
    }

	@Override
	public void save(Long noArticle, String pseudo, int montant) {
		String sql = "INSERT INTO encheres (no_article, id_utilisateur, montant_enchere, date_enchere) VALUES (?, ?, ?, ?)";
	    jdbcTemplate.update(sql, noArticle, pseudo, montant, java.time.LocalDateTime.now());
		
	}
}
