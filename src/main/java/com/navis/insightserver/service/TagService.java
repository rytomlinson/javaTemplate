package com.navis.insightserver.service;

import com.navis.insightserver.Repository.I18nStringRepository;
import com.navis.insightserver.Repository.TagRepository;
import com.navis.insightserver.Repository.TagTagRepository;
import com.navis.insightserver.Repository.TranslationRepository;
import com.navis.insightserver.dto.TagDTO;
import com.navis.insightserver.entity.TagEntity;
import com.navis.insightserver.entity.TagTagEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private I18nStringRepository i18nStringRepository;

    @Override
    public List<TagDTO> getTags(UUID propertyId) {
        log.debug("In getTags Service:");
        return buildTagsDTO(propertyId);
    }

    @Override
    public List<TagDTO> getDepartmentTags(UUID propertyId) {
        log.debug("In getDepartmentTags Service:");
        TagEntity tagEntity = tagRepository.getDepartmentTag(uuid);
        return buildDepartmentTagsDTO(propertyId, tagEntity.getId());
    }

    @Override
    public List<TagDTO> getSurveyTypeTags() {
        log.debug("In getSurveyTypeTags Service:");
        return buildSurveyTypeTagsDTO(uuid);
    }

    @Override
    public Long upsertTag(UUID owner, TagDTO tagDTO, String locale) {
        log.debug("In upsertTag Service:");
        Date now = new Date();
        Long tagId = tagDTO.getId();
        Long nameId;
        TagEntity tagEntity;
        TagEntity tagParentEntity;

        if(null != tagId) {
            tagEntity = tagRepository.findOne(tagId);
        } else {
            nameId = translationRepository.createTranslation(tagDTO.getName());
            tagEntity = convertToEntity(owner, tagDTO, locale);
            tagEntity.setI18NStringByNameId(i18nStringRepository.findOne(nameId));
            tagEntity.setMarketSegment(false);
            tagEntity.setQuestion(false);
            tagEntity.setSurvey(false);
            tagEntity.setSurveyType(false);
//            tagEntity.setType(null);
        }

        tagEntity = tagRepository.save(tagEntity);

        if(null != tagDTO.getParentTagId()) {
            tagParentEntity = tagRepository.findOne(tagDTO.getParentTagId());
        } else {
            tagParentEntity = tagRepository.getDepartmentTag(uuid);
        }

        //TODO: refactor code
        TagTagEntity tagTagEntity = new TagTagEntity();
        tagTagEntity.setTagByParentTagId(tagParentEntity);
        tagTagEntity.setTagByTagId(tagEntity);
        tagTagEntity.setCreatedAt(now);
        tagTagEntity.setUpdatedAt(now);
        tagTagEntity.setOwner(owner);

        tagTagRepository.save(tagTagEntity);

        return tagEntity.getId();
    }

    @Override
    public TagDTO getTag(UUID owner, Long id) {
        TagEntity tagEntity = tagRepository.findOne(id);

        return convertToDto(tagEntity);
    }

    @Override
    public void deleteTag(UUID owner, Long id) {
        tagRepository.delete(id);
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

    private List<TagDTO> buildSurveyTypeTagsDTO(UUID owner) {
        List<TagEntity> list = tagRepository.findByOwnerAndSurveyTypeTrue(owner);

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

    private TagEntity convertToEntity(UUID owner, TagDTO tagDTO, String locale) {
        Date now = new Date();
        TagEntity tagEntity = new TagEntity();
        tagEntity.setOwner(owner);
        tagEntity.setCreatedAt(now);
        tagEntity.setUpdatedAt(now);
        tagEntity.setMinimumValue(tagDTO.getMinimumValue());
        tagEntity.setMaximumValue(tagDTO.getMaximumValue());

        return tagEntity;
    }
}
