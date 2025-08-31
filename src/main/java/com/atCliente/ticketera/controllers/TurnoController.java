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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
public class TurnoController {

    private final TicketService ticketService;

    public TurnoController(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    @GetMapping("/")
    public String mostrarForm(Model model) {
        model.addAttribute("ticketDTO", new TicketDTO());
        model.addAttribute("tiposConsulta", Tipo_consulta.values());
        return "index";
    }

    @PostMapping("/turnos")
    public String sacarTurno(@RequestParam String email,
                             @RequestParam String dni,
                             @RequestParam Tipo_consulta tipoConsulta,
                             Model model, RedirectAttributes redirectAttributes) {

        if (dni.trim().length() != 8) {
            redirectAttributes.addFlashAttribute("error",
                    "El DNI debe tener exactamente 8 caracteres.");
            return "redirect:/";
        }


        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setClienteEmail(email);
        ticketDTO.setClienteDni(dni.trim());
        ticketDTO.setFechaHora(new Date());
        ticketDTO.setEstado(Estado.EN_ESPERA);
        ticketDTO.setTipoConsulta(tipoConsulta);
        System.out.println("DNI recibido: '" + ticketDTO.getClienteDni() + "'");

        try {
            ticketService.crearTicket(ticketDTO);
            return "redirect:/cola";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Cliente no registrado: " + dni);
            model.addAttribute("error", "Cliente no registrado: " + dni);
            return "error-turno";
        }
    }
}
