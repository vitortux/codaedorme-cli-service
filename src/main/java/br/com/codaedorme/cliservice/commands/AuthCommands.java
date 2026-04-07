package br.com.codaedorme.cliservice.commands;

import lombok.RequiredArgsConstructor;

import org.springframework.shell.core.command.CommandContext;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.shell.core.command.annotation.Option;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.codaedorme.cliservice.domain.dto.UserDTO;
import br.com.codaedorme.cliservice.domain.enumeration.UserGroupEnum;

@Component
@RequiredArgsConstructor
public class AuthCommands {

	private static final String BASE_URL = "http://localhost:8080/users";

	private static final RestTemplate restTemplate = new RestTemplate();

	@Command(name = "auth register", group = "auth", description = "Cadastro de usuário")
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
					.group(groupEnum)
					.password(pwd)
					.build();

			System.out.println("Enviando requisição de cadastro para o usuário: " + user.toString());

			Long id = restTemplate.postForEntity(BASE_URL + "/create", user, Long.class).getBody();
			return "Usuário ID " + id + " cadastrado com sucesso!";
		} catch (Exception e) {
			return "Erro ao cadastrar usuário: " + e.getMessage();
		}
	}
}
