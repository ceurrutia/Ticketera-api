package com.atCliente.ticketera.services;

import com.atCliente.ticketera.DTO.BoxDTO;
import com.atCliente.ticketera.entitys.Box;
import com.atCliente.ticketera.enums.BoxEstado;
import com.atCliente.ticketera.respository.BoxRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoxService {
    private final BoxRepository boxRepository;

    public BoxService(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    public List<BoxDTO> obtenerTodosLosBoxes() {
        return boxRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //publico, no puede ser private
    public BoxDTO convertirADTO(Box box) {
        return new BoxDTO(box.getId(), box.getType(), box.getEstado());
    }

    public Box encontrarProximoBoxDisponible() {
        return boxRepository.findFirstByEstado(BoxEstado.LIBRE)
                .orElseThrow(() -> new RuntimeException("No hay boxes disponibles"));
    }

    public void cambiarEstadoBox(Long id, BoxEstado nuevoEstado) {
        Box box = boxRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Box no encontrado con ID: " + id));
        box.setEstado(nuevoEstado);
        boxRepository.save(box);
    }

    //box por entidad
    public Box obtenerBoxEntidad(Long id) {
        return boxRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Box no encontrado con ID: " + id));
    }
}
