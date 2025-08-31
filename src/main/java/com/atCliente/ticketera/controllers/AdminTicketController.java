package com.atCliente.ticketera.controllers;

import com.atCliente.ticketera.DTO.BoxDTO;
import com.atCliente.ticketera.DTO.TicketDTO;
import com.atCliente.ticketera.enums.Estado;
import com.atCliente.ticketera.services.BoxService;
import com.atCliente.ticketera.services.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminTicketController {

    private final TicketService ticketService;
    private final BoxService boxService;

    public AdminTicketController(TicketService ticketService, BoxService boxService) {
        this.ticketService = ticketService;
        this.boxService = boxService;
    }

    //vista de admin
    @GetMapping("/admin/cola")
    public String verColaAdmin(Model model) {
        //lista de tickets a mostrar
        List<TicketDTO> tickets = ticketService.obtenerTodos();
        //lista de boxes para el desplegable
        List<BoxDTO> todosLosBoxes = boxService.obtenerTodosLosBoxes();

        model.addAttribute("tickets", tickets);
        model.addAttribute("boxes", todosLosBoxes);
        return "admin-cola";
    }

    //asignar un Box a un Ticket solo admin puede
    @PostMapping("/admin/cola/asignar-box")
    public String asignarBoxATicket(@RequestParam("ticketId") Long ticketId,
                                    @RequestParam("boxId") Long boxId) {
        ticketService.asignarBox(ticketId, boxId);
        return "redirect:/admin/cola";
    }

    //cambio el estado del ticket solo admin puede
    @PostMapping("/admin/cola/actualizar-estado")
    public String actualizarEstadoTicket(@RequestParam("ticketId") Long ticketId,
                                         @RequestParam("nuevoEstado") Estado nuevoEstado) {
        ticketService.cambiarEstado(ticketId, nuevoEstado);
        return "redirect:/admin/cola";
    }
}