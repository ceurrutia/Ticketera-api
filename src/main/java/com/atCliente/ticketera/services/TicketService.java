package com.atCliente.ticketera.services;

import com.atCliente.ticketera.DTO.TicketDTO;
import com.atCliente.ticketera.entitys.Box;
import com.atCliente.ticketera.entitys.Cliente;
import com.atCliente.ticketera.entitys.Consulta;
import com.atCliente.ticketera.entitys.Ticket;
import com.atCliente.ticketera.enums.Estado;
import com.atCliente.ticketera.enums.Tipo_consulta;
import com.atCliente.ticketera.respository.ClienteRepository;
import com.atCliente.ticketera.respository.ConsultaRepository;
import com.atCliente.ticketera.respository.TicketRepository;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final ClienteRepository clienteRepository;
    private final ConsultaRepository consultaRepository;
    private final ColaTicketsService colaTicketsService;
    private final BoxService boxService;

    //Inyección por constructor
    public TicketService(TicketRepository ticketRepository,
                         ClienteRepository clienteRepository,
                         ConsultaRepository consultaRepository,
                         ColaTicketsService colaTicketsService, BoxService boxService) {
        this.ticketRepository = ticketRepository;
        this.clienteRepository = clienteRepository;
        this.consultaRepository = consultaRepository;
        this.colaTicketsService = colaTicketsService;
        this.boxService = boxService;
    }

    // Crear un ticket (recibe entidad, devuelve DTO)
    public TicketDTO crearTicket(TicketDTO ticketDTO) {
        //normalizar DNI con trim
        String dniNormalizado = ticketDTO.getClienteDni().trim();

        //busca cliente.
        //busca al cliente por su DNI.
        Cliente cliente = clienteRepository.findByDni(dniNormalizado)
                .orElseThrow(() -> new RuntimeException("Cliente no registrado"));

        //bBuscar consulta.
        //el repo puede encontrar la consulta directamente por el enum.
        Tipo_consulta tipo = ticketDTO.getTipoConsulta();
        // AQUI ESTA LA CORRECCION: ASIGNA EL RESULTADO A LA VARIABLE 'consulta'
        Consulta consulta = consultaRepository.findByTipoConsulta(tipo)
                .orElseThrow(() -> new RuntimeException("Consulta no encontrada"));

        //crea el ticket, debe ser "EN_ESPERA" al crearse siempre.
        Ticket ticket = new Ticket();
        ticket.setCliente(cliente);
        ticket.setConsulta(consulta); // AHORA FUNCIONARÁ
        ticket.setFecha_hora(new Date());
        ticket.setEstado(Estado.EN_ESPERA);

        //guarda el ticket.
        Ticket nuevoTicket = ticketRepository.save(ticket);
        System.out.println("Ticket creado con id=" + nuevoTicket.getId());

        //devuelve el DTO del nuevo ticket.
        return convertirADTO(nuevoTicket);
    }

    //Todos los tickets
    public List<TicketDTO> obtenerTodos() {
        return ticketRepository.findAllWithBoxes()
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

    //obtener pednientes
    public List<TicketDTO> obtenerTicketsPendientes() {
        return ticketRepository.findAll().stream()
                .filter(t -> t.getEstado() == Estado.EN_ESPERA) // lossolo pendientes
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //los uttimos atendidos
   public List<TicketDTO> obtenerUltimosAtendidos(int cantidad) {
        return ticketRepository.findAll().stream()
                .filter(t -> t.getEstado() == Estado.ATENDIDO)
                .sorted((t1, t2) -> {
                    Date f1 = t1.getFecha_hora();
                    Date f2 = t2.getFecha_hora();
                    if (f1 == null && f2 == null) return 0;
                    if (f1 == null) return 1;   // nulo al final
                    if (f2 == null) return -1;  // nulo al final
                    return f2.compareTo(f1);    // orden descendente (últimos primero)
                })
                .limit(cantidad)
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //obtengo los tickest para cola
    public List<TicketDTO> obtenerTicketsParaCola() {
        return ticketRepository.findAll().stream()
                // Primero filtra las entidades de Ticket
                .filter(t -> t.getEstado() == Estado.EN_ESPERA ||
                        t.getEstado() == Estado.EN_PROCESO ||
                        t.getEstado() == Estado.ATENDIDO)
                // Luego, convierte cada una a un DTO usando el mapper
                .map(this::convertirADTO)
                .collect(Collectors.toList());
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
                ticket.getEstado(),
                ticket.getBox() != null ? boxService.convertirADTO(ticket.getBox()) : null
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

    public void asignarBox(Long ticketId, Long boxId) {
        //encuentra el ticket por su ID
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + ticketId));

        //encuentra el box por su ID
        Box box = boxService.obtenerBoxEntidad(boxId);

        //box al ticket
        ticket.setBox(box);

        //guardaticket actualizado en la bbdd
        ticketRepository.save(ticket);
    }
}
