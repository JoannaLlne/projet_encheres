package fr.eni.ecole.projet.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import fr.eni.ecole.projet.encheres.bo.ArticleAVendre;
import fr.eni.ecole.projet.encheres.bo.Utilisateur;

public class ArticleVendu implements RowMapper<ArticleAVendre> {
    @Override
    public ArticleAVendre mapRow(ResultSet rs, int rowNum) throws SQLException {
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
    }
}
