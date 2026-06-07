package cl.Historial_Medico.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos retornados de un historial medico")
public class HistorialResponseDTO {

    @Schema(description = "Numero de ficha del historial", example = "1")
    private Integer numFicha;

    @Schema(description = "RUN del paciente", example = "22.359.190-6")
    private String run;

    @Schema(description = "Nombre del paciente", example = "Matias")
    private String nombrePaciente;

    @Schema(description = "Fecha de atencion", example = "2024-06-01")
    private Date fechaAtencion;

    @Schema(description = "Diagnostico del paciente", example = "Resfriado comun")
    private String diagnostico;
}