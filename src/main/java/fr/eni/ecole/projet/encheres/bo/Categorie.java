package fr.eni.ecole.projet.encheres.bo;

import java.io.Serializable;

public class Categorie implements Serializable {
    private static final long serialVersionUID = 1L;
	
	private long id;
	private String libelle;
	
	//CONSTRUCTEURS
	public Categorie() {
	}

	public Categorie(long id, String libelle) {
		this.id = id;
		this.libelle = libelle;
	}
	
	// GETTERS & SETTERS
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Categorie [id=");
		builder.append(id);
		builder.append(", libelle=");
		builder.append(libelle);
		builder.append("]");
		return builder.toString();
	}
	
}
