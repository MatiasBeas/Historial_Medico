package cl.Historial_Medico.assemblers;

import cl.Historial_Medico.DTO.HistorialResponseDTO;
import cl.Historial_Medico.controller.HistorialController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class HistorialAssembler implements RepresentationModelAssembler<HistorialResponseDTO, EntityModel<HistorialResponseDTO>> {

    @Override
    public EntityModel<HistorialResponseDTO> toModel(HistorialResponseDTO historial) {
        return EntityModel.of(historial,
                linkTo(methodOn(HistorialController.class)
                        .obtenerPorId(historial.getNumFicha(), historial.getRun())).withSelfRel(),
                linkTo(methodOn(HistorialController.class)
                        .obtenerTodos()).withRel("todos-los-historiales"));
    }
}