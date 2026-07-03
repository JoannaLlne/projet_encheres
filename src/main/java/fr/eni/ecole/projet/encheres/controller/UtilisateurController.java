package fr.eni.ecole.projet.encheres.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import fr.eni.ecole.projet.encheres.bll.UtilisateurService;
import fr.eni.ecole.projet.encheres.bo.Adresse;
import fr.eni.ecole.projet.encheres.bo.Utilisateur;
import fr.eni.ecole.projet.encheres.exceptions.BusinessException;
import jakarta.validation.Valid;

@Controller
public class UtilisateurController {
	private UtilisateurService utilisateurService;

	public UtilisateurController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	@GetMapping("/inscription")
	public String creerUtilisateur(Model model) {
		Utilisateur utilisateur = new Utilisateur();
		Adresse adresse = new Adresse();
		
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("adresse", adresse);
		return "inscription";
	}

	@PostMapping("/inscription")
	public String updtateUtilisateur(@Valid @ModelAttribute("utilisateur") Utilisateur utilisateur, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			System.out.println("erreur");
			return "inscription";
		} else {
			try {
				System.out.println("Utilisateur récupéré depuis le formulaire : ");
				System.out.println(utilisateur);
			

				// SAUVEGARDE DES MODIFICATIONS
				utilisateurService.publierAdresse(utilisateur.getAdresse());
				utilisateurService.publierUtilisateur(utilisateur);

				return "redirect:/profil";

			} catch (BusinessException e) {
				e.getClefsExternalisations().forEach(key -> {
					ObjectError error = new ObjectError("globalError", key);
					bindingResult.addError(error);
				});
				System.out.println("erreur2");
				return "inscription";
			}
		}
	}

	@GetMapping("/profil")
	public String profil(@RequestParam(name = "pseudo", required = false) String pseudo, Principal principal,
			Model model) {
		String pseudoConnecte = (principal != null) ? principal.getName() : null;
		String pseudoAffiche = (pseudo != null) ? pseudo : pseudoConnecte;
		if (pseudoAffiche == null) {
			// ni pseudo en paramètre, ni utilisateur connecté
			return "redirect:/connexion";
		}

		boolean estMonProfil = pseudoAffiche.equals(pseudoConnecte);
		Utilisateur utilisateur = utilisateurService.consulterProfilByPseudo(pseudoAffiche);
		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("estMonProfil", estMonProfil);
		return "profil";
	}

	@PostMapping("/profil")
	public String modifierProfil(@ModelAttribute Utilisateur utilisateurForm, Principal principal, Model model) {
		String pseudoConnecte = principal.getName();

		Utilisateur utilisateur = utilisateurService.consulterProfilByPseudo(pseudoConnecte);
		utilisateur.setNom(utilisateurForm.getNom());
		utilisateur.setPrenom(utilisateurForm.getPrenom());
		utilisateur.setEmail(utilisateurForm.getEmail());
		utilisateur.setTelephone(utilisateurForm.getTelephone());
		utilisateur.setAdresse(utilisateurForm.getAdresse());

		utilisateurService.modifierProfil(utilisateur);

		model.addAttribute("utilisateur", utilisateur);
		model.addAttribute("estMonProfil", true);
		return "profil";
	}
}