package cl.Historial_Medico.WebClient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PacienteClient {
    private final WebClient webClient;

    public String obtenerNombrePaciente(String run) {
        try {
            Map response = webClient
                    .get()
                    .uri("/api/pacientes/" + run)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            return response != null ? (String) response.get("pNombre") : "Sin Paciente";
        } catch (Exception e) {
            return "Sin Paciente";
        }
    }

}
