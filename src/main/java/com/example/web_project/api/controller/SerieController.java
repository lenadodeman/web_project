package com.example.web_project.api.controller;

import com.example.web_project.api.model.Event;
import com.example.web_project.api.model.Serie;
import com.example.web_project.api.service.SerieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/series")
public class SerieController {

    private final SerieService serieService;

    public SerieController(SerieService timeSerieService) {
        this.serieService = timeSerieService;
    }

    @GetMapping("/{id_serie}")
    public ResponseEntity<Serie> getTimeSerie(@PathVariable("id_serie") long id_serie)
    {
        Serie timeSerie = serieService.getTimeSerie(id_serie);
        return ResponseEntity.ok(timeSerie);
    }

    @GetMapping()
    public Iterable<Serie> getAllTimesSeries()
    {
        return serieService.getAllTimeSeries();
    }

    @PostMapping()
    public Serie addEvent(@RequestBody Serie serie)
    {
        return serieService.addSerie(serie);
    }
    @DeleteMapping("/{id_serie}")
    public void deleteEvent(@PathVariable("id_serie") long id_serie)
    {
        serieService.deleteSerie(id_serie);

    }

    @PostMapping("/update")
    public ResponseEntity<Serie> updateSerie(@RequestBody Serie updateSerie)
    {
        Serie serie =  serieService.updateSerie(updateSerie);
        return ResponseEntity.ok(serie);
    }
}
