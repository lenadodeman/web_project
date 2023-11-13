package com.example.web_project.api.controller;

import com.example.web_project.api.model.Event;
import com.example.web_project.api.model.Serie;
import com.example.web_project.api.service.SerieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SerieController {

    private final SerieService serieService;

    public SerieController(SerieService timeSerieService) {
        this.serieService = timeSerieService;
    }

    @GetMapping("/serie/{id_serie}")
    public Object getTimeSerie(@PathVariable("id_serie") long id_serie)
    {
        Serie timeSerie = serieService.getTimeSerie(id_serie);
        return ResponseEntity.ok(timeSerie);
    }

    @GetMapping("/series")
    public Iterable<Serie> getAllTimesSeries()
    {
        return serieService.getAllTimeSeries();
    }

    @PostMapping("/addSerie")
    public Serie addEvent(@RequestBody Serie serie)
    {
        return serieService.addSerie(serie);
    }
    @DeleteMapping("/deleteSerie/{id_serie}")
    public void deleteEvent(@PathVariable("id_serie") long id_serie)
    {
        serieService.deleteSerie(id_serie);

    }
}
