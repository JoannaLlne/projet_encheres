package fr.eni.ecole.projet.encheres.bo;

import java.io.Serializable;
import org.springframework.jdbc.support.KeyHolder;

import jakarta.validation.constraints.NotBlank;

public class Adresse implements Serializable {
    private static final long serialVersionUID = 1L;

	private long id;
	
	@NotBlank
	private String rue;
	
	@NotBlank
	private String codePostal;
	
	@NotBlank
	private String ville;
	
	//CONSTRUCTEURS
	public Adresse() {
	}

	public Adresse(long id, String rue, String codePostal, String ville) {
		this.id = id;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}

	//GETTERS & SETTERS
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Adresse [id=");
		builder.append(id);
		builder.append(", rue=");
		builder.append(rue);
		builder.append(", codePostal=");
		builder.append(codePostal);
		builder.append(", ville=");
		builder.append(ville);
		builder.append("]");
		return builder.toString();
	}
}
