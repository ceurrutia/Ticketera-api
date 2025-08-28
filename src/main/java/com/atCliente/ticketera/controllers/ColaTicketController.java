package com.atCliente.ticketera.controllers;

import com.atCliente.ticketera.entitys.Ticket;
import com.atCliente.ticketera.services.ColaTicketsService;
import com.atCliente.ticketera.services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.atCliente.ticketera.enums.Estado;

@RestController
@RequestMapping("/api/cola")
public class ColaTicketController {
    private final ColaTicketsService colaTicketsService;
    private final TicketService ticketService;

    public ColaTicketController(ColaTicketsService colaTicketsService, TicketService ticketService) {
        this.colaTicketsService = colaTicketsService;
        this.ticketService = ticketService;
    }

    // Endpoint para tomar el siguiente ticket de la cola
    @PostMapping("/tomar")
    public ResponseEntity<String> tomarTicket() {
        try {
            Ticket ticket = colaTicketsService.tomarTicket();
            ticket.setEstado(Estado.EN_PROCESO); // marca en proceso
            ticketService.actualizarTicket(ticket);
            return ResponseEntity.ok("Ticket tomado: ID " + ticket.getId());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(500).body("Error al tomar ticket de la cola");
        }
    }

    // Endpoint opcional para ver cuantos tickets hay en la cola
    @GetMapping("/pendientes")
    public ResponseEntity<Integer> pendientes() {
        return ResponseEntity.ok(colaTicketsService.tamanoCola());
    }

    @PostMapping("/agregar/{ticketId}")
    public ResponseEntity<String> agregarTicket(@PathVariable Long ticketId) {
        try {
            Ticket ticket = ticketService.obtenerTicketEntidad(ticketId); // método nuevo
            colaTicketsService.agregarTicket(ticket);
            return ResponseEntity.ok("Ticket agregado a la cola: ID " + ticket.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
