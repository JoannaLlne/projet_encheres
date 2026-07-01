package fr.eni.ecole.projet.encheres.dal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.ecole.projet.encheres.bo.ArticleAVendre;
import fr.eni.ecole.projet.encheres.bo.Categorie;
import fr.eni.ecole.projet.encheres.bo.Utilisateur;

@Repository
public class ArticleDAOImpl implements ArticleDAO {

	private JdbcTemplate jdbcTemplate;

	public ArticleDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public ArticleAVendre findById(Long id) {
		String sql = "SELECT a.no_article AS id, a.nom_article AS nom, " + "a.description AS description, "
				+ "a.date_debut_encheres AS dateDebutEncheres, " + "a.date_fin_encheres AS dateFinEncheres, "
				+ "a.statut_enchere AS statut, " + "a.prix_initial AS prixInitial, " + "a.prix_vente AS prixVente, "
				+ "a.photo AS photo, " + "u.pseudo AS pseudo, " + "u.nom AS nomVendeur, "
				+ "u.prenom AS prenomVendeur, " + "c.no_categorie AS categorieId, " + "c.libelle AS categorieLibelle "
				+ "FROM articles_a_vendre a " + "INNER JOIN utilisateurs u ON u.pseudo = a.id_utilisateur "
				+ "INNER JOIN categories c ON c.no_categorie = a.no_categorie " + "WHERE a.no_article = ?";
		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
			ArticleAVendre article = new ArticleAVendre();
			article.setId(rs.getLong("id"));
			article.setNom(rs.getString("nom"));
			article.setDescription(rs.getString("description"));
			article.setDateDebutEncheres(rs.getDate("dateDebutEncheres").toLocalDate());
			article.setDateFinEncheres(rs.getDate("dateFinEncheres").toLocalDate());
			article.setStatut(rs.getInt("statut"));
			article.setPrixInitial(rs.getInt("prixInitial"));
			article.setPrixVente(rs.getObject("prixVente", Integer.class));
			article.setPhoto(rs.getString("photo"));
			Categorie categorie = new Categorie();
			categorie.setId(rs.getLong("categorieId"));
			categorie.setLibelle(rs.getString("categorieLibelle"));
			article.setCategorie(categorie);
			Utilisateur vendeur = new Utilisateur();
			vendeur.setPseudo(rs.getString("pseudo"));
			vendeur.setNom(rs.getString("nomVendeur"));
			vendeur.setPrenom(rs.getString("prenomVendeur"));
			article.setVendeurs(List.of(vendeur));
			return article;
		}, id);

	}

	@Override
	public List<ArticleAVendre> findAll(String nomArticle, Long categorieId) {
		StringBuilder sql = new StringBuilder(
			    "SELECT a.no_article AS id, a.nom_article AS nom, " +
			    "a.date_debut_encheres AS dateDebutEncheres, " +
			    "a.date_fin_encheres AS dateFinEncheres, " +
			    "a.statut_enchere AS statut, " +
			    "a.prix_initial AS prixInitial, " +
			    "a.prix_vente AS prixVente, " +
			    "a.photo AS photo, " +
			    "u.pseudo AS pseudo, " +
			    "u.nom AS nomVendeur, " +
			    "u.prenom AS prenomVendeur, " +
			    "COALESCE(MAX(e.montant_enchere), a.prix_initial) AS meilleureOffre " +
			    "FROM articles_a_vendre a " +
			    "INNER JOIN utilisateurs u ON u.pseudo = a.id_utilisateur " +
			    "LEFT JOIN encheres e ON e.no_article = a.no_article " +
			    "WHERE 1=1"
			);

		List<Object> params = new ArrayList<>();

		if (nomArticle != null && !nomArticle.isBlank()) {
			sql.append(" AND a.nom_article LIKE ?");
			params.add("%" + nomArticle + "%");
		}

		if (categorieId != null) {
			sql.append(" AND a.no_categorie = ?");
			params.add(categorieId);
		}

		return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> {
			ArticleAVendre article = new ArticleAVendre();
			article.setId(rs.getLong("id"));
			article.setNom(rs.getString("nom"));
			article.setDateDebutEncheres(rs.getDate("dateDebutEncheres").toLocalDate());
			article.setDateFinEncheres(rs.getDate("dateFinEncheres").toLocalDate());
			article.setStatut(rs.getInt("statut"));
			article.setPrixInitial(rs.getInt("prixInitial"));
			article.setPrixVente(rs.getObject("prixVente", Integer.class));
			article.setPhoto(rs.getString("photo"));

			Utilisateur vendeur = new Utilisateur();
			vendeur.setPseudo(rs.getString("pseudo"));
			vendeur.setNom(rs.getString("nomVendeur"));
			vendeur.setPrenom(rs.getString("prenomVendeur"));
			article.setVendeurs(List.of(vendeur));

			return article;
		}, params.toArray());
	}

	@Override
	public Long save(ArticleAVendre article, Long categorieId, Long adresseId, String pseudoVendeur) {
		String sql = "INSERT INTO articles_a_vendre (nom_article, description, date_debut_encheres, date_fin_encheres, statut_enchere, prix_initial, no_categorie, no_adresse_retrait, id_utilisateur) "
				+ "VALUES (?, ?, ?, ?, 0, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			var ps = connection.prepareStatement(sql, new String[] { "no_article" });
			ps.setString(1, article.getNom());
			ps.setString(2, article.getDescription());
			ps.setObject(3, article.getDateDebutEncheres());
			ps.setObject(4, article.getDateFinEncheres());
			ps.setInt(5, article.getPrixInitial());
			ps.setLong(6, categorieId);
			ps.setLong(7, adresseId);
			ps.setString(8, pseudoVendeur);
			return ps;
		}, keyHolder);
		return keyHolder.getKey().longValue();
	}

	@Override
	public void updatePhoto(Long articleId, String filename) {
		String sql = "UPDATE articles_a_vendre SET photo = ? WHERE no_article = ?";
		jdbcTemplate.update(sql, filename, articleId);
	}

	@Override
    public List<ArticleAVendre> findMesVentesEnCours(String pseudo) {
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
                "WHERE a.id_utilisateur = ? AND a.date_debut_encheres <= CAST(GETDATE() AS DATE) AND a.date_fin_encheres >= CAST(GETDATE() AS DATE) AND a.statut_enchere != 100";
        
        return jdbcTemplate.query(sql, new ArticleVendu(), pseudo);
    }

	@Override
	public List<ArticleAVendre> findMesVentesNonDebutees(String pseudo) {
		String sql = "SELECT a.no_article AS id, a.nom_article AS nom, "
				+ "a.date_debut_encheres AS dateDebutEncheres, " + "a.date_fin_encheres AS dateFinEncheres, "
				+ "a.statut_enchere AS statut, " + "a.prix_initial AS prixInitial, " + "a.prix_vente AS prixVente, "
				+ "a.photo AS photo, " + "u.pseudo AS pseudo, u.nom AS nomVendeur, u.prenom AS prenomVendeur "
				+ "FROM articles_a_vendre a " + "INNER JOIN utilisateurs u ON u.pseudo = a.id_utilisateur "
				+ "WHERE a.id_utilisateur = ? AND a.date_debut_encheres > CAST(GETDATE() AS DATE)";
		return jdbcTemplate.query(sql, new ArticleVendu(), pseudo);
	}

	@Override
	public List<ArticleAVendre> findMesVentesTerminees(String pseudo) {
		String sql = "SELECT a.no_article AS id, a.nom_article AS nom, "
				+ "a.date_debut_encheres AS dateDebutEncheres, " + "a.date_fin_encheres AS dateFinEncheres, "
				+ "a.statut_enchere AS statut, " + "a.prix_initial AS prixInitial, " + "a.prix_vente AS prixVente, "
				+ "a.photo AS photo, " + "u.pseudo AS pseudo, u.nom AS nomVendeur, u.prenom AS prenomVendeur "
				+ "FROM articles_a_vendre a " + "INNER JOIN utilisateurs u ON u.pseudo = a.id_utilisateur "
				+ "WHERE a.id_utilisateur = ? AND a.statut_enchere IN (2, 3, 100)";
		return jdbcTemplate.query(sql, new ArticleVendu(), pseudo);
	}

	@Override
	public void updatePrixVente(Long articleId, int prixVente) {
		String sql = "UPDATE articles_a_vendre SET prix_vente = ? WHERE no_article = ?";
	    jdbcTemplate.update(sql, prixVente, articleId);
		
	}
	
	@Override
	public List<ArticleAVendre> findWithFilters(String nomArticle, Long categorieId, String pseudo, 
	        String typeFiltre, String statutAchat, String statutVente) {
	    StringBuilder sql = new StringBuilder(
	        "SELECT a.no_article AS id, a.nom_article AS nom, " +
	        "a.date_debut_encheres AS dateDebutEncheres, " +
	        "a.date_fin_encheres AS dateFinEncheres, " +
	        "a.statut_enchere AS statut, " +
	        "a.prix_initial AS prixInitial, " +
	        "a.prix_vente AS prixVente, " +
	        "a.photo AS photo, " +
	        "u.pseudo AS pseudo, " +
	        "u.nom AS nomVendeur, " +
	        "u.prenom AS prenomVendeur, " +
	        "COALESCE(MAX(e.montant_enchere), a.prix_initial) AS meilleureOffre " +
	        "FROM articles_a_vendre a " +
	        "INNER JOIN utilisateurs u ON u.pseudo = a.id_utilisateur " +
	        "LEFT JOIN encheres e ON e.no_article = a.no_article " +
	        "WHERE 1=1"
	    );

	    List<Object> params = new ArrayList<>();

	    // Filtre : nom de l'article
	    if (nomArticle != null && !nomArticle.isBlank()) {
	        sql.append(" AND a.nom_article LIKE ?");
	        params.add("%" + nomArticle + "%");
	    }

	    // Filtre : catégorie
	    if (categorieId != null) {
	        sql.append(" AND a.no_categorie = ?");
	        params.add(categorieId);
	    }

	    // Filtre : achats/ventes
	    if (pseudo != null && "achats".equals(typeFiltre)) {
	        // Achats : articles où l'utilisateur n'est PAS le vendeur
	        sql.append(" AND a.id_utilisateur != ?");
	        params.add(pseudo);
	        
	        // Statut selon le sous-filtre
	        if ("gagnees".equals(statutAchat)) {
	            sql.append(" AND a.statut_enchere = 3"); // Livrée
	        } else if ("encours".equals(statutAchat)) {
	            sql.append(" AND a.statut_enchere = 1"); // En cours
	        } else {
	            // "ouvertes" par défaut
	            sql.append(" AND a.statut_enchere = 1");
	        }
	        
	    } else if (pseudo != null && "ventes".equals(typeFiltre)) {
	        // Ventes : articles où l'utilisateur EST le vendeur
	        sql.append(" AND a.id_utilisateur = ?");
	        params.add(pseudo);
	        
	        // Statut selon le sous-filtre
	        if ("nondebutees".equals(statutVente)) {
	            sql.append(" AND a.date_debut_encheres > CAST(GETDATE() AS DATE)");
	        } else if ("terminees".equals(statutVente)) {
	            sql.append(" AND a.statut_enchere IN (2, 3, 100)");
	        } else {
	            // "encours" par défaut
	            sql.append(" AND a.date_debut_encheres <= CAST(GETDATE() AS DATE) " +
	                      "AND a.date_fin_encheres >= CAST(GETDATE() AS DATE) " +
	                      "AND a.statut_enchere != 100");
	        }
	        
	    } else {
	        // Pas de filtre achats/ventes : affiche les enchères ouvertes (statut 1)
	        sql.append(" AND a.statut_enchere = 1");
	    }
	    
	    sql.append(" GROUP BY a.no_article, a.nom_article, a.date_debut_encheres, " +
	               "a.date_fin_encheres, a.statut_enchere, a.prix_initial, " +
	               "a.prix_vente, a.photo, u.pseudo, u.nom, u.prenom");

	    return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> {
	        ArticleAVendre article = new ArticleAVendre();
	        article.setId(rs.getLong("id"));
	        article.setNom(rs.getString("nom"));
	        article.setDateDebutEncheres(rs.getDate("dateDebutEncheres").toLocalDate());
	        article.setDateFinEncheres(rs.getDate("dateFinEncheres").toLocalDate());
	        article.setStatut(rs.getInt("statut"));
	        article.setPrixInitial(rs.getInt("prixInitial"));
	        article.setPrixVente(rs.getObject("prixVente", Integer.class));
	        article.setPhoto(rs.getString("photo"));
	        article.setMeilleureOffre(rs.getInt("meilleureOffre"));

	        Utilisateur vendeur = new Utilisateur();
	        vendeur.setPseudo(rs.getString("pseudo"));
	        vendeur.setNom(rs.getString("nomVendeur"));
	        vendeur.setPrenom(rs.getString("prenomVendeur"));
	        article.setVendeurs(List.of(vendeur));

	        return article;
	    }, params.toArray());
	}
}