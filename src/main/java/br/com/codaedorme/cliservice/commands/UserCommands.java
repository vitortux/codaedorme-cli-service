package br.com.codaedorme.cliservice.commands;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.core.command.CommandContext;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Option;
import org.springframework.stereotype.Component;

import br.com.codaedorme.cliservice.context.Session;
import br.com.codaedorme.cliservice.domain.dto.PasswordRequestDTO;
import br.com.codaedorme.cliservice.domain.dto.UserDTO;

@Component
public class UserCommands extends BaseCommands {

	private static final String BASE_URL = "http://localhost:8080/users";

	public UserCommands(Session session) {
		super(session);
	}

	@Command(name = "user list", group = "user", description = "Lista de usuários cadastrados", availabilityProvider = "admin")
	public void findAll() {

		try {
			ResponseEntity<List<UserDTO>> response = restTemplate.exchange(
					BASE_URL,
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<UserDTO>>() {});

			List<UserDTO> users = response.getBody();

			System.out.println("Lista de usuários cadastrados: ");
			users.forEach(user -> System.out.println(user.toString()));
		} catch (Exception e) {
			System.out.println("Ocorreu um erro ao recuperar usuários: " + e.getMessage());
		}
	}

	@Command(name = "user change-password", group = "user", description = "Altera senha de um usuário por ID", availabilityProvider = "admin")
	public String changePwd(
			CommandContext ctx,
			@Option(longName = "id", required = true) Long id) throws Exception {
		String pwd = new String(ctx.inputReader().readPassword("Insira sua senha: "));
		String confirmPwd = new String(ctx.inputReader().readPassword("Confirme sua senha: "));

		if (!pwd.equals(confirmPwd)) {
			return "Senhas não coincidem. Confime sua senha antes de continuar!";
		}

		try {
			PasswordRequestDTO request = new PasswordRequestDTO(pwd, confirmPwd);
			String url = BASE_URL + "/" + id + "/update-password";
			restTemplate.put(url, request);
			return "Senha alterada com sucesso para o usuário ID " + id;
		} catch (Exception e) {
			return "Erro ao alterar senha para o usuário ID " + id + ", " + e.getMessage();
		}
	}
}
