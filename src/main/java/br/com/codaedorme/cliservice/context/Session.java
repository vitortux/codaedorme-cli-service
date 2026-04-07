package br.com.codaedorme.cliservice.context;

import lombok.Getter;

import org.springframework.stereotype.Component;

import br.com.codaedorme.cliservice.domain.dto.UserDTO;

@Getter
@Component
public class Session {

	private String token;

	private UserDTO user;

	public void login(String token, UserDTO user) {
		this.token = token;
		this.user = user;
	}

	public void logout() {
		this.token = null;
		this.user = null;
	}

	public boolean isAuthenticated() {
		return token != null && user != null;
	}
}
