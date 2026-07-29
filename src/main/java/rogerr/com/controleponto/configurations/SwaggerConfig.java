package rogerr.com.controleponto.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Controle de Ponto")
                        .description("""
                                API REST para gerenciamento de funcionários, gestores
                                e registros de operações de ponto.
                                
                                Created by Roger Ribeiro Santos
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Roger")));
    }
}
