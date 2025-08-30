package com.atCliente.ticketera.controllers;

import com.atCliente.ticketera.DTO.TicketDTO;
import com.atCliente.ticketera.enums.Estado;
import com.atCliente.ticketera.enums.Tipo_consulta;
import com.atCliente.ticketera.services.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class TurnoController {

    private final TicketService ticketService;

    public TurnoController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/turnos")
    public String mostrarForm() {
        return "index";
    }

    @PostMapping("/turnos")
    public String sacarTurno(@RequestParam String nombre,
                             @RequestParam String dni,
                             Model model) {

        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setClienteNombre(nombre);
        ticketDTO.setClienteDni(dni.trim());
        ticketDTO.setFechaHora(new Date());
        ticketDTO.setEstado(Estado.EN_ESPERA);
        ticketDTO.setTipoConsulta(Tipo_consulta.CUENTAS);
        System.out.println("DNI recibido: '" + ticketDTO.getClienteDni() + "'");
        try {
            //ojo que aca falla si el cliente no esta registrado en el sistema
            TicketDTO creado = ticketService.crearTicket(ticketDTO);
            model.addAttribute("ticket", creado);
            return "ticket"; //renderiza la pag ticket
        } catch (RuntimeException e) {
            //error
            model.addAttribute("error", "Cliente no registrado: " + dni);
            return "error-turno";
        }
    }
}
