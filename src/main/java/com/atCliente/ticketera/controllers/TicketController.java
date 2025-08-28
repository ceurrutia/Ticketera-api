package com.atCliente.ticketera.controllers;

import com.atCliente.ticketera.DTO.TicketDTO;
import com.atCliente.ticketera.enums.Estado;
import com.atCliente.ticketera.services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketDTO> crearTicket(@RequestBody TicketDTO ticketDTO) {
        TicketDTO creado = ticketService.crearTicket(ticketDTO);
        return ResponseEntity.ok(creado);
    }

    @GetMapping
    public List<TicketDTO> obtenerTodos() {
        return ticketService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> obtenerPorId(@PathVariable Long id) {
        TicketDTO ticket = ticketService.obtenerPorId(id);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }

    //patch al estado del ticket
    @PatchMapping("/{id}/estado")
    public ResponseEntity<TicketDTO> cambiarEstado(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Estado estado = Estado.valueOf(body.get("estado"));
        return ResponseEntity.ok(ticketService.cambiarEstado(id, estado));
    }

}
