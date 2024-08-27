package org.springframework.samples.petclinic.system;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

	@Bean
	@Order(99)
	public SecurityFilterChain legacyui(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
//			.requestMatchers("/api/owners/by-key-fields").denyAll()
			.anyRequest().authenticated());
		http.headers(Customizer.withDefaults());
		http.sessionManagement(Customizer.withDefaults());
		http.formLogin(Customizer.withDefaults());
		http.oauth2Login(o2l -> o2l.userInfoEndpoint(uie ->
			uie.userAuthoritiesMapper(userAuthoritiesMapper())));
		http.anonymous(Customizer.withDefaults());
		http.csrf(CsrfConfigurer::disable);
		http.userDetailsService(inMemoryUserDetailsService());
		return http.build();
	}



	public UserDetailsService inMemoryUserDetailsService() {
		User.UserBuilder users = User.builder();
		InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
		userDetailsManager.createUser(users.username("admin")
			.password("{noop}admin")
			.roles("ADMIN")
			.build());
		userDetailsManager.createUser(users.username("user")
			.password("{noop}user")
			.roles("USER")
			.build());
		return userDetailsManager;
	}

	public GrantedAuthoritiesMapper userAuthoritiesMapper() {
		return (authorities) -> {
			Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
			authorities.forEach(authority -> {
				//TODO Map roles
//				if (authority instanceof OidcUserAuthority){
//					OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
//					JSONArray keycloakRoles = (JSONArray) oidcUserAuthority.getAttributes().get("roles");
//					keycloakRoles.forEach(role -> mappedAuthorities.add(new SimpleGrantedAuthority((String) role)));
//				} else {
//					mappedAuthorities.add(authority);
//				}
			});
			return mappedAuthorities;
		};
	}

	@Bean
	@Order(10)
	public SecurityFilterChain api(HttpSecurity http) throws Exception {
		http.securityMatcher("/api/**");
		http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
			.requestMatchers("/api/owners/**").hasRole("BUG")
			.anyRequest().authenticated());
		http.headers(Customizer.withDefaults());
		http.oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
			.jwt(jwtConfigurer -> jwtConfigurer
				.jwkSetUri("http://localhost:9081/realms/petclinic/protocol/openid-connect/certs")));
		http.anonymous(Customizer.withDefaults());
		http.csrf(CsrfConfigurer::disable);
		return http.build();
	}

//	@Bean
//	@Order(20)
	public SecurityFilterChain adminui(HttpSecurity http) throws Exception {
		http.securityMatcher("/adminui/*");
		http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
			.anyRequest().authenticated());
		http.headers(Customizer.withDefaults());
		http.oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
			.jwt(jwtConfigurer -> jwtConfigurer
				.jwkSetUri("http://localhost:9081/realms/petclinic-backoffice/protocol/openid-connect/certs")));
		http.anonymous(Customizer.withDefaults());
		http.csrf(CsrfConfigurer::disable);
		return http.build();
	}
}
