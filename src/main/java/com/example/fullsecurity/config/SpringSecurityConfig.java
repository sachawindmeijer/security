package com.example.fullsecurity.config;

import com.example.fullsecurity.filter.JwtRequestFilter;
import com.example.fullsecurity.services.CustomUserDetailService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*  Deze security is niet de enige manier om het te doen.
    In de andere branch van deze github repo staat een ander voorbeeld
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    public final CustomUserDetailService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfig(CustomUserDetailService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }



//     Authenticatie met customUserDetailsService en passwordEncoder
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(auth);
    }
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder = new AuthenticationManagerBuilder(
//                http.getSharedObject(AuthenticationConfiguration.class).getObject
//        );
//        authenticationManagerBuilder
//                .userDetailsService(customUserDetailsService)
//                .passwordEncoder(passwordEncoder());
//        return authenticationManagerBuilder.build();
//    }
//


    // Authorizatie met jwt
    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                                auth
                                        // Wanneer je deze uncomments, staat je hele security open. Je hebt dan alleen nog een jwt nodig.
                .requestMatchers("/**").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.GET,"/users").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.POST,"/users/**").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.POST, "/cimodules").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.DELETE, "/cimodules/**").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.POST, "/remotecontrollers").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.DELETE, "/remotecontrollers/**").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.POST, "/televisions").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.DELETE, "/televisions/**").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.POST, "/wallbrackets").hasRole("ADMIN")
                                        .requestMatchers(HttpMethod.DELETE, "/wallbrackets/**").hasRole("ADMIN")
                                        // Je mag meerdere paths tegelijk definieren
                                        .requestMatchers("/cimodules", "/remotecontrollers", "/televisions", "/wallbrackets").hasAnyRole("ADMIN", "USER")
                                        .requestMatchers("/authenticated").authenticated()
                                        .requestMatchers("/authenticate").permitAll()
                                        .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
