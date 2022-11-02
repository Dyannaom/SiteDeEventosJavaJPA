package br.com.gft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import br.com.gft.services.UsuarioService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

	final UsuarioService usuarioService;

	public WebSecurityConfig(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http

				.httpBasic()
				.and()
				.authorizeRequests()
				
				   .antMatchers ("/").permitAll()
	                .antMatchers ("webjars/**", "/css/**", "/images/**", "/js/**" ).permitAll()
	                .antMatchers ("/usuario/*").permitAll()
	                .antMatchers ("/evento/").permitAll()
	                .antMatchers ("/evento/ranking*").permitAll()
	                .antMatchers ("/atividade/*").permitAll()

	                
	                .anyRequest().authenticated()
	                .and()
	                    .formLogin()
	                    .loginPage("/usuario")
	                    .defaultSuccessUrl("/", true)
	                    .failureUrl("/usuario-error")
	                    .permitAll()
	                .and()
	                    .logout()
	                    .logoutSuccessUrl("/")
	                .and()
	                    .exceptionHandling()
	                    .accessDeniedPage("/acesso-negado");          
			
		return http.build();

	}



	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();

	}
	
	 public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
	        authenticationManagerBuilder.userDetailsService(usuarioService).passwordEncoder(new BCryptPasswordEncoder());
	      }
	    
	    
	    
	    @Bean
	    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {        
	            
	    return (web) -> web.ignoring().antMatchers("/");
	    }

}
