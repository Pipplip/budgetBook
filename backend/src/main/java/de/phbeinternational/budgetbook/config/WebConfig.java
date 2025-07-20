package de.phbeinternational.budgetbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import de.phbeinternational.budgetbook.repository.UserRepository;

@Configuration
//@EnableJpaRepositories(basePackages = "de.phbeinternational.budgetbook.repository")
//@EntityScan(basePackages = "de.phbeinternational.budgetbook.entity")
public class WebConfig implements WebMvcConfigurer {
	
	/*
	 * Diese Klasse kümmert sich um allgemeine Spring-Boot-Konfigurationen wie CORS, 
	 * benutzerdefinierte UserDetailsService, Authentifizierung und PasswordEncoder.
	 */
	
	private final UserRepository userRepo;
	
	public WebConfig(UserRepository userRepo) {
		 this.userRepo = userRepo;
	}

	// CORS mapping wird in der SecurityConfiguration durchgefuehrt
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // CORS für alle Routen aktivieren
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:8080", "http://localhost:4200")  // Erlaubt nur Anfragen von dieser Domain
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Erlaubt nur diese HTTP-Methoden
//                .allowedHeaders("*")  // Erlaubt alle Header
//                .allowCredentials(true) // Wenn du mit Cookies oder dem Authorization-Header arbeitest, musst du allowCredentials(true) setzen, um CORS-Anfragen zu erlauben, die mit Anmeldeinformationen gesendet werden.
//                .maxAge(3600);  // Cache-Control für Preflight-Anfragen, 1 Stunde
//    }
    
    @Bean
    public UserDetailsService userDetailsService() {
    	
    	return username -> userRepo.findByEmail(username)
    			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    	
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    	provider.setUserDetailsService(userDetailsService());
    	provider.setPasswordEncoder(passwordEncoder());
    	return provider;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    	return config.getAuthenticationManager();
    }
}
