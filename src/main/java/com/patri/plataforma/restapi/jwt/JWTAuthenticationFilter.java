
package com.patri.plataforma.restapi.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTAuthenticationFilter extends GenericFilterBean
{
	private final TokenAuthenticationService tks;
	private final HandlerExceptionResolver handler;
	
	public JWTAuthenticationFilter(TokenAuthenticationService tks, HandlerExceptionResolver handler)
	{
		super();
		this.tks = tks;
		this.handler = handler;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException
	{
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		Authentication authentication = null;
		if (isPublic(req)) {
			filterChain.doFilter(request, response);
			return;
		}
		try
		{
			authentication = tks.getAuthentication(req, resp);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			filterChain.doFilter(request, response);
		} catch (JWTAutenticationException e)
		{
			log.debug("ANONIMOUS ACCESS: {}", req.getRequestURI());
			handler.resolveException(req, resp, null, e);
		}
	}

	private boolean isPublic(HttpServletRequest req) {
		 if (StringUtils.contains(req.getRequestURI(), "swagger-ui") ||
				 StringUtils.contains(req.getRequestURI(), "api-docs") ||
				 StringUtils.contains(req.getRequestURI(), "webjars") ||
				 StringUtils.contains(req.getRequestURI(), "blob") ||
				 (StringUtils.contains(req.getRequestURI(), "usuarios") &&
						 !StringUtils.contains(req.getRequestURI(), "refresh")))
		{
			 return true;
		}
		 return false;
	}
}