package com.example.web_project.api.controller;

import com.example.web_project.api.dto.SerieAcessDTO;
import com.example.web_project.api.dto.SerieDTO;
import com.example.web_project.api.service.SerieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    private final SerieService serieService;

    public SerieController(SerieService timeSerieService) {
        this.serieService = timeSerieService;
    }

    @GetMapping("/{id_serie}")
    public ResponseEntity<SerieDTO> getTimeSerie(@PathVariable long serieId)
    {
        SerieDTO foundSerie = serieService.getTimeSerie(serieId);
        return new ResponseEntity<>(foundSerie, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<SerieDTO>> getAllTimesSeries()
    {
        List<SerieDTO> foundSeries = serieService.getAllTimeSeries();
        return new ResponseEntity<>(foundSeries, HttpStatus.OK);
    }

//    @GetMapping()
//    public ResponseEntity<List<SerieDTO>> getSeriesByCurrentUserId()
//    {
//        List<SerieDTO> foundSeries = serieService.getSeriesByCurrentUserId();
//        return new ResponseEntity<>(foundSeries, HttpStatus.OK);
//    }


    @PostMapping()
    public ResponseEntity<SerieDTO> addSerie(@RequestBody SerieDTO serieDTO)
    {
        SerieDTO addedSerie = serieService.addSerie(serieDTO);
        return new ResponseEntity<>(addedSerie, HttpStatus.CREATED);
    }
    @DeleteMapping("/{serieId}")
    public ResponseEntity<HttpStatus> deleteSerie(@PathVariable long serieId)
    {
        serieService.deleteSerie(serieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<SerieDTO> updateSerie(@RequestBody SerieDTO serieDTO)
    {
        SerieDTO updatedSerie = serieService.updateSerie(serieDTO);
        return new ResponseEntity<>(updatedSerie, HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/share")
    public ResponseEntity<SerieAcessDTO> shareSerie(@RequestBody SerieAcessDTO serieAcessDTO) {
        SerieAcessDTO addedSerieAcessDTO = serieService.shareSerieWithUser(serieAcessDTO);
        return new ResponseEntity<>(addedSerieAcessDTO, HttpStatus.CREATED);
    }

    @PutMapping("/access")
    public ResponseEntity<SerieAcessDTO> updateSerieAccess(@RequestBody SerieAcessDTO updateSerieAcessDTO) {
        SerieAcessDTO serieAcessDTO = serieService.updateSerieAccess(updateSerieAcessDTO);
        return new ResponseEntity<>(serieAcessDTO, HttpStatus.OK);
    }

    @DeleteMapping("/access")
    public ResponseEntity<?> removeSerieAccess(@RequestBody SerieAcessDTO serieAcessDTO) {
        serieService.removeSerieAccess(serieAcessDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
