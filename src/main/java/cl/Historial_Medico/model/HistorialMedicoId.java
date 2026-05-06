package cl.Historial_Medico.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class HistorialMedicoId implements Serializable {
    private Integer numFicha;
    private String Run;

}
