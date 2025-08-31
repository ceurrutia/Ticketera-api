package com.atCliente.ticketera.controllers;

import com.atCliente.ticketera.DTO.BoxDTO;
import com.atCliente.ticketera.services.BoxService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/boxes")
public class BoxController {
    private final BoxService boxService;

    public BoxController(BoxService boxService) {
        this.boxService = boxService;
    }

    @GetMapping
    public List<BoxDTO> obtenerEstadoDeBoxes() {
        return boxService.obtenerTodosLosBoxes();
    }
}
