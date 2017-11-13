package com.navis.insightserver.service;

import com.navis.insightserver.Repository.ITagRepository;
import com.navis.insightserver.dto.TagDTO;
import com.navis.insightserver.entity.TagEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by darrell-shofstall on 8/9/17.
 */

@Service
public class TagService implements ITagService {
    private static final Logger log = LoggerFactory.getLogger(TagService.class);

    @Autowired
    private ITagRepository tagRepository;

    @Override
    public List<TagDTO> getTags() {
        log.debug("In getTags Service:");
        return buildTagsDTO();
    }

    private List<TagDTO> buildTagsDTO() {
        List<TagEntity> list = tagRepository.findAll();

        List<TagDTO> listDto = list.stream().map(item -> convertToDto(item)).collect(Collectors.toList());

        return listDto;
    }

    private TagDTO convertToDto(TagEntity tagEntity) {
        TagDTO tagDTO = new TagDTO(tagEntity);
        return tagDTO;
    }
}
