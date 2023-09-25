package com.patri.plataforma.restapi.exeptions;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;

public interface MessageResponseInterface
{
	public String getCausa();
	public void setCausa(String causa);
	public String getStackTrace();
	public void setStackTrace(String stackTrace);
	public String getUser();
	public void setUser(String user);
	public String getPath();
	public void setPath(String path);
	public void addMensagem(String mensagem);
	public List<String> getMsgs();
	public void setMsgs(List<String> msgs);
	public LocalDateTime getDate();
	public void setDate(LocalDateTime date);
	public HttpStatus getStatus();
	public void setStatus(HttpStatus status);
}