package com.atCliente.ticketera.controllers;

import com.atCliente.ticketera.DTO.TicketDTO;
import com.atCliente.ticketera.respository.TicketRepository;
import com.atCliente.ticketera.services.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ColaViewController {
    private final TicketRepository ticketRepository;
    private final TicketService ticketService;

    public ColaViewController(TicketRepository ticketRepository, TicketService ticketService) {
        this.ticketRepository = ticketRepository;
        this.ticketService = ticketService;
    }

    @GetMapping("/cola")
    public String verColaPendientes(Model model) {
        //solo tickets pendientes
        List<TicketDTO> ticketsPendientes = ticketService.obtenerTicketsPendientes();

        //pasa a la vista
        model.addAttribute("ticketsPendientes", ticketsPendientes);

        //retorna cola.html
        return "cola";
    }
}
