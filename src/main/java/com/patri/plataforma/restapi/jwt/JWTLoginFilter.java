
package com.patri.plataforma.restapi.jwt;

import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.HandlerExceptionResolver;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    final HandlerExceptionResolver handlerExceptionResolver;
    final TokenAuthenticationService tks;

    protected JWTLoginFilter(AuthenticationManager authenticationManager, HandlerExceptionResolver handlerExceptionResolver, TokenAuthenticationService tks)
    {
    	super(new AntPathRequestMatcher("/login"));
    	setAuthenticationManager(authenticationManager);
    	this.handlerExceptionResolver = handlerExceptionResolver;
    	this.tks = tks;
    }

	@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        AccountCredentials credentials = new ObjectMapper()
            .readValue(request.getInputStream(), AccountCredentials.class);

        try
        {
        	return getAuthenticationManager().authenticate(
        			new UsernamePasswordAuthenticationToken(
        					credentials.getUsername(), 
        					credentials.getPassword(), 
        					Collections.emptyList()
        					)
        			);
        }catch (BadCredentialsException bex) 
        {
            handlerExceptionResolver.resolveException(request, response, null, bex);
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request, 
            HttpServletResponse response,
            FilterChain filterChain,
            Authentication auth) throws IOException, ServletException 
    {

//        if (auth.getAuthorities() != null && auth.getAuthorities().iterator().hasNext())
//        {
        	JWTUsuario user = (JWTUsuario) auth.getPrincipal();
            tks.login(response, user);
//        } 
//        else 
//        {
//            tks.addAuthentication(response, null);
//        }

    }
}