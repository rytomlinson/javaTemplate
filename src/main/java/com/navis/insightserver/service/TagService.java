package com.navis.insightserver.service;

import com.navis.insightserver.Repository.TagRepository;
import com.navis.insightserver.Repository.TagTagRepository;
import com.navis.insightserver.dto.TagDTO;
import com.navis.insightserver.entity.TagEntity;
import com.navis.insightserver.entity.TagTagEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by darrell-shofstall on 8/9/17.
 */

@Service
public class TagService implements ITagService {
    private static final Logger log = LoggerFactory.getLogger(TagService.class);

    @Value("${navis.prop.uuid}")
    private UUID uuid;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagTagRepository tagTagRepository;

    @Override
    public List<TagDTO> getTags(UUID propertyId) {
        log.debug("In getTags Service:");
        return buildTagsDTO(propertyId);
    }

    @Override
    public List<TagDTO> getDepartmentTags(UUID propertyId) {
        log.debug("In getTags Service:");
        TagEntity tagEntity = tagRepository.getDepartmentTag(uuid);
        return buildDepartmentTagsDTO(propertyId, tagEntity.getId());
    }

    private List<TagDTO> buildTagsDTO(UUID propertyId) {
        List<TagEntity> list = tagRepository.findByOwner(propertyId);

        List<TagDTO> listDto = list.stream().map(item -> convertToDto(item)).collect(Collectors.toList());

        return listDto;
    }

    private List<TagDTO> buildDepartmentTagsDTO(UUID propertyId, Long departmentTagId) {
        List<TagTagEntity> list = tagRepository.getDepartmentTagsByOwner(propertyId, departmentTagId);

        List<TagDTO> listDto = list.stream().map(item -> convertToDto(item)).collect(Collectors.toList());

        return listDto;
    }

    private TagDTO convertToDto(TagEntity tagEntity) {
        TagDTO tagDTO = new TagDTO(tagEntity);
        return tagDTO;
    }
    private TagDTO convertToDto(TagTagEntity tagTagEntity) {
        TagDTO tagDTO = new TagDTO(tagTagEntity);
        return tagDTO;
    }
}
