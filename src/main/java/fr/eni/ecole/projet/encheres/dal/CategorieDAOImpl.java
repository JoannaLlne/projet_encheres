package fr.eni.ecole.projet.encheres.dal;

import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import fr.eni.ecole.projet.encheres.bo.Categorie;

@Repository
public class CategorieDAOImpl implements CategorieDAO {

    private JdbcTemplate jdbcTemplate;

    public CategorieDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Categorie> findAll() {
        String sql = "SELECT no_categorie AS id, libelle FROM categories";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Categorie.class));
    }
}
