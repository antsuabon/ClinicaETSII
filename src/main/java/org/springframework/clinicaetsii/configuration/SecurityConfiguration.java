package org.springframework.clinicaetsii.configuration;

import java.time.Duration;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.client.RestTemplate;

/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity

@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		http.addFilterBefore(new EncodingFilter(), ChannelProcessingFilter.class);

		http.authorizeRequests()
				.antMatchers("/resources/**", "/webjars/**", "/h2-console/**", "/monitoring/**")
				.permitAll().antMatchers(HttpMethod.GET, "/", "/oups").permitAll()
				.antMatchers("/anonymous/**").permitAll().antMatchers("/patient/**")
				.hasAnyAuthority("patient").antMatchers("/doctor/**").hasAnyAuthority("doctor")
				.antMatchers("/administrative/**").hasAnyAuthority("administrative")
				.antMatchers("/admin/**").hasAnyAuthority("admin")
				// .antMatchers("/users/new").permitAll()
				// .antMatchers("/owners/**").hasAnyAuthority("owner","admin")
				// .antMatchers("/vets/**").authenticated()
				.anyRequest().denyAll().and().formLogin()
				/* .loginPage("/login") */
				.failureUrl("/login-error").and().logout().logoutSuccessUrl("/");
		// Configuración para que funcione la consola de administración
		// de la BD H2 (deshabilitar las cabeceras de protección contra
		// ataques de tipo csrf y habilitar los framesets si su contenido
		// se sirve desde esta misma página.
		http.csrf().ignoringAntMatchers("/h2-console/**");
		http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(this.dataSource)
				.usersByUsernameQuery(
						"select username,password,enabled from users where username = ?")
				.authoritiesByUsernameQuery(
						"select * from authorities a where exists (select * from users u where a.user_id = u.id and u.username= ? )")
				.passwordEncoder(passwordEncoder());
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public RestTemplate restTemplate(final RestTemplateBuilder builder) {

		return builder.setConnectTimeout(Duration.ofMillis(10 * 1000l))
				.setReadTimeout(Duration.ofMillis(10 * 1000l)).build();
	}
}
