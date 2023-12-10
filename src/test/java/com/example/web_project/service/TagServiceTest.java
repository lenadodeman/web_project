//package com.example.web_project.service;
//
//
//import com.example.web_project.api.dto.TagDTO;
//import com.example.web_project.api.mapper.TagMapper;
//import com.example.web_project.api.model.Tag;
//import com.example.web_project.api.repository.TagRepository;
//import com.example.web_project.api.service.TagService;
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.Mockito.*;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//@ExtendWith(MockitoExtension.class)
//public class TagServiceTest {
//    @Mock
//    private TagRepository tagRepository;
//
//    @Mock
//    private TagMapper tagMapper;
//
//    @InjectMocks
//    private TagService tagService;
//
//
//    @Test
//    public void testFindTagById() {
//        Tag tag = new Tag();
//        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));
//
//        Tag result = tagService.findTagById(1L);
//
//        assertThat(result).isEqualTo(tag);
//        verify(tagRepository).findById(1L);
//    }
//
//    @Test
//    public void testFindTagById_NotFound() {
//        when(tagRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> tagService.findTagById(1L))
//                .isInstanceOf(EntityNotFoundException.class)
//                .hasMessageContaining("Tag not found with id: 1");
//    }
//
//    @Test
//    public void testGetTag() {
//        Tag tag = new Tag();
//        TagDTO tagDTO = new TagDTO();
//        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));
//        when(tagMapper.toDTO(any(Tag.class))).thenReturn(tagDTO);
//
//        TagDTO result = tagService.getTag(1L);
//
//        assertThat(result).isEqualTo(tagDTO);
//        verify(tagRepository).findById(1L);
//        verify(tagMapper).toDTO(tag);
//    }
//
//    @Test
//    public void testGetTag_NotFound() {
//        when(tagRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        assertThatThrownBy(() -> tagService.getTag(1L))
//                .isInstanceOf(EntityNotFoundException.class)
//                .hasMessageContaining("Tag not found with id: 1");
//    }
//
//    @Test
//    public void testGetAllTags() {
//        Tag tag1 = new Tag();
//        Tag tag2 = new Tag();
//        TagDTO tagDTO1 = new TagDTO();
//        TagDTO tagDTO2 = new TagDTO();
//        when(tagRepository.findAll()).thenReturn(Arrays.asList(tag1, tag2));
//        when(tagMapper.toDTOList(anyList())).thenReturn(Arrays.asList(tagDTO1, tagDTO2));
//
//        List<TagDTO> result = tagService.getAllTags();
//
//        assertThat(result).containsExactly(tagDTO1, tagDTO2);
//        verify(tagRepository).findAll();
//        verify(tagMapper).toDTOList(Arrays.asList(tag1, tag2));
//    }
//
//    @Test
//    public void testGetAllTagsEmpty() {
//        when(tagRepository.findAll()).thenReturn(Arrays.asList());
//        when(tagMapper.toDTOList(anyList())).thenReturn(Arrays.asList());
//
//        List<TagDTO> result = tagService.getAllTags();
//
//        assertThat(result).isEmpty();
//        verify(tagRepository).findAll();
//        verify(tagMapper).toDTOList(Arrays.asList());
//    }
//
//    @Test
//    public void testAddTag() {
//        TagDTO tagDTO = new TagDTO();
//        tagDTO.setId(1L);
//
//        Tag tag = new Tag();
//
//        when(tagRepository.existsById(anyLong())).thenReturn(false);
//        when(tagMapper.toDomain(any(TagDTO.class))).thenReturn(tag);
//        when(tagRepository.save(any(Tag.class))).thenReturn(tag);
//        when(tagMapper.toDTO(any(Tag.class))).thenReturn(tagDTO);
//
//        TagDTO result = tagService.addTag(tagDTO);
//
//        assertThat(result).isEqualTo(tagDTO);
//        verify(tagRepository).existsById(tagDTO.getId());
//        verify(tagMapper).toDomain(tagDTO);
//        verify(tagRepository).save(tag);
//        verify(tagMapper).toDTO(tag);
//    }
//
//    @Test
//    public void testAddTagAlreadyExists() {
//        TagDTO tagDTO = new TagDTO();
//        tagDTO.setId(1L);
//
//        when(tagRepository.existsById(anyLong())).thenReturn(true);
//
//        assertThatThrownBy(() -> tagService.addTag(tagDTO))
//                .isInstanceOf(IllegalStateException.class)
//                .hasMessageContaining("Tag with id " + tagDTO.getId() + " already exists");
//    }
//
//    @Test
//    public void testDeleteTag() {
//        Tag tag = new Tag();
//
//        when(tagRepository.existsById(anyLong())).thenReturn(true);
//        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));
//
//        tagService.deleteTag(1L);
//
//        verify(tagRepository).existsById(1L);
//        verify(tagRepository).findById(1L);
//        verify(tagRepository).save(tag);
//        verify(tagRepository).deleteById(1L);
//    }
//
//    @Test
//    public void testDeleteTagNotFound() {
//        when(tagRepository.existsById(anyLong())).thenReturn(false);
//
//        assertThatThrownBy(() -> tagService.deleteTag(1L))
//                .isInstanceOf(EntityNotFoundException.class)
//                .hasMessageContaining("Tag not found with id: 1");
//    }
//
//    @Test
//    public void testUpdateTag() {
//        TagDTO tagDTO = new TagDTO();
//        tagDTO.setId(1L);
//        tagDTO.setLabel("New Label");
//
//        Tag tag = new Tag();
//        tag.setId(1L);
//        tag.setLabel(tagDTO.getLabel());
//
//        when(tagRepository.existsById(tagDTO.getId())).thenReturn(true);
//        when(tagMapper.toDomain(tagDTO)).thenReturn(tag);
//        when(tagRepository.save(tag)).thenReturn(tag);
//        when(tagMapper.toDTO(tag)).thenReturn(tagDTO);
//
//        TagDTO result = tagService.updateTag(tagDTO);
//
//        assertThat(result).isEqualTo(tagDTO);
//        verify(tagRepository).existsById(tagDTO.getId());
//        verify(tagMapper).toDomain(tagDTO);
//        verify(tagRepository).save(tag);
//        verify(tagMapper).toDTO(tag);
//    }
//
//    @Test
//    public void testUpdateTag_NotExists() {
//        TagDTO tagDTO = new TagDTO();
//        tagDTO.setId(1L);
//        tagDTO.setLabel("New Label");
//
//        when(tagRepository.existsById(tagDTO.getId())).thenReturn(false);
//
//        assertThatThrownBy(() -> tagService.updateTag(tagDTO))
//                .isInstanceOf(EntityNotFoundException.class)
//                .hasMessage("Tag not found with id: " + tagDTO.getId());
//
//        verify(tagRepository).existsById(tagDTO.getId());
//    }
//
//
//
//}
