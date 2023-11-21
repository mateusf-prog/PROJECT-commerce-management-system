package br.com.mateus.commercemanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests((authorize) -> {
                        authorize.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN");
                        authorize.anyRequest().authenticated();
                    })
            .formLogin(Customizer.withDefaults());

        return http.build();
    }
}