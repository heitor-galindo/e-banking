package com.ebanking.userms.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {

  private final OAuth2AuthorizedClientManager manager;

  public OAuth2FeignRequestInterceptor(OAuth2AuthorizedClientManager manager) {
    this.manager = manager;
  }

  @Override
  public void apply(RequestTemplate template) {
    var client =
        manager.authorize(
            OAuth2AuthorizeRequest.withClientRegistrationId("user-service")
                .principal("user-service")
                .build());
    if (client != null && client.getAccessToken() != null) {
      String tokenValue = client.getAccessToken().getTokenValue();
      template.header("Authorization", "Bearer " + tokenValue);
    }
  }
}
