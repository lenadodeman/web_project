package com.example.web_project.api.controller;

import com.example.web_project.api.model.Serie;
import com.example.web_project.api.model.Tag;
import com.example.web_project.api.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/tag/{id_tag}")
    public Object getTimeSerie(@PathVariable("id_tag") long id_tag)
    {
        Tag tag = tagService.getTag(id_tag);
        return ResponseEntity.ok(tag);
    }

    @GetMapping("/tags")
    public Iterable<Tag> getAllTags()
    {
        return tagService.getAllTags();
    }

    @DeleteMapping("/deleteTag/{id_tag}")
    public void deleteTag(@PathVariable("id_tag") long id_tag)
    {
        tagService.deleteTag(id_tag);

    }
}
