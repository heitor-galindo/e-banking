package com.skistation.gateway.configuration;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Gateway config.
 */
@Configuration
public class GatewayConfig {
  /**
   * Log request headers global filter.
   *
   * @return  the global filter
   */
@Bean
  public GlobalFilter logRequestHeaders() {
    return (exchange, chain) -> {
      System.out.println("Headers sent to MS: " + exchange.getRequest().getHeaders());
      return chain.filter(exchange);
    };
  }
}
