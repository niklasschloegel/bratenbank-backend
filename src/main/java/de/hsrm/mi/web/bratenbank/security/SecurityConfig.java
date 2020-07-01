package de.hsrm.mi.web.bratenbank.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    BenutzerUserDetailService userDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder pwenc = getPasswordEncoder();

//        auth.inMemoryAuthentication()
//                .withUser("friedfert")
//                .password(pwenc.encode("dingdong"))
//                .roles("USER")
//            .and()
//                .withUser("joghurta")
//                .password(pwenc.encode("geheim123"))
//                .roles("USER");


        auth.userDetailsService(userDetailService)
                .passwordEncoder(pwenc)
            .and()
                .inMemoryAuthentication()
                .withUser("root")
                .password(pwenc.encode("sudologin"))
                .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/benutzer/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/stompbroker**").permitAll()
                .antMatchers("/braten/**").authenticated()
                .anyRequest().hasRole("ADMIN")
            .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/braten/angebot")
                .permitAll()
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll();
    }
}
