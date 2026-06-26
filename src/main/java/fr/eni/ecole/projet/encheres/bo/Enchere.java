package fr.eni.ecole.projet.encheres.bo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Enchere implements Serializable {
    private static final long serialVersionUID = 1L;

	private LocalDateTime date;
	private int montant;
	
	private ArticleAVendre articleAVendre;
	private Utilisateur acquereur;

	//CONSTRUCTEURS
	public Enchere() {
	}

	public Enchere(LocalDateTime date, int montant, ArticleAVendre articleAVendre, Utilisateur acquereur) {
		this.date = date;
		this.montant = montant;
		this.articleAVendre = articleAVendre;
		this.acquereur = acquereur;
	}

	//GETTERS & SETTERS
	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public int getMontant() {
		return montant;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

	public ArticleAVendre getArticleAVendre() {
		return articleAVendre;
	}

	public void setArticleAVendre(ArticleAVendre articleAVendre) {
		this.articleAVendre = articleAVendre;
	}

	public Utilisateur getAcquereur() {
		return acquereur;
	}

	public void setAcquereur(Utilisateur acquereur) {
		this.acquereur = acquereur;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Enchere [date=");
		builder.append(date);
		builder.append(", montant=");
		builder.append(montant);
		builder.append(", articleAVendre=");
		builder.append(articleAVendre);
		builder.append(", acquereur=");
		builder.append(acquereur);
		builder.append("]");
		return builder.toString();
	}
}
