package cl.Historial_Medico.DTO;


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
