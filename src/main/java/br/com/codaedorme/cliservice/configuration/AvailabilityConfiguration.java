package br.com.codaedorme.cliservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.core.command.availability.Availability;
import org.springframework.shell.core.command.availability.AvailabilityProvider;

import br.com.codaedorme.cliservice.context.Session;

@Configuration
public class AvailabilityConfiguration {

	@Bean
	public AvailabilityProvider admin(Session session) {
		return () -> session.isAuthenticated() && session.isAdmin()
				? Availability.available()
				: Availability.unavailable("É preciso ser um administrador logado!");
	}

	@Bean
	public AvailabilityProvider authenticated(Session session) {
		return () -> session.isAuthenticated()
				? Availability.available()
				: Availability.unavailable("Você precisa estar logado!");
	}
}
