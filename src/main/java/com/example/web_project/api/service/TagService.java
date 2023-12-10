package com.example.web_project.api.service;


import com.example.web_project.api.dto.TagDTO;
import com.example.web_project.api.mapper.TagMapper;
import com.example.web_project.api.model.Tag;
import com.example.web_project.api.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper = TagMapper.INSTANCE;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag findTagById(final long tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Tag not found with id: " + tagId)
                );
    }

    public TagDTO getTag(long tagId) {
        return tagMapper.toDTO(findTagById(tagId));
    }

    public List<TagDTO> getAllTags() {
        return tagMapper.toDTOList(tagRepository.findAll());
    }

    public TagDTO addTag(TagDTO tagDTO) {
        if (tagRepository.existsById(tagDTO.getId())) {
            throw new IllegalStateException("Tag with id " + tagDTO.getId() + " already exists");
        }

        Tag tagTOUpdate = tagMapper.toDomain(tagDTO);

        return tagMapper.toDTO(tagRepository.save(tagTOUpdate));
    }

    public void deleteTag(final long tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new EntityNotFoundException("Tag not found with id: " + tagId);
        }
        tagRepository.deleteById(tagId);
    }

    @Transactional
    public TagDTO updateTag(TagDTO tagDTO) {
        if (!tagRepository.existsById(tagDTO.getId())) {
            throw new EntityNotFoundException("Tag not found with id: " + tagDTO.getId());
        }

        Tag tagToUpdate = tagMapper.toDomain(tagDTO);

        return tagMapper.toDTO(tagRepository.save(tagToUpdate));
    }


}
