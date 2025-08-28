package com.atCliente.ticketera.controllers;

import com.atCliente.ticketera.DTO.ConsultaDTO;
import com.atCliente.ticketera.enums.Tipo_consulta;
import com.atCliente.ticketera.services.ConsultaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultas")
public class ConsultaController {
    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    // Crear o actualizar consulta
    @PostMapping
    public ResponseEntity<ConsultaDTO> guardarConsulta(@RequestBody ConsultaDTO consultaDTO) {
        ConsultaDTO guardada = consultaService.guardarConsulta(consultaDTO);
        return ResponseEntity.ok(guardada);
    }

    // Listar todas las consultas
    @GetMapping
    public ResponseEntity<List<ConsultaDTO>> listarConsultas() {
        List<ConsultaDTO> consultas = consultaService.listarConsultas();
        return ResponseEntity.ok(consultas);
    }

    // Buscar consulta por ID
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> buscarPorId(@PathVariable Long id) {
        ConsultaDTO consulta = consultaService.buscarPorId(id);
        return ResponseEntity.ok(consulta);
    }

    // Buscar consulta por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<ConsultaDTO> buscarPorTipo(@PathVariable Tipo_consulta tipo) {
        return consultaService.buscarPorTipo(tipo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
