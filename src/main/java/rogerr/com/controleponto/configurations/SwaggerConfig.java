package rogerr.com.controleponto.configurations;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI().components(new Components())
                .info(new Info()
                .title("Controle de Ponto")
                .description("Projeto")
                .version("v1"));

    }
}
