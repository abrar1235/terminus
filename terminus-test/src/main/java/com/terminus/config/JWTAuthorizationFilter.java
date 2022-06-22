package com.terminus.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private static final String HEADER_NAME = "Authorization";
//	private static final String SECRET = "myAwesomes#$^er^^";

	private final String secret;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, String secret) {
		super(authenticationManager);
		this.secret = secret;
	}

	/**
	 * filter all the calls before the calling actual controller methods
	 */
	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String header = request.getHeader(HEADER_NAME);
		if (header == null || !header.startsWith("Bearer")) {
			chain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

	/**
	 * authenticate the user token
	 * 
	 * @param request {@link HttpServletRequest} from which header can be extracted
	 * @return new {@link UsernamePasswordAuthenticationToken} object
	 */
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		try {
			String token = request.getHeader(HEADER_NAME);
			String user = JWT.require(Algorithm.HMAC512(secret)).build().verify(token.replace("Bearer ", ""))
					.getSubject();
			String role = JWT.require(Algorithm.HMAC512(secret)).build().verify(token.replace("Bearer ", ""))
					.getClaim("ROLE").asString();
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, "", new ArrayList<>(List.of(() -> role)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
