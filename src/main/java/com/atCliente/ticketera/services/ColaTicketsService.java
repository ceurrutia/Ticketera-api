package com.atCliente.ticketera.services;

import com.atCliente.ticketera.entitys.Ticket;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class ColaTicketsService {
    private BlockingQueue<Ticket> cola = new LinkedBlockingQueue<>();

    public void agregarTicket(Ticket ticket) {
        cola.offer(ticket);
    }

    public Ticket tomarTicket() throws InterruptedException {
        //espera hasta que haya ticket
        return cola.take();
    }

    public int tamanoCola() {
        return cola.size();
    }
}
