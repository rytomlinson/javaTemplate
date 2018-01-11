package com.navis.insightserver.service;

import com.navis.insightserver.dto.TagDTO;

import java.util.List;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 8/9/17.
 */
public interface ITagService {

    List<TagDTO> getTags(UUID propertyId, String locale);
    TagDTO getTag(UUID propertyId, Long id, String locale);
    List<TagDTO> getDepartmentTags(UUID propertyId, String locale);
    List<TagDTO> getSurveyTypeTags(String locale);
    Long upsertTag(UUID owner, TagDTO tagDTO, String locale);
    void deleteTag(UUID propertyId, Long id);
}
