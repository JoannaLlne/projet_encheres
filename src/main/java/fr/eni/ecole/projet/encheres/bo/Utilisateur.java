package fr.eni.ecole.projet.encheres.bo;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Utilisateur implements Serializable {
    private static final long serialVersionUID = 1L;
	@NotBlank
	private String pseudo;
	
	@NotBlank
	private String nom;
	
	@NotBlank
	private String prenom;
	
	@NotBlank
	private String email;
	
	private String telephone;
	
	@NotBlank
    @Size(min=8, max=20)
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*?[0-9]).{8,20}$")
	private String motDePasse;
	private int credit;
	private boolean admin;
	private Adresse adresse;
	
	//CONSTRUCTEURS
	public Utilisateur() {
	}

	public Utilisateur(@NotBlank String pseudo, @NotBlank String nom, @NotBlank String prenom, @NotBlank String email,
			String telephone, @NotBlank @Size(min = 8, max = 20) String motDePasse, int credit, boolean admin,
			Adresse adresse) {
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.admin = admin;
		this.adresse = adresse;
	}

	//GETTERS & SETTERS
	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Utilisateur [pseudo=");
		builder.append(pseudo);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", prenom=");
		builder.append(prenom);
		builder.append(", email=");
		builder.append(email);
		builder.append(", telephone=");
		builder.append(telephone);
		builder.append(", motDePasse=");
		builder.append(motDePasse);
		builder.append(", credit=");
		builder.append(credit);
		builder.append(", admin=");
		builder.append(admin);
		builder.append(", adresse=");
		builder.append(adresse);
		builder.append("]");
		return builder.toString();
	}

}
