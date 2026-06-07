package cl.Historial_Medico.controller;

import cl.Historial_Medico.DTO.HistorialRequestDTO;
import cl.Historial_Medico.DTO.HistorialResponseDTO;
import cl.Historial_Medico.assemblers.HistorialAssembler;
import cl.Historial_Medico.model.HistorialMedicoId;
import cl.Historial_Medico.service.HistorialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/historialMedico")
@RequiredArgsConstructor
@Tag(name = "Gestion de Historial Medico", description = "Endpoints para administrar el historial medico de los pacientes")
public class HistorialController {
    private final HistorialService historialService;
    private final HistorialAssembler historialAssembler;


    //-----------------BUSCAR TODOS LOS HISTORIALES ----------
    @Operation(summary = "Obtener todos los historiales", description = "Retorna una lista completa de todos los historiales medicos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<HistorialResponseDTO>>> obtenerTodos() {
        List<EntityModel<HistorialResponseDTO>> historiales = historialService.obtenerTodos()
                .stream()
                .map(historialAssembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(historiales,
                linkTo(methodOn(HistorialController.class).obtenerTodos()).withSelfRel()));
    }

    //-----------------BUSCAR LOS HISTORIALES POR EL NUMFICHA Y EL RUN (DEBEN COINCIDIR) ----------
    //  {numFicha}/{run}
    @Operation(summary = "Obtener historial por ID", description = "Retorna un historial especifico por numFicha y run")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historial encontrado"),
            @ApiResponse(responseCode = "404", description = "Historial no encontrado")
    })
    @GetMapping("/{numFicha}/{run}")
    public ResponseEntity<EntityModel<HistorialResponseDTO>> obtenerPorId(
            @Parameter(description = "Numero de ficha", example = "1")
            @PathVariable int numFicha,
            @Parameter(description = "RUN del paciente", example = "22.359.190-6")
            @PathVariable String run) {
        HistorialMedicoId id = new HistorialMedicoId(numFicha, run);
        return historialService.obtenerPorId(id)
                .map(historialAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //-----------------GUARDAR HISTORIAL----------
    @Operation(summary = "Crear historial", description = "Crea un nuevo historial medico")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Historial creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos")
    })
    @PostMapping
    public ResponseEntity<EntityModel<HistorialResponseDTO>> crear(
            @Valid @RequestBody HistorialRequestDTO dto) {
        HistorialResponseDTO response = historialService.guardar(dto);
        return ResponseEntity.status(201).body(historialAssembler.toModel(response));
    }

    //-----------------ACTUALIZACION HISTORIAL----------
    // {numFicha}/{run}
    @Operation(summary = "Actualizar historial", description = "Actualiza un historial medico existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historial actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Historial no encontrado")
    })
    @PutMapping("/{numFicha}/{run}")
    public ResponseEntity<EntityModel<HistorialResponseDTO>> actualizar(
            @Parameter(description = "Numero de ficha", example = "1")
            @PathVariable int numFicha,
            @Parameter(description = "RUN del paciente", example = "22.359.190-6")
            @PathVariable String run,
            @Valid @RequestBody HistorialRequestDTO dto) {
        HistorialMedicoId id = new HistorialMedicoId(numFicha, run);
        return historialService.actualizar(id, dto)
                .map(historialAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //-----------------ELIMINAR PACIENTE----------
    // {numFicha}/{run}
    @Operation(summary = "Eliminar historial", description = "Elimina un historial medico")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Historial eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Historial no encontrado")
    })
    @DeleteMapping("/{numFicha}/{run}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Numero de ficha", example = "1")
            @PathVariable int numFicha,
            @Parameter(description = "RUN del paciente", example = "22.359.190-6")
            @PathVariable String run) {
        HistorialMedicoId id = new HistorialMedicoId(numFicha, run);
        if (historialService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        historialService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
