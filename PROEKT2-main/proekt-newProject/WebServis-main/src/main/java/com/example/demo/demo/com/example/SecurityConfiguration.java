package com.example.demo.demo.com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.demo.com.example.config.JwtAuthenticationFilter;


import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    
        private final JwtAuthenticationFilter jwtAuthFilter;
                private final AuthenticationProvider authenticationProvider;
                
                @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                    http
                            .csrf(csrf -> csrf
                                    .disable())
                            .authorizeRequests(requests -> requests // 'authorizeRequests' is used in 5.x, not 'authorizeHttpRequests'
                                    .antMatchers("/api/v1/auth/**") // Use antMatchers in Spring Security 5.x
                                    .permitAll()
                                    .antMatchers("/h2-console/**").permitAll()
                                    .antMatchers("/destinations/**").permitAll()
                                    .antMatchers("/ws/**").permitAll()
                                    .antMatchers("/index.html").permitAll()
                                    .antMatchers("/register.html").permitAll()
                                    .antMatchers("/unregister.html").permitAll()
                                    .antMatchers("/login.html").permitAll()
                                    .antMatchers("/user.html").permitAll()
                                    .antMatchers("/admin.html").permitAll()
                                    .anyRequest()
                                    .authenticated())
                            .sessionManagement(management -> management
                                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                            .authenticationProvider(authenticationProvider)
                            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}

          

}
