package com.example.web_project.api.service;


import com.example.web_project.api.model.Tag;
import com.example.web_project.api.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository)
    {
        this.tagRepository = tagRepository;
    }

    public Tag getTag(final long id_tag)
    {
        return tagRepository.findById(id_tag).orElseThrow(() -> new EntityNotFoundException("Tag not found with id: " + id_tag));
    }

    public Iterable<Tag> getAllTags()
    {
        return tagRepository.findAll();
    }

    public void deleteTag(final long id_tag)
    {
        tagRepository.deleteById(id_tag);
    }



}
