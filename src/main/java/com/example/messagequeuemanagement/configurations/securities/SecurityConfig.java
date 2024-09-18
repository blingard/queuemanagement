package com.example.messagequeuemanagement.configurations.securities;

import com.example.messagequeuemanagement.implementations.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomAuthenticationSuccessHandler successHandler;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthenticationFilter jwtAuthFilter, CustomAuthenticationSuccessHandler successHandler, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
        this.successHandler = successHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        /*http
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests(
                        auth -> {
                            auth
                                    //.requestMatchers("/admin/plugins/**").permitAll()
                                    //.requestMatchers("/admin/dist/**").permitAll()
                                    .requestMatchers("/monitoring").permitAll()
                                    .requestMatchers("/ticket").permitAll()
                                    .requestMatchers("/cashier/login").permitAll()
                                    .requestMatchers("/login").permitAll()
                                    //.requestMatchers("/admin/login").permitAll()
                                    //.requestMatchers("/admin/profile").permitAll();
                                    .requestMatchers("/admin/**").permitAll().anyRequest().permitAll()
                            ;
                                    //.requestMatchers("/admin/actions/**").hasAuthority("ADMIN")
                                    //.requestMatchers("/cashier/actions/**").hasAnyAuthority("SELLER","TELLER","CASHIER");
                        }
                ).formLogin(
                        form -> form
                                .loginPage("/admin/login")
                                .loginProcessingUrl("/admin/login")
                                .successHandler(successHandler)
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()

                );*/
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> {
                            auth
                                    .requestMatchers(
                                            "/test/**",
                                            "/service",
                                            "/service/list-by-cp-id/**",
                                            "/api/article/active",
                                            "//swagger-ui-custom.html",
                                            "/v3/api-docs/**",
                                            "/api/article/get/**",
                                            "/api/article/active/**",
                                            "/api/article/active/home",
                                            "/api/article/get-by-categorie/**",
                                            "/api/centrepartenaire/getById/**",
                                            "/api/centrepartenaire/localisation",
                                            "/user/register",
                                            "/denonciation",
                                            "/message/save",
                                            "/user/register",
                                            "/denonciation/list/**",
                                            "/api/file/**",
                                            "/api/missions",
                                            "/generate-pdf/**",
                                            "/api/missions/active/**",
                                            "/api/missions/active/home",
                                            "/api/missions/find-by-id/**",
                                            "/produit/getById/**",
                                            "/rapport/getById/**",
                                            "/rapport/list/**",
                                            "/api/valeur/find-active",
                                            "/api/auth/login",
                                            "/api/parametres/get/**",
                                            "/api/temoignage/active",
                                            "/api/mediatech/active",
                                            "/api/temoignage/active/home",
                                            "/api/temoignage/getById/**")
                                    .permitAll()
                                    .requestMatchers(HttpMethod.OPTIONS, "/**")
                                    .permitAll()
                                    .anyRequest()
                                    .permitAll();//authenticated();
                        }
                )
                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }




    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

}
