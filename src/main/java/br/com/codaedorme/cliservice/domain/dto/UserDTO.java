package br.com.codaedorme.cliservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import br.com.codaedorme.cliservice.domain.enumeration.StatusEnum;
import br.com.codaedorme.cliservice.domain.enumeration.UserGroupEnum;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private Long id;

	private String name;

	private String email;

	private String cpf;

	private UserGroupEnum userGroup;

	private StatusEnum status;

	private String password;
}
