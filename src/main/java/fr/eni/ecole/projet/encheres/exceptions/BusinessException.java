package fr.eni.ecole.projet.encheres.exceptions;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;
		private List<String> clefsExternalisations;

		public BusinessException() {
			super();
		}

		public BusinessException(Throwable cause) {
			super(cause);
		}

		public List<String> getClefsExternalisations() {
			return clefsExternalisations;
		}

// Ajouter une clef d'erreur
		public void add(String clef) {
			if (clefsExternalisations == null) {
				clefsExternalisations = new ArrayList<>();
			}
			clefsExternalisations.add(clef);
		}


//Confirmer si des erreurs ont été chargées
		public boolean isValid() {
			return clefsExternalisations == null || clefsExternalisations.isEmpty();
		}
}
