package com.projects.backend.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf->csrf.disable())
		.formLogin(form -> form
			 .loginPage("http://localhost:5173/login"));
		
			// .loginPage("http://localhost:5173/login")
			//.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated()
				//.requestMatchers("http://localhost:5173/login").permitAll()
				//.anyRequest().authenticated()
			//);
			// .httpBasic(Customizer.withDefaults())
			// .formLogin(form -> form
			// .loginPage("http://localhost:5173/login")
			// .permitAll()
		

		return http.build();
	}

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
		UserDetails userDetails = User.builder()//.withDefaultPasswordEncoder()
			.username("user")
			.password(passwordEncoder().encode("password"))
			.roles("USER")
			.build();

			UserDetails admin = User.builder()
        .username("admin")
        .password(passwordEncoder().encode("password"))
        .roles("USER", "ADMIN")
        .build();

		return new InMemoryUserDetailsManager(userDetails,admin);
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