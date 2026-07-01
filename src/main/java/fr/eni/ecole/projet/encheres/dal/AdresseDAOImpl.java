package fr.eni.ecole.projet.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import fr.eni.ecole.projet.encheres.bo.Adresse;
import fr.eni.ecole.projet.encheres.dal.UtilisateurDAOImpl.UtilisateurRowMapper;


@Repository
public class AdresseDAOImpl implements AdresseDAO {
    private static final String INSERT = "INSERT INTO adresses (rue, code_postal, ville)" + "VALUES (:rue, :codePostal, :ville)";
	private final String FIND_noAdresse = "SELECT no_adresse FROM adresses  WHERE noAdresse = :noAdresse";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
    
    @Override
    public List<Adresse> findAll() {
        String sql = "SELECT no_adresse AS id, rue, code_postal AS codePostal, ville FROM adresses";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Adresse.class));
    }
   
	@Override
	public void createAddress(Adresse adresse) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("rue", adresse.getRue());
		namedParameters.addValue("codePostal", adresse.getCodePostal());
		namedParameters.addValue("ville", adresse.getVille());

		jdbcTemplate.update(INSERT, namedParameters, keyHolder, new String[]{"no_adresse"});
		
		if (keyHolder != null && keyHolder.getKey() != null) {
			// Mise à jour de l'identifiant auto-généré par la base
			adresse.setId(keyHolder.getKey().intValue());
		}
	}

	class AdresseRowMapper implements RowMapper<Adresse> {
		@Override
		public Adresse mapRow(ResultSet rs, int rowNum) throws SQLException {
			Adresse a = new Adresse();
			a.setId(rs.getInt("id"));
			a.setRue(rs.getString("rue"));
			a.setCodePostal(rs.getString("codePostal"));
			a.setVille(rs.getString("ville"));

			return a;
		}
	}
}