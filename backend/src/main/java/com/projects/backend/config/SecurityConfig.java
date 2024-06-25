package com.projects.backend.config;

import java.io.IOException;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.server.csrf.CsrfWebFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.formLogin(form -> form.loginPage("http://localhost:5173"))
				.csrf(csrf -> csrf.disable());

		// http.csrf(csrf ->
		// csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		// .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler())).formLogin(
		// form -> form.loginPage("http://localhost:5173"))
		// .authorizeHttpRequests(req ->
		// req.requestMatchers("/emoloyees").hasRole("ADMIN"));

		return http.build();
	}

	final class SpaCsrfTokenRequestHandler extends CsrfTokenRequestAttributeHandler {
		private final CsrfTokenRequestHandler delegate = new XorCsrfTokenRequestAttributeHandler();

		@Override
		public void handle(HttpServletRequest request, HttpServletResponse response, Supplier<CsrfToken> csrfToken) {
			/*
			 * Always use XorCsrfTokenRequestAttributeHandler to provide BREACH protection
			 * of
			 * the CsrfToken when it is rendered in the response body.
			 */

			this.delegate.handle(request, response, csrfToken);
			System.out.println("response " + response.getStatus());

		}
	}

	// @Override
	// public String resolveCsrfTokenValue(HttpServletRequest request, CsrfToken
	// csrfToken) {
	// /*
	// * If the request contains a request header, use
	// * CsrfTokenRequestAttributeHandler
	// * to resolve the CsrfToken. This applies when a single-page application
	// * includes
	// * the header value automatically, which was obtained via a cookie containing
	// * the
	// * raw CsrfToken.
	// */
	// System.out.println("token resolved?" +
	// request.getHeader(csrfToken.getHeaderName()) + csrfToken.getHeaderName());
	// System.out.println(request.getHeader("X-XSRF-TOKEN"));

	// if (StringUtils.hasText(request.getHeader(csrfToken.getHeaderName()))) {
	// System.out.println(csrfToken.getToken());
	// System.out.println("super resolve " + super.resolveCsrfTokenValue(request,
	// csrfToken));
	// return super.resolveCsrfTokenValue(request, csrfToken);
	// }
	// /*
	// * In all other cases (e.g. if the request contains a request parameter), use
	// * XorCsrfTokenRequestAttributeHandler to resolve the CsrfToken. This applies
	// * when a server-side rendered form includes the _csrf request parameter as a
	// * hidden input.
	// */
	// System.out.println("token resolved 2?" + csrfToken.getToken());

	// return this.delegate.resolveCsrfTokenValue(request, csrfToken);
	// }
	// }

	// final class CsrfCookieFilter extends OncePerRequestFilter {

	// @Override
	// protected void doFilterInternal(HttpServletRequest request,
	// HttpServletResponse response, FilterChain filterChain)
	// throws ServletException, IOException {
	// CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
	// // Render the token value to a cookie by causing the deferred token to be
	// loaded
	// csrfToken.getToken();
	// System.out.println("token resolved 3?" + csrfToken.getToken());

	// filterChain.doFilter(request, response);
	// }
	// }

	@Bean
	public AuthenticationManager authenticationManager(
			UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		ProviderManager providerManager = new ProviderManager(authenticationProvider);
		providerManager.setEraseCredentialsAfterAuthentication(false);

		return providerManager;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails userDetails = User.builder()// .withDefaultPasswordEncoder()
				.username("user")
				.password(passwordEncoder().encode("password"))
				.roles("USER")
				.build();

		UserDetails admin = User.builder()
				.username("admin")
				.password(passwordEncoder().encode("password"))
				.roles("USER", "ADMIN")
				.build();

		return new InMemoryUserDetailsManager(userDetails, admin);
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder builder) {
		builder.eraseCredentials(false);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}