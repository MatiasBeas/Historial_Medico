package cl.Historial_Medico.controller;

import cl.Historial_Medico.DTO.HistorialRequestDTO;
import cl.Historial_Medico.DTO.HistorialResponseDTO;
import cl.Historial_Medico.model.HistorialMedicoId;
import cl.Historial_Medico.service.HistorialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historialMedico")
@RequiredArgsConstructor
public class HistorialController {
    private final HistorialService historialService;


    //-----------------BUSCAR HISTORIALES DE DISTINTAS FORMAS----------
    @GetMapping
    public ResponseEntity<List<HistorialResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(historialService.obtenerTodos());
    }

    //  {numFicha}/{run}
    @GetMapping("/{numFicha}/{run}")
    public ResponseEntity<HistorialResponseDTO> obtenerPorId(
            @PathVariable int numFicha,
            @PathVariable String run) {
        HistorialMedicoId id = new HistorialMedicoId(numFicha, run);
        return historialService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //-----------------GUARDAR HISTORIAL----------
    @PostMapping
    public ResponseEntity<HistorialResponseDTO> crear(
            @Valid @RequestBody HistorialRequestDTO dto) {
        HistorialResponseDTO response = historialService.guardar(dto);
        return ResponseEntity.status(201).body(response);
    }

    //-----------------ACTUALIZACION HISTORIAL----------
    // {numFicha}/{run}
    @PutMapping("/{numFicha}/{run}")
    public ResponseEntity<HistorialResponseDTO> actualizar(
            @PathVariable int numFicha,
            @PathVariable String run,
            @Valid @RequestBody HistorialRequestDTO dto) {
        HistorialMedicoId id = new HistorialMedicoId(numFicha, run);
        return historialService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //-----------------ELIMINAR PACIENTE----------
    // {numFicha}/{run}
    @DeleteMapping("/{numFicha}/{run}")
    public ResponseEntity<Void> eliminar(
            @PathVariable int numFicha,
            @PathVariable String run) {
        HistorialMedicoId id = new HistorialMedicoId(numFicha, run);
        if (historialService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        historialService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
