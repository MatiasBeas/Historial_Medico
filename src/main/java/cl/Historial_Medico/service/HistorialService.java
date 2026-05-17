package cl.Historial_Medico.service;

import cl.Historial_Medico.DTO.HistorialRequestDTO;
import cl.Historial_Medico.DTO.HistorialResponseDTO;
import cl.Historial_Medico.WebClient.PacienteClient;
import cl.Historial_Medico.model.HistorialMedico;
import cl.Historial_Medico.model.HistorialMedicoId;
import cl.Historial_Medico.repository.HistorialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistorialService {
    private final HistorialRepository historialRepository;
    private final PacienteClient pacienteClient;

    //-----------------MAPEO PRIVADO: HISTORIAL -> ResponseDTO----------
    private HistorialResponseDTO mapToDTO(HistorialMedico historial) {
        String nombrePaciente = pacienteClient
                .obtenerNombrePaciente(historial.getId().getRun());
        return new HistorialResponseDTO(
                historial.getId().getNumFicha(),
                historial.getId().getRun(),
                nombrePaciente,
                historial.getFechaAtencion(),
                historial.getDiagnostico()
        );
    }

    //-----------------BUSCAR HISTORIALES DE DISTINTAS FORMAS----------
    public List<HistorialResponseDTO> obtenerTodos() {
        log.info("Obteniendo TODOS los historiales");
        return historialRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<HistorialResponseDTO> obtenerPorId(HistorialMedicoId id) {
        log.info("Obteniendo el Historial de ID: "+ id);
        return historialRepository.findById(id).map(this::mapToDTO);
    }

    //-----------------GUARDAR HISTORIAL----------
    public HistorialResponseDTO guardar(HistorialRequestDTO dto) {
        log.info("Guardando un nuevo historial");
        long nextId = historialRepository.count() + 1;
        HistorialMedicoId id = new HistorialMedicoId((int) nextId, dto.getPacienteRun());
        HistorialMedico historial = new HistorialMedico(
                id,
                dto.getFechaAtencion(),
                dto.getDiagnostico()
        );
        return mapToDTO(historialRepository.save(historial));
    }

    //-----------------ACTUALIZACION HISTORIAL----------
    public Optional<HistorialResponseDTO> actualizar(HistorialMedicoId id, HistorialRequestDTO dto) {
        log.info("Actualizando el Historial Medico con ID: " + id);
        return historialRepository.findById(id).map(existente -> {
            existente.setFechaAtencion(dto.getFechaAtencion());
            existente.setDiagnostico(dto.getDiagnostico());
            return mapToDTO(historialRepository.save(existente));
        });
    }

    //-----------------ELIMINAR HISTORIAL----------
    public void eliminar(HistorialMedicoId id) {
        historialRepository.deleteById(id);
        log.info("Eliminando el historial con ID" + id);
    }
}
