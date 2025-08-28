package com.atCliente.ticketera.services;

import com.atCliente.ticketera.DTO.TicketDTO;
import com.atCliente.ticketera.entitys.Cliente;
import com.atCliente.ticketera.entitys.Consulta;
import com.atCliente.ticketera.entitys.Ticket;
import com.atCliente.ticketera.enums.Estado;
import com.atCliente.ticketera.respository.ClienteRepository;
import com.atCliente.ticketera.respository.ConsultaRepository;
import com.atCliente.ticketera.respository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final ClienteRepository clienteRepository;
    private final ConsultaRepository consultaRepository;
    private final ColaTicketsService colaTicketsService;

    //Inyección por constructor
    public TicketService(TicketRepository ticketRepository,
                         ClienteRepository clienteRepository,
                         ConsultaRepository consultaRepository,
                         ColaTicketsService colaTicketsService) {
        this.ticketRepository = ticketRepository;
        this.clienteRepository = clienteRepository;
        this.consultaRepository = consultaRepository;
        this.colaTicketsService = colaTicketsService;
    }

    // Crear un ticket (recibe entidad, devuelve DTO)
    public TicketDTO crearTicket(TicketDTO ticketDTO) {
        Cliente cliente = clienteRepository.findByDni(ticketDTO.getClienteDni())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Consulta consulta = consultaRepository.findByTipoConsulta(ticketDTO.getTipoConsulta())
                .orElseThrow(() -> new RuntimeException("Consulta no encontrada"));

        Ticket ticket = new Ticket();
        ticket.setCliente(cliente);
        ticket.setConsulta(consulta);
        ticket.setFecha_hora(ticketDTO.getFechaHora());
        ticket.setEstado(ticketDTO.getEstado());

        Ticket nuevoTicket = ticketRepository.save(ticket);
        return convertirADTO(nuevoTicket);
    }

    //Todos los tickets
    public List<TicketDTO> obtenerTodos() {
        return ticketRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //Ticket por ID
    public TicketDTO obtenerPorId(Long id) {
        return ticketRepository.findById(id)
                .map(this::convertirADTO)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + id));
    }

    //actualiza ticket
    public TicketDTO actualizarTicket(Ticket ticket) {
        Ticket actualizado = ticketRepository.save(ticket);
        return convertirADTO(actualizado);
    }

    // MAPPERS
    private TicketDTO convertirADTO(Ticket ticket) {
        return new TicketDTO(
                ticket.getId(),
                ticket.getCliente().getNombre(),
                ticket.getCliente().getDni(),
                ticket.getCliente().getEmail(),
                ticket.getConsulta().getTipoConsulta(),
                ticket.getFecha_hora(),
                ticket.getEstado()
        );
    }

    //cambiar estados de ticket

    public TicketDTO cambiarEstado(Long id, Estado nuevoEstado) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));

        ticket.setEstado(nuevoEstado);
        Ticket actualizado = ticketRepository.save(ticket);
        colaTicketsService.agregarTicket(ticket);

        return convertirADTO(actualizado);
    }

    public Ticket obtenerTicketEntidad(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + id));
    }
}
