package br.com.alysonrodrigo.apimoutstiorders.config;

import br.com.alysonrodrigo.apimoutstiorders.mapper.TaxMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public TaxMapper taxMapper() {
        return new TaxMapper();
    }
}
