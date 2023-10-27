package br.com.mateus.commercemanagementsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin")
                .roles("ADMIN", "EMPLOYEE")
                .build();

        UserDetails employee = User.builder()
                .username("employee")
                .password("{noop}employee")
                .roles("EMPLOYEE")
                .build();

        return new InMemoryUserDetailsManager(admin, employee);
    }

    // filters to intercept requests
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                http.authorizeHttpRequests( configurer ->
                        configurer
                                .requestMatchers(HttpMethod.DELETE, "/api/products", "/api/clients", "/api/orders")
                                .hasRole("ADMIN")
                                .anyRequest().hasRole("EMPLOYEE")
                );

                http.httpBasic(Customizer.withDefaults());

                // disable csrf
                http.csrf(csrf -> csrf.disable());

                return http.build();
    }
}
