package com.patri.plataforma.restapi.tasks.model.email;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.patri.plataforma.restapi.mapper.ReportMapper;
import com.patri.plataforma.restapi.model.Item;
import com.patri.plataforma.restapi.model.Report;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsLetterEmail
{
	private static final Long TEMPLATE_PORQUE_ISSO_IMPORTA = 1l;
	
	@JsonProperty(value = "reports")
	private Collection<ReportEmail> reports;
	
	public static NewsLetterEmail montaObjetoNewsletter(Collection<Report> reports)
	{
		reports.forEach(r -> {
			List<Item> itens = r.getItens().stream()
					.filter(it -> it.getTemplate().getId().equals(TEMPLATE_PORQUE_ISSO_IMPORTA))
					.collect(Collectors.toList());
			r.setItens(itens);
		});
		
		List<ReportEmail> reportsEmail = reports.stream()
				.map(ReportMapper.INSTANCE::convertToTemplateEmail)
				.collect(Collectors.toList());
		
		NewsLetterEmail newsLetterEmail = new NewsLetterEmail(reportsEmail);
		return newsLetterEmail;
	}
}
