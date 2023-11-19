package com.example.web_project.api.service;


import com.example.web_project.api.model.Tag;
import com.example.web_project.api.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


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

    public Tag addTag(Tag tag)
    {
        if(tagRepository.existsById(tag.getId()) && tag.getId() != 0)
        {
            throw new IllegalStateException("Tag with id " + tag.getId() + " already exists");
        }
        return tagRepository.save(tag);
    }

    public void deleteTag(final long id_tag)
    {
        tagRepository.deleteById(id_tag);
    }

    @Transactional
    public Tag updateTag(Tag updateTag)
    {
        if(!tagRepository.existsById(updateTag.getId()))
        {
            throw new EntityNotFoundException("Tag not found with id: " + updateTag.getId());
        }
        updateTag.setLabel(updateTag.getLabel());
        return tagRepository.save(updateTag);
    }



}
