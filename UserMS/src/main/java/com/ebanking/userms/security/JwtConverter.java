package com.ebanking.userms.security;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

@Component
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {
  private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
      new JwtGrantedAuthoritiesConverter();

  @Value("${jwt.auth.converter.principal-attribute}")
  private String principleAttribute;

  @Value("${jwt.auth.converter.resource-id}")
  private String resourceId;

  @Override
  public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
    Stream<GrantedAuthority> realmRoles =
        Optional.ofNullable(jwt.getClaimAsMap("realm_access"))
            .map(map -> (Collection<String>) map.get("roles"))
            .orElse(Collections.emptyList())
            .stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
    Stream<GrantedAuthority> clientRoles = extractResourceRoles(jwt).stream();
    Collection<GrantedAuthority> authorities =
        Stream.concat(realmRoles, clientRoles).collect(Collectors.toSet());
    return new JwtAuthenticationToken(jwt, authorities, getPrincipalName(jwt));
  }

  private String getPrincipalName(Jwt jwt) {
    if (principleAttribute != null && jwt.getClaim(principleAttribute) != null) {
      return jwt.getClaim(principleAttribute);
    }
    return jwt.getSubject();
  }

  private Collection<GrantedAuthority> extractResourceRoles(Jwt jwt) {
    Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
    if (resourceAccess == null || resourceAccess.get(resourceId) == null) {
      return Set.of();
    }
    Map<String, Object> resource = (Map<String, Object>) resourceAccess.get(resourceId);
    Collection<String> roles = (Collection<String>) resource.get("roles");
    if (roles == null) return Set.of();
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.replace("role_", "").toUpperCase()))
        .collect(Collectors.toSet());
  }
}
