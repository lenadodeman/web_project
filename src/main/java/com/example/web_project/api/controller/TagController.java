package com.example.web_project.api.controller;

import com.example.web_project.api.model.Serie;
import com.example.web_project.api.model.Tag;
import com.example.web_project.api.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{id_tag}")
    public Object getTimeSerie(@PathVariable("id_tag") long id_tag)
    {
        Tag tag = tagService.getTag(id_tag);
        return ResponseEntity.ok(tag);
    }

    @GetMapping()
    public Iterable<Tag> getAllTags()
    {
        return tagService.getAllTags();
    }

    @PostMapping()
    public Tag addTag(@RequestBody Tag tag)
    {
        return tagService.addTag(tag);
    }

    @DeleteMapping("/{id_tag}")
    public void deleteTag(@PathVariable("id_tag") long id_tag)
    {
        tagService.deleteTag(id_tag);

    }

    @PutMapping("/update")
    public Tag updateTag(@RequestBody Tag updateTag)
    {
        return tagService.updateTag(updateTag);
    }
}
