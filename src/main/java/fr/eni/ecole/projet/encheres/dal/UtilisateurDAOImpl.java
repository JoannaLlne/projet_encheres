package fr.eni.ecole.projet.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import fr.eni.ecole.projet.encheres.bo.Adresse;
import fr.eni.ecole.projet.encheres.bo.Utilisateur;


@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {
	private final String FIND_BY_PSEUDO = "SELECT u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.mot_de_passe, u.credit, u.administrateur, u.no_adresse, " +
		    "a.rue, a.code_postal, a.ville " +
		    "FROM utilisateurs u " +
		    "LEFT JOIN adresses a ON u.no_adresse = a.no_adresse " +
		    "WHERE u.pseudo = :pseudo";
	private final String FIND_ALL = "SELECT u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.mot_de_passe, u.credit, u.administrateur, u.no_adresse, " +
		    "a.rue, a.code_postal, a.ville " +
		    "FROM utilisateurs u " +
		    "LEFT JOIN adresses a ON u.no_adresse = a.no_adresse";
	private final String FIND_PSEUDO = "SELECT pseudo FROM utilisateurs  WHERE pseudo = :pseudo";
    private static final String INSERT = "INSERT INTO utilisateurs (pseudo, nom, prenom, email, telephone, mot_de_passe, credit, no_adresse)" + "VALUES (:pseudo, :nom, :prenom, :email, :telephone, :motDePasse, 10, :noAdresse)";
    private static final String DELETE = "DELETE FROM utilisateurs (pseudo, nom, prenom, email, telephone, mot_de_passe, credit)" + "VALUES (:pseudo, :nom, :prenom, :email, :telephone, :motDePasse, :credit)";
    private final String UPDATE = "UPDATE utilisateurs SET nom = :nom, prenom = :prenom, email = :email, telephone = :telephone, credit = :credit WHERE pseudo = :pseudo";
    private final String UPDATE_CREDIT = "UPDATE utilisateurs SET credit = credit + :delta WHERE pseudo = :pseudo";

    
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public Utilisateur read(String pseudo) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", pseudo);

		return jdbcTemplate.queryForObject(FIND_BY_PSEUDO, namedParameters, new UtilisateurRowMapper());
	}

	


	@Override
	public void createUtilisateur(Utilisateur utilisateur) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		namedParameters.addValue("pseudo", utilisateur.getPseudo());
		namedParameters.addValue("nom", utilisateur.getNom());
		namedParameters.addValue("prenom", utilisateur.getPrenom());
		namedParameters.addValue("email", utilisateur.getEmail());
		namedParameters.addValue("telephone", utilisateur.getTelephone());
		namedParameters.addValue("motDePasse", utilisateur.getMotDePasse());
		namedParameters.addValue("credit", utilisateur.getCredit());
		namedParameters.addValue("noAdresse", utilisateur.getAdresse().getId());




		jdbcTemplate.update(INSERT, namedParameters);
	}

	@Override
	public void deleteUtilisateur(Utilisateur utilisateur) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", utilisateur.getPseudo());
		namedParameters.addValue("nom", utilisateur.getNom());
		namedParameters.addValue("prenom", utilisateur.getPrenom());
		namedParameters.addValue("email", utilisateur.getEmail());
		namedParameters.addValue("telephone", utilisateur.getTelephone());
		namedParameters.addValue("mot_de_passe", utilisateur.getMotDePasse());
		namedParameters.addValue("credit", utilisateur.getCredit());
		jdbcTemplate.update(DELETE, namedParameters);
	}

	@Override
	public List<Utilisateur> findAll() {
		return jdbcTemplate.query(FIND_ALL, new UtilisateurRowMapper());
	}
	
	@Override
	public boolean findPseudo(String pseudo) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", pseudo);

		int val = jdbcTemplate.queryForObject(FIND_PSEUDO, namedParameters,  Integer.class);
		return val >= 1;
	}

	class UtilisateurRowMapper implements RowMapper<Utilisateur> {
	    @Override
	    public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
	        Utilisateur m = new Utilisateur();
	        m.setPseudo(rs.getString("pseudo"));
	        m.setNom(rs.getString("nom"));
	        m.setPrenom(rs.getString("prenom"));
	        m.setEmail(rs.getString("email"));
	        m.setTelephone(rs.getString("telephone"));
	        m.setMotDePasse(rs.getString("mot_de_passe"));
	        m.setCredit(rs.getInt("credit"));
	        m.setAdmin(rs.getBoolean("administrateur"));

	        // Association pour l'adresse
	        Adresse adresse = new Adresse();
	        adresse.setId(rs.getInt("no_adresse"));
	        adresse.setRue(rs.getString("rue"));
	        adresse.setCodePostal(rs.getString("code_postal"));
	        adresse.setVille(rs.getString("ville"));
	        m.setAdresse(adresse);
	        
	        return m;
	    }
	
	}

	@Override
	public void update(Utilisateur utilisateur) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("pseudo", utilisateur.getPseudo());
		namedParameters.addValue("nom", utilisateur.getNom());
		namedParameters.addValue("prenom", utilisateur.getPrenom());
		namedParameters.addValue("email", utilisateur.getEmail());
		namedParameters.addValue("telephone", utilisateur.getTelephone());
		namedParameters.addValue("mot_de_passe", utilisateur.getMotDePasse());
		namedParameters.addValue("credit", utilisateur.getCredit());
		namedParameters.addValue("rue", utilisateur.getAdresse().getRue());
		namedParameters.addValue("codePostal", utilisateur.getAdresse().getCodePostal());
		namedParameters.addValue("ville", utilisateur.getAdresse().getVille());

		jdbcTemplate.update(UPDATE, namedParameters);
	}

	@Override
	public void modifierCredit(String pseudo, int delta) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	    namedParameters.addValue("pseudo", pseudo);
	    namedParameters.addValue("delta", delta);
	    jdbcTemplate.update(UPDATE_CREDIT, namedParameters);
		
	}
}
