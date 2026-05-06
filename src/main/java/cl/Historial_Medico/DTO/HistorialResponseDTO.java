package cl.Historial_Medico.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialResponseDTO {

    private Integer numFicha;
    private String run;
    private String nombrePaciente;
    private Date fechaAtencion;
    private String diagnostico;

}
