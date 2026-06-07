package cl.Historial_Medico.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos requeridos para crear o actualizar un historial medico")
public class HistorialRequestDTO {

    @NotBlank(message = "El RUN del paciente no puede estar VACIO")
    @Schema(description = "RUN del paciente", example = "22.359.190-6")
    private String pacienteRun;

    @NotNull(message = "La FECHA es OBLIGATORIA")
    @Schema(description = "Fecha de atencion", example = "2024-06-01")
    private Date fechaAtencion;

    @NotBlank(message = "El DIAGNOSTICO no puede estar VACIO")
    @Schema(description = "Diagnostico del paciente", example = "Resfriado comun")
    private String diagnostico;
}