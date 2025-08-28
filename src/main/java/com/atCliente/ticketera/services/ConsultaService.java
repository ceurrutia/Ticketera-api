package com.atCliente.ticketera.services;

import com.atCliente.ticketera.DTO.ConsultaDTO;
import com.atCliente.ticketera.entitys.Consulta;
import com.atCliente.ticketera.enums.Tipo_consulta;
import com.atCliente.ticketera.respository.ConsultaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultaService {
    private final ConsultaRepository consultaRepository;

    public ConsultaService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }
    // Crear o actualizar consulta
    public ConsultaDTO guardarConsulta(ConsultaDTO consultaDTO) {
        Consulta consulta = new Consulta();
        consulta.setId(consultaDTO.getId()); // si es null, JPA hace insert
        consulta.setTipoConsulta(consultaDTO.getTipoConsulta());

        Consulta guardada = consultaRepository.save(consulta);
        return convertirADTO(guardada);
    }

    // Listar todas las consultas
    public List<ConsultaDTO> listarConsultas() {
        return consultaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar por ID
    public ConsultaDTO buscarPorId(Long id) {
        return consultaRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException("Consulta no encontrada con ID: " + id));
    }

    // Buscar por Tipo_consulta
    public Optional<ConsultaDTO> buscarPorTipo(Tipo_consulta tipoConsulta) {
        return consultaRepository.findByTipoConsulta(tipoConsulta)
                .map(this::convertirADTO);
    }

    // Mapper privado
    private ConsultaDTO convertirADTO(Consulta consulta) {
        return new ConsultaDTO(
                consulta.getId(),
                consulta.getTipoConsulta()
        );
    }
}
