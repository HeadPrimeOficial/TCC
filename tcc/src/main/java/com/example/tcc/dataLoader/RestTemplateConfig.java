package com.example.tcc.dataLoader;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    /**
     * Define o bean RestTemplate para ser injetado em outros serviços,
     * como o NominatimService.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}