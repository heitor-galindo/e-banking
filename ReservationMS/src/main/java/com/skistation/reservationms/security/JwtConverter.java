package com.skistation.reservationms.security;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

/** The type Jwt converter. */
@Component
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {
  private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
      new JwtGrantedAuthoritiesConverter();

  @Value("${jwt.auth.converter.principal-attribute}")
  private String principalAttribute;

  @Value("${jwt.auth.converter.resource-id}")
  private String resourceId;

  @Override
  public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
    // Extract realm roles
    Stream<GrantedAuthority> realmRoles =
        Optional.ofNullable(jwt.getClaimAsMap("realm_access"))
            .map(map -> (Collection<String>) map.get("roles"))
            .orElse(Collections.emptyList())
            .stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));

    // Extract client (resource) roles
    Stream<GrantedAuthority> clientRoles = extractResourceRoles(jwt).stream();

    // Combine realm + client roles
    Collection<GrantedAuthority> authorities =
        Stream.concat(realmRoles, clientRoles).collect(Collectors.toSet());

    return new JwtAuthenticationToken(jwt, authorities, getPrincipalName(jwt));
  }

  /** Determine the principal name from JWT claims. */
  private String getPrincipalName(Jwt jwt) {
    if (principalAttribute != null && jwt.getClaim(principalAttribute) != null) {
      return jwt.getClaim(principalAttribute);
    }
    return jwt.getSubject(); // fallback to subject
  }

  /** Extract roles assigned to the client/resource. */
  private Collection<GrantedAuthority> extractResourceRoles(Jwt jwt) {
    Map<String, Object> resourceAccess = jwt.getClaim("resource_access");

    if (resourceAccess == null || resourceAccess.get(resourceId) == null) {
      return Set.of();
    }
    Map<String, Object> resource = (Map<String, Object>) resourceAccess.get(resourceId);
    Collection<String> roles = (Collection<String>) resource.get("roles");
    if (roles == null) return Set.of();
    // Convert Keycloak roles like "role_user" -> Spring Security ROLE_USER
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.replace("role_", "").toUpperCase()))
        .collect(Collectors.toSet());
  }
}
