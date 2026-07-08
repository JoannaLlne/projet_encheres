package fr.eni.ecole.projet.encheres.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.eni.ecole.projet.encheres.bll.ArticleService;
import fr.eni.ecole.projet.encheres.bll.CategorieService;
import fr.eni.ecole.projet.encheres.bll.EnchereService;
import fr.eni.ecole.projet.encheres.bo.ArticleAVendre;
import fr.eni.ecole.projet.encheres.bo.Utilisateur;
import fr.eni.ecole.projet.encheres.dal.AdresseDAO;

@Controller
public class EncheresController {

	private CategorieService categorieService;
	private ArticleService articleService;
	private AdresseDAO adresseDAO;
	private EnchereService enchereService;

	public EncheresController(CategorieService categorieService, ArticleService articleService, AdresseDAO adresseDAO,
			EnchereService enchereService) {
		this.categorieService = categorieService;
		this.articleService = articleService;
		this.adresseDAO = adresseDAO;
		this.enchereService = enchereService;
	}

	@GetMapping("/")
	public String accueil(@RequestParam(name = "categorieId", required = false) Long categorieId,
	        @RequestParam(name = "nomArticle", required = false) String nomArticle,
	        @RequestParam(name = "typeFiltre", required = false) String typeFiltre,
	        @RequestParam(name = "statutAchat", required = false) String statutAchat,
	        @RequestParam(name = "statutVente", required = false) String statutVente, 
	        Principal principal,
	        Model model) {

	    String pseudo = (principal != null) ? principal.getName() : null;
	    
	    // Une seule méthode qui cumule tous les filtres
	    List<ArticleAVendre> encheres = articleService.findWithFilters(
	        nomArticle, 
	        categorieId, 
	        pseudo, 
	        typeFiltre, 
	        statutAchat, 
	        statutVente
	    );

	    model.addAttribute("categories", categorieService.findAll());
	    model.addAttribute("categorieSelectionnee", categorieId);
	    model.addAttribute("nomArticle", nomArticle);
	    model.addAttribute("typeFiltre", typeFiltre);
	    model.addAttribute("statutAchat", statutAchat);
	    model.addAttribute("statutVente", statutVente);
	    model.addAttribute("encheres", encheres);

	    return "accueil";
	}

	@GetMapping("/vendre")
	public String vendre() {
		return "redirect:/nouvelle-vente";
	}

	@GetMapping("/nouvelle-vente")
	public String nouvelleVente(Model model) {
		model.addAttribute("categories", categorieService.findAll());
		model.addAttribute("adresses", adresseDAO.findAll());
		model.addAttribute("article", new ArticleAVendre());
		return "nouvelle-vente";
	}

	@PostMapping("/nouvelle-vente")
	public String nouvelleVente(@ModelAttribute ArticleAVendre article,
			@RequestParam(name = "categorieId") Long categorieId, @RequestParam(name = "adresseId") Long adresseId,
			Principal principal, RedirectAttributes redirectAttributes) {
		Long articleId = articleService.save(article, categorieId, adresseId, principal.getName());
		redirectAttributes.addAttribute("articleId", articleId);
		return "redirect:/photo-upload";
	}

	@GetMapping("/photo-upload")
	public String photoUpload(@RequestParam(name = "articleId", required = false) Long articleId, Model model) {
		model.addAttribute("articleId", articleId);
		return "photo-upload";
	}

	@PostMapping("/photo-upload")
	public String uploadPhoto(@RequestParam("articleId") Long articleId, @RequestParam("photo") MultipartFile photo,
			RedirectAttributes redirectAttributes) throws IOException {

		if (!photo.isEmpty()) {
			String filename = photo.getOriginalFilename();

			Path uploadDir = Paths.get("uploads/photos");
			if (!Files.exists(uploadDir)) {
				Files.createDirectories(uploadDir);
			}

			Path filePath = uploadDir.resolve(filename);
			photo.transferTo(filePath);

			articleService.updatePhoto(articleId, filename);
		}

		return "redirect:/";
	}

	@GetMapping("/vente-detail")
	public String venteDetail(@RequestParam(name = "id") Long id, Principal principal, Model model) {
	    ArticleAVendre article = articleService.findById(id);
	    String pseudoConnecte = (principal != null) ? principal.getName() : null;
	    boolean estMaVente = pseudoConnecte != null
	            && !article.getVendeurs().isEmpty()
	            && pseudoConnecte.equals(article.getVendeurs().get(0).getPseudo());

	    model.addAttribute("enchere", article);
	    model.addAttribute("estMaVente", estMaVente);
	    return "vente-detail";
	}

	@PostMapping("/vente-detail")
	public String placerEnchere(@RequestParam(name = "id") Long id,
	                             @RequestParam(name = "montant") int montant,
	                             Principal principal,
	                             RedirectAttributes redirectAttributes) {
	    if (principal == null) {
	        return "redirect:/connexion";
	    }
	    try {
	        enchereService.faireEnchere(id, principal.getName(), montant);
	    } catch (IllegalArgumentException e) {
	        redirectAttributes.addFlashAttribute("erreurEnchere", e.getMessage());
	    }
	    redirectAttributes.addAttribute("id", id);
	    return "redirect:/vente-detail";
	}

	@GetMapping("/vente-win-detail")
	public String venteWinDetail() {
		return "vente-win-detail";
	}

}
