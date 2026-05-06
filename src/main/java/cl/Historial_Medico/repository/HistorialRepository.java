package cl.Historial_Medico.repository;

import cl.Historial_Medico.model.HistorialMedico;
import cl.Historial_Medico.model.HistorialMedicoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorialRepository extends JpaRepository<HistorialMedico, HistorialMedicoId> {

}
