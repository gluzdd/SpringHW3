package ru.gb.springdemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)  throws Exception {
        return httpSecurity
                .authorizeHttpRequests(configurer -> configurer
//                        .requestMatchers("/users/**").hasAuthority("admin")
//                        .requestMatchers("/readers/**").hasAuthority("reader")
//                        .requestMatchers("/issue/**").hasAuthority("admin")
//                        .requestMatchers("/books/**").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(Customizer.withDefaults())
                //.httpBasic(Customizer.withDefaults())
                .csrf(it->it.disable())
                .build();
    }
}
