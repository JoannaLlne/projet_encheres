package fr.eni.ecole.projet.encheres.controller.security;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.ecole.projet.encheres.bll.contexte.ContexteService;
import fr.eni.ecole.projet.encheres.bo.Utilisateur;

@Controller
@SessionAttributes("utilisateurEnSession")
public class LoginController {
		private ContexteService service;
		
	    public LoginController(ContexteService service) {
			this.service = service;
		}

	    @GetMapping("/login")
	    public String loginRedirect() {
	        return "redirect:/connexion";
	    }
	    
	    @GetMapping("/connexion")
	    public String loginPage() {
	        return "connexion";
	    }
	    
		//CHARGER MEMBRE EN SESSION
		public String chargerUtilisateurEnSession(@ModelAttribute("utilisateurEnSession") Utilisateur utilisateurEnSession,
				Principal principal) {
			String pseudo = principal.getName();
			Utilisateur aCharger = service.charger(pseudo);
			
			if (aCharger != null) {
				utilisateurEnSession.setPseudo(aCharger.getPseudo());
				utilisateurEnSession.setNom(aCharger.getNom());
				utilisateurEnSession.setPrenom(aCharger.getPrenom());
				utilisateurEnSession.setAdmin(aCharger.isAdmin());
			} else {
				utilisateurEnSession.setPseudo(null);
				utilisateurEnSession.setNom(null);
				utilisateurEnSession.setPrenom(null);
				utilisateurEnSession.setAdmin(false);
			}
			System.out.println(utilisateurEnSession);
			return "redirect:/accueil";
		}
		
		@ModelAttribute("utilisateurEnSession")
		public Utilisateur utilisateurEnSession() {
		    return new Utilisateur();
		}

		
		
		
}
