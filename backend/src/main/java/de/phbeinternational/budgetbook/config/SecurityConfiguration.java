package de.phbeinternational.budgetbook.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import de.phbeinternational.budgetbook.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

	/*
	 * Diese Klasse behandelt die Spring-Security-Konfiguration, wie HTTP-Sicherheitsrichtlinien, 
	 * JWT-Authentifizierung und CORS-Konfiguration speziell für den Spring-Security-Filter.
	 */
	
	private final AuthenticationProvider authenticationProvider; // defined in WebConfig.java as a Bean
	private final JwtAuthenticationFilter jwtAuthFilter;
	private static final String[] ALLOW_LIST = {"/api/auth/**"};
	
	public SecurityConfiguration(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthFilter) {
		this.authenticationProvider = authenticationProvider;
		this.jwtAuthFilter = jwtAuthFilter;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests( req -> req.requestMatchers(ALLOW_LIST)
					.permitAll()
					.anyRequest()
					.authenticated()
			)
			.sessionManagement(
					session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
			.cors();
		
		return http.build();
	}
	
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:8080"));  // Erlaubt CORS-Anfragen von diesen Ursprüngen
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // Erlaubt diese HTTP-Methoden
        configuration.setAllowedHeaders(Arrays.asList("*")/*Arrays.asList("Content-Type", "Authorization", "X-Requested-With")*/);  // Erlaubt diese Header
        configuration.setAllowCredentials(true);  // Wenn du mit Cookies oder dem Authorization-Header arbeitest
        configuration.setMaxAge(3600L);  // Preflight-Anfragen für maximal eine Stunde zwischenspeichern

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // Wendet diese Konfiguration auf alle Endpunkte an
        return source;
    }
}
