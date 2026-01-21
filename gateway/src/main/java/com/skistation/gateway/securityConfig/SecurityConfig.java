package com.skistation.gateway.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

/**
 * The type Security config.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  /**
   * Spring security filter chain security web filter chain.
   *
   * @param http the http
   * @return  the security web filter chain
   */
@Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
        .authorizeExchange(
            auth -> auth.pathMatchers("/actuator/**").permitAll().anyExchange().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
        .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
        .build();
  }
  //  private final JwtConverter jwtConverter = new JwtConverter();
  //
  //  @Bean
  //  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
  //    return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
  //        .authorizeExchange(
  //            auth ->
  //                auth
  //                    // Only users with ADMIN role can access student endpoints
  //                    .pathMatchers("/students/**")
  //                    .hasRole("ADMIN")
  //                    // All other endpoints under /msstudentservice/** require authentication
  //                    .pathMatchers("/**")
  //                    .authenticated())
  //        .oauth2ResourceServer(
  //            oauth2 ->
  //                oauth2.jwt(
  //                    jwt ->
  //                        jwt.jwtAuthenticationConverter(
  //                            new ReactiveJwtAuthenticationConverterAdapter(
  //                                jwtConverter)) // Converts JWT claims to Spring Security
  // authorities
  //                    ))
  //        .build();
  //  }
}
