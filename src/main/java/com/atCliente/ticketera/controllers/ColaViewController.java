package com.atCliente.ticketera.controllers;

import com.atCliente.ticketera.DTO.TicketDTO;
import com.atCliente.ticketera.services.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ColaViewController {
    private final TicketService ticketService;

    public ColaViewController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/cola")
    public String verColaUsuario(Model model) {
        //trae los tickets para la vista de usuario
        List<TicketDTO> ticketsParaCola = ticketService.obtenerTicketsParaCola();

        model.addAttribute("tickets", ticketsParaCola);
        return "cola";
    }
}
