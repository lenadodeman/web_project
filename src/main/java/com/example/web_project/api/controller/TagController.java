package com.example.web_project.api.controller;

import com.example.web_project.api.dto.TagDTO;
import com.example.web_project.api.model.Serie;
import com.example.web_project.api.model.Tag;
import com.example.web_project.api.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<TagDTO> getTag(@PathVariable long tagId)
    {
        TagDTO tagDTO = tagService.getTag(tagId);
        return new ResponseEntity<>(tagDTO, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<TagDTO>> getAllTags()
    {
        List<TagDTO> foundTags = tagService.getAllTags();
        return new ResponseEntity<>(foundTags, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<TagDTO> addTag(@RequestBody TagDTO tagDTO)
    {
        TagDTO addedTag = tagService.addTag(tagDTO);
        return new ResponseEntity<>(addedTag, HttpStatus.CREATED);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<HttpStatus> deleteTag(@PathVariable long tagId)
    {
        tagService.deleteTag(tagId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<TagDTO> updateTag(@RequestBody TagDTO tagDTO)
    {
        TagDTO updatedTag = tagService.updateTag(tagDTO);
        return new ResponseEntity<>(updatedTag, HttpStatus.OK);
    }
}
