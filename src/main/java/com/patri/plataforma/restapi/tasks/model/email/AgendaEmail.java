package com.patri.plataforma.restapi.tasks.model.email;

import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgendaEmail
{
	@JsonProperty(value = "pautas")
	private Collection<PautaEmail> pautas;
}
