package br.com.codaedorme.cliservice.commands;

import lombok.RequiredArgsConstructor;

import org.springframework.web.client.RestTemplate;

import br.com.codaedorme.cliservice.context.Session;

@RequiredArgsConstructor
public class BaseCommands {

	protected static final RestTemplate restTemplate = new RestTemplate();

	protected final Session session;
}
