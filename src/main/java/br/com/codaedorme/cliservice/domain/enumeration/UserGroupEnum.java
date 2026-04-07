package br.com.codaedorme.cliservice.domain.enumeration;

import lombok.Getter;

@Getter
public enum UserGroupEnum {

	ADMINISTRADOR("Administrador"),
	ESTOQUISTA("Estoquista");

	private final String description;

	UserGroupEnum(String description) {
		this.description = description;
	}
}
