package br.com.codaedorme.cliservice.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import br.com.codaedorme.cliservice.domain.enumeration.StatusEnum;
import br.com.codaedorme.cliservice.domain.enumeration.UserGroupEnum;

@Getter
@Setter
@Builder
@ToString
public class UserDTO {

	private Long id;

	private String name;

	private String email;

	private String cpf;

	private UserGroupEnum group;

	private StatusEnum status;

	private String password;
}
