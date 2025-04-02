package com.flavioramses.huellitasbackend.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI huellitasOpenAPI() {
        Server localServer = new Server()
                .url("http://localhost:8080")
                .description("Servidor de desarrollo local");

        Server productionServer = new Server()
                .url("https://independent-eagerness-production.up.railway.app")
                .description("Servidor de producción");

        Contact contact = new Contact()
                .name("Equipo Huellitas")
                .email("info@huellitas.com")
                .url("https://grupo-4-proyecto-integrador-dh-frontend-1ep1.vercel.app");

        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("API de Huellitas")
                .version("1.0")
                .description("API para la gestión de alojamientos, reservas y mascotas del sistema Huellitas")
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer, productionServer));
    }
} 