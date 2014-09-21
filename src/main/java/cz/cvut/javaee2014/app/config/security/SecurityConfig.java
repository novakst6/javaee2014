/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.cvut.javaee2014.app.config.security;

import cz.cvut.javaee2014.service.security.UserEntityDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author Stenlik
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserEntityDetailsService ueds;
    @Autowired
    private ShaPasswordEncoder spe;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("mkyong").password("123456").roles("USER");
	auth.inMemoryAuthentication().withUser("admin").password("123456").roles("ADMIN");
	auth.inMemoryAuthentication().withUser("dba").password("123456").roles("DBA");
        auth
                .userDetailsService(ueds)
                .passwordEncoder(spe);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
        web.ignoring().antMatchers("/css/**","/js/**");
        
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login.htm").permitAll();
        http.authorizeRequests().antMatchers("/resources/**").permitAll();
        http.authorizeRequests().anyRequest().hasAnyRole("USER","DBA","ADMIN","ROLE_USER","ROLE_ADMIN");
        http.csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize")).disable();
        http.csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("/api/**")).disable();
        http.logout().logoutUrl("/logout.htm");
        http.formLogin()
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .loginPage("/login.htm")
                .failureUrl("/login.htm?error=true")
                .defaultSuccessUrl("/index.htm");
                
    }
 
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
