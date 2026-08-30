package com.etec.tourtripapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI tourTripOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tour Trip API")
                        .description("REST API for tour booking, payment, invoice, receipt, and participant management")
                        .version("1.0.0"));
    }
}
