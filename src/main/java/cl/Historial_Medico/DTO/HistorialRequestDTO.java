package cl.Historial_Medico.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialRequestDTO {

    @NotBlank(message = "El RUN del paciente no puede estar VACIO")
    private String pacienteRun;

    @NotNull(message = "La FECHA es OBLIGATORIA")
    private Date fechaAtencion;

    @NotBlank(message = "El DIAGNOSTICO no puede estar VACIO")
    private String diagnostico;
}
