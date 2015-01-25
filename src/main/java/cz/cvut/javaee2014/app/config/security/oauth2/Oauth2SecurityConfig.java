/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.javaee2014.app.config.security.oauth2;

import cz.cvut.javaee2014.service.security.UserEntityDetailsService;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

/**
 *
 * @author Stenlik
 */
@Configuration
public class Oauth2SecurityConfig {

    private static final String RESOURCE_ID = "restapi";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends
            ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            // @formatter:off
            resources
                    .resourceId(RESOURCE_ID);
            // @formatter:on
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
//            // @formatter:off                       
            http
                    .requestMatchers().antMatchers("/resources/**")
                    .and()
                    .authorizeRequests()
                    .antMatchers("/resources/**").authenticated();

            //http.sessionManagement().sessionFixation().none();
        }

    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends
            AuthorizationServerConfigurerAdapter {

        @Autowired
        private UserEntityDetailsService ueds;

        @Autowired
        private DataSource tokenDataSource;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
 
            clients
                    .inMemory()
                    .withClient("clientapp")
                    .authorizedGrantTypes("client_credentials")
                    .authorities("USER")
                    .scopes("read", "write")
                    .resourceIds(RESOURCE_ID)
                    .secret("123456");
        }

        @Bean
        public ApprovalStore approvalStore() {
            return new JdbcApprovalStore(tokenDataSource);
        }

        @Bean
        public TokenStore tokenStore() {
            return new JdbcTokenStore(tokenDataSource);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.clientDetailsService(ueds);
            endpoints.tokenStore(tokenStore()).approvalStore(approvalStore());
        }

    }
}
