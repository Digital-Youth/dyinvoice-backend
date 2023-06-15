package com.dyinvoice.backend.security;


import com.dyinvoice.backend.controller.ControllerVariables;
import com.dyinvoice.backend.model.entity.EntitiesRoleName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    private JwtAuthenticationFilter authenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtAuthenticationEntryPoint authenticationEntryPoint,
                          JwtAuthenticationFilter authenticationFilter){
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors();

        http.csrf().disable();

        http.headers().frameOptions().sameOrigin();

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                http.authorizeRequests()
                    .antMatchers(HttpMethod.GET, ControllerVariables.userAntPatterns).permitAll();

        http.authorizeRequests()
                        .antMatchers(HttpMethod.POST, ControllerVariables.userAntPatterns).permitAll();
        http.authorizeRequests()
                    .antMatchers(ControllerVariables.devAntPatterns).permitAll();

        http.authorizeRequests()
                .antMatchers(ControllerVariables.userAntPatterns)
                .hasAnyRole(new String[]{EntitiesRoleName.ROLE_USER, EntitiesRoleName.ROLE_ADMIN, EntitiesRoleName.ROLE_SUPER_ADMIN});

        http.authorizeRequests()
                .antMatchers(ControllerVariables.staffAntPatterns)
                .hasAnyRole(new String[]{EntitiesRoleName.ROLE_USER, EntitiesRoleName.ROLE_ADMIN});

        http.authorizeRequests()
                .anyRequest()
                .authenticated();

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }



}
