package fr.eni.ecole.projet.encheres.bo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class ArticleAVendre implements Serializable {
    private static final long serialVersionUID = 1L;
	
	private long id;
	
	@NotBlank
	private String nom;
	
	@NotBlank
	private LocalDate dateDebutEncheres;
	
	@NotBlank
	private LocalDate dateFinEncheres;
	
	private int statut;
	private String description;
	
	@NotBlank
	private int prixInitial;
	private Integer prixVente;
	private int meilleureOffre;
	
	private Categorie categorie;
	private Adresse retrait;
	
	private List<Utilisateur> vendeurs= new ArrayList<>();
	private String photo;
	
//CONSTRUCTEURS
	public ArticleAVendre() {
	}

	public ArticleAVendre(long id, String nom, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int statut,
		int prixInitial, int prixVente, Categorie categorie, Adresse retrait, List<Utilisateur> vendeurs) {
	this.id = id;
	this.nom = nom;
	this.dateDebutEncheres = dateDebutEncheres;
	this.dateFinEncheres = dateFinEncheres;
	this.statut = statut;
	this.prixInitial = prixInitial;
	this.prixVente = prixVente;
	this.categorie = categorie;
	this.retrait = retrait;
	this.vendeurs = vendeurs;
}

	//GETTERS & SETTERS
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public LocalDate getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public LocalDate getDateFinEncheres() {
		return dateFinEncheres;
	}

	public void setDateFinEncheres(LocalDate dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public int getStatut() {
		return statut;
	}

	public void setStatut(int statut) {
		this.statut = statut;
	}

	public int getPrixInitial() {
		return prixInitial;
	}

	public void setPrixInitial(int prixInitial) {
		this.prixInitial = prixInitial;
	}

	public Integer getPrixVente() {
	    return prixVente;
	}
	public void setPrixVente(Integer prixVente) {
	    this.prixVente = prixVente;
	}
	
	public int getMeilleureOffre() {
	    return meilleureOffre;
	}
	public void setMeilleureOffre(int meilleureOffre) {
	    this.meilleureOffre = meilleureOffre;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	
	public String getDescription() {
	    return description;
	}

	public void setDescription(String description) {
	    this.description = description;
	}
	
	public Adresse getRetrait() {
		return retrait;
	}

	public void setRetrait(Adresse retrait) {
		this.retrait = retrait;
	}

	public List<Utilisateur> getVendeurs() {
		return vendeurs;
	}

	public void setVendeurs(List<Utilisateur> vendeurs) {
		this.vendeurs = vendeurs;
	}
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ArticleAVendre [id=");
		builder.append(id);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", dateDebutEncheres=");
		builder.append(dateDebutEncheres);
		builder.append(", dateFinEncheres=");
		builder.append(dateFinEncheres);
		builder.append(", statut=");
		builder.append(statut);
		builder.append(", prixInitial=");
		builder.append(prixInitial);
		builder.append(", prixVente=");
		builder.append(prixVente);
		builder.append(", categorie=");
		builder.append(categorie);
		builder.append(", description=");
		builder.append(description);
		builder.append(", retrait=");
		builder.append(retrait);
		builder.append(", photo=");
	    builder.append(photo);
		builder.append("]");
		return builder.toString();
	}

	

	
}
