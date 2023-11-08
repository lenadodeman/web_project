package com.example.web_project.api.controller;

import com.example.web_project.api.model.Serie;
import com.example.web_project.api.service.SerieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SerieController {

    private final SerieService serieService;

    public SerieController(SerieService timeSerieService) {
        this.serieService = timeSerieService;
    }

    @GetMapping("/serie/{idSerie}")
    public Object getTimeSerie(@PathVariable("idSerie") long idTimeSerie)
    {
        Serie timeSerie = serieService.getTimeSerie(idTimeSerie);
        return ResponseEntity.ok(timeSerie);
    }

    @GetMapping("/series")
    public Iterable<Serie> getAllTimesSeries()
    {
        return serieService.getAllTimeSeries();
    }
}
