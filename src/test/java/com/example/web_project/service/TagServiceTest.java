package com.example.web_project.service;


import com.example.web_project.api.model.Tag;
import com.example.web_project.api.repository.TagRepository;
import com.example.web_project.api.service.TagService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @Mock
    private TagRepository tagRepository;
    @InjectMocks
    private TagService tagService;

    @Test
    public void testGetTag() {
        Tag tag = new Tag();
        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));

        Tag result = tagService.getTag(1L);

        assertThat(result).isEqualTo(tag);
        verify(tagRepository).findById(1L);
    }

    @Test
    public void testGetTagNotFound() {
        when(tagRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tagService.getTag(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Tag not found with id: 1");
    }

    @Test
    public void testGetAllTags() {
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        when(tagRepository.findAll()).thenReturn(Arrays.asList(tag1, tag2));

        Iterable<Tag> result = tagService.getAllTags();

        assertThat(result).containsExactly(tag1, tag2);
        verify(tagRepository).findAll();
    }

    @Test
    public void testGetAllTagsEmpty() {
        when(tagRepository.findAll()).thenReturn(new ArrayList<>());

        Iterable<Tag> result = tagService.getAllTags();

        assertThat(result).isEmpty();
        verify(tagRepository).findAll();
    }

    @Test
    public void testAddTag() {
        Tag newTag  = new Tag();
        newTag.setLabel("Science");
        when(tagRepository.existsById(anyLong())).thenReturn(false);
        when(tagRepository.save(any(Tag.class))).thenReturn(newTag);

        Tag result = tagService.addTag(newTag);

        assertThat(result.getLabel()).isEqualTo(newTag.getLabel());
    }

    @Test
    public void testAddTagAlreadyExists() {
        Tag tag = new Tag();
        tag.setId(1L);
        when(tagRepository.existsById(anyLong())).thenReturn(true);

        assertThatThrownBy(() -> tagService.addTag(tag))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Tag with id 1 already exists");
    }

    @Test
    public void testAddTagWithDatabaseError() {
        Tag newTag = new Tag();
        when(tagRepository.existsById(anyLong())).thenReturn(false);
        when(tagRepository.save(any(Tag.class))).thenThrow(new RuntimeException("Database Error"));

        assertThatThrownBy(() -> tagService.addTag(newTag))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Database Error");
    }

    @Test
    public void testDeleteTag() {
        doNothing().when(tagRepository).deleteById(anyLong());
        when(tagRepository.existsById(anyLong())).thenReturn(true);

        tagService.deleteTag(1L);

        verify(tagRepository).deleteById(1L);
    }

    @Test
    public void testDeleteTagNotFound() {
        when(tagRepository.existsById(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> tagService.deleteTag(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Tag not found with id: 1");
    }

    @Test
    public void testUpdateTagDetails() {
        Tag existingTag = new Tag();
        existingTag.setId(1L);
        existingTag.setLabel("Old Label");

        Tag updateTag = new Tag();
        updateTag.setId(1L);
        updateTag.setLabel("New Label");

        when(tagRepository.existsById(anyLong())).thenReturn(true);
        when(tagRepository.save(any(Tag.class))).thenReturn(updateTag);

        Tag result = tagService.updateTag(updateTag);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getLabel()).isEqualTo("New Label");
        verify(tagRepository).save(updateTag);
    }

    @Test
    public void testUpdateTagNotFound() {
        Tag updateTag = new Tag();
        updateTag.setId(1L);
        updateTag.setLabel("New Label");

        when(tagRepository.existsById(anyLong())).thenReturn(false);

        assertThatThrownBy(() -> tagService.updateTag(updateTag))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Tag not found with id: " + updateTag.getId());
    }



}
