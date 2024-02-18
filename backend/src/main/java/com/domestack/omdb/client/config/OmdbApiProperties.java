package com.domestack.omdb.client.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "omdb")
@Getter
@Setter
public class OmdbApiProperties {
  private String apiUrl;
  private String apiKey;
}
