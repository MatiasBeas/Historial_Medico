package cl.Historial_Medico.service;

import cl.Historial_Medico.DTO.HistorialRequestDTO;
import cl.Historial_Medico.DTO.HistorialResponseDTO;
import cl.Historial_Medico.WebClient.PacienteClient;
import cl.Historial_Medico.model.HistorialMedico;
import cl.Historial_Medico.model.HistorialMedicoId;
import cl.Historial_Medico.repository.HistorialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistorialService {
    private final HistorialRepository historialRepository;
    private final PacienteClient pacienteClient;

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

    public List<HistorialResponseDTO> obtenerTodos() {
        return historialRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<HistorialResponseDTO> obtenerPorId(HistorialMedicoId id) {
        return historialRepository.findById(id).map(this::mapToDTO);
    }

    public HistorialResponseDTO guardar(HistorialRequestDTO dto) {
        HistorialMedicoId id = new HistorialMedicoId(null, dto.getPacienteRun());
        HistorialMedico historial = new HistorialMedico(
                id,
                dto.getFechaAtencion(),
                dto.getDiagnostico()
        );
        return mapToDTO(historialRepository.save(historial));
    }

    public Optional<HistorialResponseDTO> actualizar(HistorialMedicoId id, HistorialRequestDTO dto) {
        return historialRepository.findById(id).map(existente -> {
            existente.setFechaAtencion(dto.getFechaAtencion());
            existente.setDiagnostico(dto.getDiagnostico());
            return mapToDTO(historialRepository.save(existente));
        });
    }

    public void eliminar(HistorialMedicoId id) {
        historialRepository.deleteById(id);
    }
}
