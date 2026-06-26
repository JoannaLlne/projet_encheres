package fr.eni.ecole.projet.encheres.configuration.security;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class EnchereSecurityConfig {
	private static final String SELECT_USER = "SELECT pseudo, mot_de_passe, 1 FROM utilisateurs WHERE pseudo = ?";
	private static final String SELECT_ROLES = "SELECT u.pseudo, r.role FROM utilisateurs u INNER JOIN roles r"
			+ " ON r.is_admin = u.administrateur WHERE u.pseudo = ?";

	@Bean
	// RECUPERER INFOS UTILISATEUR
	UserDetailsManager userDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		
		//RECUPERER PSEUDO + PASSWORD
		jdbcUserDetailsManager.setUsersByUsernameQuery(SELECT_USER);
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(SELECT_ROLES);

		return jdbcUserDetailsManager;
	}

	// FILTRES DE SECURITE
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.authorizeHttpRequests(auth -> {
	        auth
	            .requestMatchers("/").permitAll()
	            .requestMatchers("/*").permitAll()
	            .requestMatchers("/css/*").permitAll()
	            .requestMatchers("/js/**").permitAll()
	            .requestMatchers("/images/*").permitAll()
	            .requestMatchers("/vendre").permitAll()
	            .requestMatchers("/nouvelle-vente").permitAll()
	            .requestMatchers("/inscription").permitAll()
	            .requestMatchers("/accueil").permitAll()
	            
	            // ADMIN, UTILISATEUR
	            .requestMatchers(HttpMethod.POST, "/profil").hasAnyRole("ADMIN, UTILISATEUR")
	            .requestMatchers(HttpMethod.POST, "/pwd-editer").hasAnyRole("ADMIN, UTILISATEUR")
	            .requestMatchers(HttpMethod.POST, "/photo-upload").hasAnyRole("ADMIN, UTILISATEUR")
	            
	            // UTILISATEUR
	            .requestMatchers(HttpMethod.GET, "/vendeur-detail").hasRole("UTILISATEUR")
	            
	            // ACQUEREUR
	            .requestMatchers(HttpMethod.GET, "/vente-detail").hasRole("ACQUEREUR")
	            .requestMatchers(HttpMethod.GET, "/vente-win-detail").hasRole("ACQUEREUR")
	            
	            // VENDEUR
	            .requestMatchers(HttpMethod.POST, "/nouvelle-vente").hasRole("VENDEUR");
	    }); 

	    // FORMULAIRE
	    http.formLogin(form -> {
	        form.loginPage("/connexion")
	            .loginProcessingUrl("/login")
	            .permitAll()
	            .defaultSuccessUrl("/", true)
	            .permitAll();
	    });

	    // LOGOUT
	    http.logout(logout -> logout
	        .logoutUrl("/logout")
	        .invalidateHttpSession(true)
	        .clearAuthentication(true)
	        .deleteCookies("JSESSIONID")
	        .logoutSuccessUrl("/")
	        .permitAll());

	    return http.build();
	}
	
}