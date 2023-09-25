package com.patri.plataforma.restapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.TreeMap;

@Configuration
public class SwaggerConfig
{
	@Value(value = "${api.nome}")
	private String nome;
	@Value(value = "${api.descricao}")
	private String descricao;
	@Value(value = "${api.versao}")
	private String versao;

	@Bean
	public OpenAPI patriOpenAPI()
	{
		return new OpenAPI()
				.info(new Info()
						.title(nome)
						.description(descricao)
						.version(versao)
						.contact(contact())
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}

	@Bean
	public OpenApiCustomiser sortSchemasAlphabetically()
	{
		return openApi -> {
			Map<String, Schema> schemas = openApi.getComponents().getSchemas();
			openApi.getComponents().setSchemas(new TreeMap<>(schemas));
		};
	}

	private Contact contact() {
		Contact contact = new Contact();
		contact.setName("Luxfacta Solucoes de TI");
		contact.setEmail("comercial.br@luxfacta.com");
		contact.setUrl("https://www.luxfacta.com");
		return contact;
	}
}
