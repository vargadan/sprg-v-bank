package com.dani.vbank.spring;

import com.dani.vbank.auth.VBankAuthenticationProvider;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.RequestRejectedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(WebSecurityConfig.class);

    @Autowired
    private VBankAuthenticationProvider authProvider;

    @Value("${enableCSRFProtection:false}")
    private Boolean enableCSRFProtection;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //for openshift to access health check
                .authorizeRequests().antMatchers("/actuator/health").permitAll().and()
                //this is how we allow direct access of login without ripping of request parameters
                // so that reflected XSS is possible on login page
                .authorizeRequests().antMatchers("/login").permitAll().and()
                //this is how we allow unauthenticated access of static resources
                .authorizeRequests().antMatchers("/js/**", "/css/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
        //disable CSRF protection
        log.warn("enableCSRFProtection = " + enableCSRFProtection);
        if (!enableCSRFProtection) {
            http.csrf().disable();
            log.warn("CSRF disabled");
        }
        //disable CORS
        http.cors().disable();
        //disable XSS proctection
        http.headers().xssProtection().xssProtectionEnabled(false);
        //disable session fixation procection
        http.sessionManagement().sessionFixation().none();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Override
    public void init(WebSecurity web) throws Exception {
        super.init(web);
        web.httpFirewall(new HttpFirewall() {
            @Override
            public FirewalledRequest getFirewalledRequest(HttpServletRequest request) throws RequestRejectedException {
                return new FirewalledRequest(request) {
                    @Override
                    public void reset() {
                    }
                };
            }

            @Override
            public HttpServletResponse getFirewalledResponse(HttpServletResponse response) {
                return response;
            }
        });
    }

}