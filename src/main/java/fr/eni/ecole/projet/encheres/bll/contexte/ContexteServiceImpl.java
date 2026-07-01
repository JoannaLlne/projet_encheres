package fr.eni.ecole.projet.encheres.bll.contexte;

import org.springframework.stereotype.Service;
import fr.eni.ecole.projet.encheres.bo.Utilisateur;
import fr.eni.ecole.projet.encheres.dal.UtilisateurDAO;

@Service
public class ContexteServiceImpl implements ContexteService {

	private UtilisateurDAO utilisateurDAO;
	
	public ContexteServiceImpl() {
	}
	
	public ContexteServiceImpl(UtilisateurDAO utilisateurDAO) {
		this.utilisateurDAO = utilisateurDAO;
	}

	@Override
	public Utilisateur charger(String pseudo) {
		return utilisateurDAO.read(pseudo);
	}
}
