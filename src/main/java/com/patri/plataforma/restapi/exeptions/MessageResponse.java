package com.patri.plataforma.restapi.exeptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse implements MessageResponseInterface
{
	@Schema(description = "Mensagens")
	private List<String> msgs;

	@Schema(description = "Data e hora de ocorrência do evento.")
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime date;

	@Schema(description = "URL que foi requisitada.")
	protected String path;

	@JsonIgnore
	private String user;

	@Schema(description = "A causa da mensagem.")
	protected String causa;

	@Schema(description = "Detalhe da mensagem.")
	protected String stackTrace;

	@JsonIgnore
	private HttpStatus status;

	public MessageResponse()
	{
	}

	public MessageResponse(String msg) {
		super();
		this.msgs = Collections.singletonList(msg);
		this.date = LocalDateTime.now();
	}

	public MessageResponse(String msg, HttpStatus status, String causa){
		this.msgs = Collections.singletonList(msg);
		this.status = status;
		this.causa = causa;
		this.date = LocalDateTime.now();
	}
	
	public MessageResponse(String msg, String causa)
	{
		super();
		this.msgs = Collections.singletonList(msg);
		this.causa = causa;
		this.date = LocalDateTime.now();
	}

	public MessageResponse(Exception exc)
	{
		super();
		this.stackTrace = ExceptionUtils.getStackTrace(exc);
		this.causa = ExceptionUtils.getRootCauseMessage(exc);
		this.date = LocalDateTime.now();
	}

	public MessageResponse(String msg, Exception exc)
	{
		super();
		this.msgs = Collections.singletonList(msg);
		this.stackTrace = ExceptionUtils.getStackTrace(exc);
		this.causa = ExceptionUtils.getRootCauseMessage(exc);
		this.date = LocalDateTime.now();
	}

	public MessageResponse(List<String> msgs, Exception exc)
	{
		super();
		this.msgs = msgs;
		this.stackTrace = ExceptionUtils.getStackTrace(exc);
		this.date = LocalDateTime.now();
	}

	public void addMensagem(String mensagem)
	{
		if (this.msgs == null)
		{
			this.msgs = new ArrayList<String>();
		}
		this.msgs.add(mensagem);
	}

	@Override
	public String toString()
	{
		return "MessageResponse [msgs=" + msgs + ", date=" + date + ", causa=" + causa + ", path=" + path + ", user="
				+ user + ", stackTrace=" + stackTrace + ", status=" + status + "]";
	}
}
