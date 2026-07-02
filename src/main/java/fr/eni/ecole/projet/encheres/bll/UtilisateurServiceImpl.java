package fr.eni.ecole.projet.encheres.bll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import fr.eni.ecole.projet.encheres.bo.Adresse;
import fr.eni.ecole.projet.encheres.bo.Utilisateur;
import fr.eni.ecole.projet.encheres.dal.AdresseDAO;
import fr.eni.ecole.projet.encheres.dal.UtilisateurDAO;
import fr.eni.ecole.projet.encheres.exceptions.BusinessCode;
import fr.eni.ecole.projet.encheres.exceptions.BusinessException;

@Service
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {
	private UtilisateurDAO utilisateurDAO;
	private AdresseDAO adresseDAO;
	


	public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO, AdresseDAO adresseDAO) {
		this.utilisateurDAO = utilisateurDAO;
		this.adresseDAO = adresseDAO;
	}
	
	/*
	 *  String encryptedPassword = passwordEncoder.encode(utilisateur.getMotDePasse());
		utilisateur.setMotDePasse(encryptedPassword);
	 * */

	@Override
	public void publierUtilisateur(Utilisateur utilisateur) {

		// VALIDATION DES DONNEES
		BusinessException be = new BusinessException();
		boolean isValid = true;
		isValid &= validePseudo(utilisateur.getPseudo(), be);
		isValid &= valideNom(utilisateur.getNom(), be);
		isValid &= validePrenom(utilisateur.getPrenom(), be);
		isValid &= valideEmail(utilisateur.getEmail(), be);
		isValid &= valideMdp(utilisateur.getMotDePasse(), be);
		isValid &= valideRue(utilisateur.getAdresse().getRue(), be);
		isValid &= valideCP(utilisateur.getAdresse().getCodePostal(), be);
		isValid &= valideVille(utilisateur.getAdresse().getVille(), be);

		if (isValid) {
			try {
				System.out.println(utilisateur);
				PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
				utilisateur.setMotDePasse(encoder.encode(utilisateur.getMotDePasse()));
		
				utilisateurDAO.createUtilisateur(utilisateur);
				
			} catch (DataAccessException e) {
				System.out.println("erreur de validation des données" + e);
				be.add(BusinessCode.BLL_UTILISATEUR_UPDATE_ERREUR);
				throw be;
			}
		} else {
			throw be;
		}
	}
	
	public void publierAdresse(Adresse adresse) {
		// VALIDATION DES DONNEES
		BusinessException be = new BusinessException();
		boolean isValid = true;

		isValid &= valideRue(adresse.getRue(), be);
		isValid &= valideCP(adresse.getCodePostal(), be);
		isValid &= valideVille(adresse.getVille(), be);

		if (isValid) {
			adresseDAO.createAddress(adresse);
			try {
		} catch (DataAccessException e) {
			System.out.println("erreur de validation des données" + e);
			be.add(BusinessCode.BLL_UTILISATEUR_UPDATE_ERREUR);
			throw be;
		}
			
		} else {
			throw be;
		}
	}

	@Override
	public List<Utilisateur> consulterProfil() {
		return utilisateurDAO.findAll();
	}

	@Override
	public Utilisateur consulterProfilByPseudo(String pseudo) {
		return utilisateurDAO.read(pseudo);
	}

	@Override
	public void modifierProfil(Utilisateur utilisateur) {
		utilisateurDAO.update(utilisateur);
	}


	// VALIDATION NOUVEL UTILISATEUR
	// UNICITE PSEUDO
	private boolean PseudoUnique(String pseudo, BusinessException be) {
		try {
			boolean pseudoExiste = utilisateurDAO.findPseudo(pseudo);
			if (pseudoExiste) {
				be.add(BusinessCode.VALIDATION_UTILISATEUR_PSEUDO_UNIQUE);
				return false;
			}
		} catch (DataAccessException e) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_PSEUDO_UNIQUE);
			return false;
		}
		return true;
	}

	// PSEUDO NON NUL
	private boolean validePseudo(String pseudo, BusinessException be) {
		if (pseudo == null || pseudo.isBlank()) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_PSEUDO_BLANK);
			return false;
		}
		return true;
	}

	// NOM NON NUL
	private boolean valideNom(String nom, BusinessException be) {
		if (nom == null || nom.isBlank()) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_NOM_BLANK);
			return false;
		}
		return true;
	}

	// PRENOM NON NUL
	private boolean validePrenom(String prenom, BusinessException be) {
		if (prenom == null || prenom.isBlank()) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_PRENOM_BLANK);
			return false;
		}
		return true;
	}

	// EMAIL NON NUL
	private boolean valideEmail(String email, BusinessException be) {
		if (email == null || email.isBlank()) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_EMAIL_BLANK);
			return false;
		}
		return true;
	}

	
	// PSEUDO NON NULL + TAILLE
	private boolean valideMdp(String motDePasse, BusinessException be) {
		if (motDePasse == null || motDePasse.isBlank()) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_PWD_BLANK);
			return false;
		}

		if (motDePasse.length() <= 8 && motDePasse.length() >= 25) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_PWD_LENGTH);
			return false;
		}

		 String regex = "^(?=.*[A-Za-z])(?=.*?[0-9]).{8,20}$";
		 
		 if (!motDePasse.matches(regex)) {
		 be.add(BusinessCode.VALIDATION_UTILISATEUR_PWD_PATTERN); 
		 return false; 
		 }
		return true;
	}
	
	private boolean valideRue(String rue, BusinessException be) {
		if (rue == null || rue.isBlank()) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_PSEUDO_BLANK);
			return false;
		}
		return true;
	}
	
	private boolean valideCP(String codePostal, BusinessException be) {
		if (codePostal == null || codePostal.isBlank()) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_PSEUDO_BLANK);
			return false;
		}
		return true;
	}
	
	private boolean valideVille(String ville, BusinessException be) {
		if (ville == null || ville.isBlank()) {
			be.add(BusinessCode.VALIDATION_UTILISATEUR_PSEUDO_BLANK);
			return false;
		}
		return true;
	}
}