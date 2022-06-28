package com.example.ecommercespring.security;

import java.util.Arrays;
import java.util.Collections;

import com.example.ecommercespring.security.jwt.JwtAuthEntryPoint;
import com.example.ecommercespring.security.jwt.JwtAuthTokenFilter;
import com.example.ecommercespring.security.jwt.JwtUtils;
import com.example.ecommercespring.security.services.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    private final UserDetailsServiceImpl userDetailsService;
//
//    private final JwtAuthEntryPoint unauthorizedHandler;
//
//    private final JwtUtils jwtUtils;
//
//    @Autowired
//    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthEntryPoint unauthorizedHandler,
//                             JwtUtils jwtUtils) {
//        this.userDetailsService = userDetailsService;
//        this.unauthorizedHandler = unauthorizedHandler;
//        this.jwtUtils = jwtUtils;
//    }

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtAuthEntryPoint unauthorizedHandler;

    @Autowired
    JwtUtils jwtUtils;

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter(jwtUtils, userDetailsService);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/customer/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/customer/", "/api/brand", "/api/product/**",
                        "/api/discount", "/api/hotsell", "/api/newProduct", "/api/category").permitAll()
                .antMatchers("/api/brand/**").hasRole("ADMIN")
                .antMatchers("/api/category/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/order").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/order/**").hasRole("ADMIN")
                .antMatchers("/api/os/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/detailOS").hasRole("ADMIN")
                .antMatchers("/api/product/**").hasRole("ADMIN")
                .antMatchers("/api/promotion/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/detailPromotion").hasRole("ADMIN")
                .antMatchers("/api/receipt/**").hasRole("ADMIN")
                .antMatchers("/api/shipper/**").hasRole("ADMIN")
                .antMatchers("/api/statistic/**").hasRole("ADMIN")
                .antMatchers("/api/sc/**").hasRole("ADMIN")
                .antMatchers("/api/employee/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/customer").hasRole("ADMIN")
                .antMatchers("/api/order/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/api/checkQuantity").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/api/customer/**").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/api/orderUser/**").hasRole("USER")
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
