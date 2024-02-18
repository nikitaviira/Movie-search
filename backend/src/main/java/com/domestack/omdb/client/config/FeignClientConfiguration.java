package com.domestack.omdb.client.config;

import com.domestack.omdb.client.OmdbClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import feign.Feign;
import feign.Logger.Level;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static feign.Logger.Level.BASIC;

@Configuration
public class FeignClientConfiguration {
    @Bean
    @Profile("dev")
    Level feignLoggerLevel() {
        return BASIC;
    }

    @Bean
    public Decoder feignDecoder() {
        HttpMessageConverter<Object> jacksonConverter = new MappingJackson2HttpMessageConverter(objectMapper());

        HttpMessageConverters httpMessageConverters = new HttpMessageConverters(jacksonConverter);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> httpMessageConverters;

        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }

    public ObjectMapper objectMapper(){
        var objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule().addDeserializer(String.class, new EmptyStringDeserializer());
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    @Bean
    public OmdbClient omdbClient(OmdbApiProperties omDbApiProperties,
                                 OmdbRequestInterceptor omdbRequestInterceptor) {
        return Feign.builder()
            .contract(new SpringMvcContract())
            .requestInterceptor(omdbRequestInterceptor)
            .target(OmdbClient.class, omDbApiProperties.getApiUrl());
    }
}