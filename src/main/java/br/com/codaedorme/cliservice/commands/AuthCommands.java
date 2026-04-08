package br.com.codaedorme.cliservice.commands;

import org.springframework.shell.core.command.CommandContext;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Option;
import org.springframework.stereotype.Component;

import br.com.codaedorme.cliservice.context.Session;
import br.com.codaedorme.cliservice.domain.dto.LoginDTO;
import br.com.codaedorme.cliservice.domain.dto.UserDTO;
import br.com.codaedorme.cliservice.domain.enumeration.UserGroupEnum;

@Component
public class AuthCommands extends BaseCommands {

	private static final String BASE_URL = "http://localhost:8080/auth";

	public AuthCommands(Session session) {
		super(session);
	}

	@Command(name = "auth register", group = "auth", description = "Cadastro de usuário", availabilityProvider = "admin")
	public String register(
			CommandContext ctx,
			@Option(shortName = 'n', longName = "name", required = true) String name,
			@Option(shortName = 'e', longName = "email", required = true) String email,
			@Option(shortName = 'c', longName = "cpf", required = true) String cpf,
			@Option(shortName = 'g', longName = "grupo", required = true) String group) throws Exception {

		String pwd = ctx.inputReader().readInput("Insira sua senha: ");
		String confirmPwd = ctx.inputReader().readInput("Confirme sua senha: ");

		if (!pwd.equals(confirmPwd)) {
			return "Senhas não coincidem. Confime sua senha antes de continuar!";
		}

		try {
			UserGroupEnum groupEnum = UserGroupEnum.valueOf(group.toUpperCase().trim());

			UserDTO user = UserDTO.builder()
					.name(name)
					.email(email)
					.cpf(cpf)
					.userGroup(groupEnum)
					.password(pwd)
					.build();

			System.out.println("Enviando requisição de cadastro para o usuário: " + user.toString());

			Long id = restTemplate.postForEntity(BASE_URL + "/create", user, Long.class).getBody();
			return "Usuário ID " + id + " cadastrado com sucesso!";
		} catch (Exception e) {
			return "Erro ao cadastrar usuário: " + e.getMessage();
		}
	}

	@Command(name = "auth login", group = "auth", description = "Login na aplicação")
	public String login(
			@Option(shortName = 'e', longName = "email", required = true) String email,
			@Option(shortName = 'p', longName = "password", required = true) String password) {

		try {
			LoginDTO credentials = new LoginDTO(email, password);
			UserDTO loggedUser = restTemplate.postForEntity(BASE_URL + "/login", credentials, UserDTO.class).getBody();

			session.login("token", loggedUser);
			System.out.println("Login realizado com sucesso! Bem-vindo, " + loggedUser.getName() + "!");
			return "Dados da sessão: " + loggedUser.toString();
		} catch (Exception e) {
			return "Erro ao realizar login: " + e.getMessage();
		}
	}
}
