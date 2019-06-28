package com.arm.atm.configuration.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.arm.atm.filter.security.TokenAuthFilter;
import com.arm.atm.resource.security.AuthorizationLevel;
import com.arm.atm.service.auth.LoginServiceImpl;
import com.arm.atm.service.auth.UserDetailsServiceImpl;
import com.arm.atm.service.data.UserServiceImpl;

/**
 * Configuration for the security layer for the API
 * 
 * @author jonathasmoraes
 *
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private LoginServiceImpl loginService;
	
	@Autowired
	private UserServiceImpl userService;

	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	/**
	 * Configurations for API's authorization layer within security
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.authorizeRequests()
			.antMatchers("/atm/**").hasAuthority(AuthorizationLevel.USER.toString())
			.antMatchers("/account/**").hasAuthority(AuthorizationLevel.ADMIN.toString())
			.antMatchers("/bank/**").hasAuthority(AuthorizationLevel.ADMIN.toString())
			.antMatchers("/user/**").hasAuthority(AuthorizationLevel.ADMIN.toString())
			.antMatchers("/h2-console/**").permitAll()
			.antMatchers(HttpMethod.POST, "/auth").permitAll()
			.antMatchers(HttpMethod.POST, "/auth/atm").permitAll()
			.anyRequest().authenticated()
		.and()
			.csrf().disable()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.addFilterBefore(new TokenAuthFilter(loginService, userService), UsernamePasswordAuthenticationFilter.class);
	}
	
	/**
	 * Configurations for API's authentication layer within security
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsServiceImpl)
			.passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	/**
	 * Configurations for API's Cross-origin resource sharing, setting allowed methods, headers and origins.
	 * 
	 * @return a UrlBasedCorsConfigurationSource object for the API root address
	 */
	@Bean
	protected CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("*"));
	    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
	    configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
	    configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
	    configuration.setAllowCredentials(true);
	
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	
	    return source;
   }
	
}
