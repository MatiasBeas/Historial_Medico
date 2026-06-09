package cl.Historial_Medico.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${webclient.paciente.url}")
    private String pacienteUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(pacienteUrl)
                .build();
    }
}