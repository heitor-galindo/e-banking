package com.skistation.reservationms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;

/**
 * The type Feign client config student.
 */
@Configuration
public class FeignClientConfigStudent {

  /**
   * Authorized client manager o auth 2 authorized client manager.
   *
   * @param clientRegistrationRepository the client registration repository
   * @return  the o auth 2 authorized client manager
   */
@Bean
  public OAuth2AuthorizedClientManager authorizedClientManager(
      ClientRegistrationRepository clientRegistrationRepository) {

    OAuth2AuthorizedClientService clientService =
        new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);

    // Basic repository (not based on user principal)
    OAuth2AuthorizedClientRepository repo =
        new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(clientService);

    DefaultOAuth2AuthorizedClientManager manager =
        new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, repo);

    manager.setAuthorizedClientProvider(
        OAuth2AuthorizedClientProviderBuilder.builder()
            .clientCredentials() // Important
            .build());

    return manager;
  }
}
