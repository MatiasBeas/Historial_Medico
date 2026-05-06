package cl.Historial_Medico.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Historial_Medico")

public class HistorialMedico {
    @EmbeddedId
    private HistorialMedicoId id;

    @Column(nullable = false)
    private Date fechaAtencion;

    @Column(nullable = false)
    private String diagnostico;

}
