package com.domestack.omdb.client.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class OmdbRequestInterceptor implements RequestInterceptor {
    private final OmdbApiProperties omDbApiProperties;

    public OmdbRequestInterceptor(OmdbApiProperties omDbApiProperties) {
        this.omDbApiProperties = omDbApiProperties;
    }

    @Override
    public void apply(RequestTemplate template) {
        template.query("apikey", omDbApiProperties.getApiKey());
    }
}