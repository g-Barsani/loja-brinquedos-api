package edu.meialua.kidsgrace.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private CustomUserDetailsService userDetailsService;
    private JwtAuthEntryPoint authEntryPoint;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthEntryPoint authEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.authEntryPoint = authEntryPoint;
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .authentrypoint
//                        .requestMatchers(HttpMethod.GET, "/toys/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.POST, "/users/**").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/users/login").permitAll()
//                        .requestMatchers("/swagger-ui/**").permitAll()
//                        .anyRequest().permitAll() // Permite tudo
//                )
//
//
//
////                .httpBasic(withDefaults());
//                .formLogin(form -> form.disable()) // Desativa login por formulário
//                .httpBasic(basic -> basic.disable()); // Desativa Basic Auth
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(ex ->
                        ex.authenticationEntryPoint(authEntryPoint) // Set your custom entry point
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/toys/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/users/").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/login").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }



//    @Bean
//    public UserDetailsService users(){
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{noop}password")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{noop}password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration) throws Exception{

            return authenticationConfiguration.getAuthenticationManager();
        }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
