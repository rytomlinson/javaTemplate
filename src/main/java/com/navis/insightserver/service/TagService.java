package com.navis.insightserver.service;

import com.navis.insightserver.Repository.I18nStringRepository;
import com.navis.insightserver.Repository.TagRepository;
import com.navis.insightserver.Repository.TagTagRepository;
import com.navis.insightserver.Repository.TranslationRepository;
import com.navis.insightserver.dto.ResourceNotFoundExceptionDTO;
import com.navis.insightserver.dto.TagDTO;
import com.navis.insightserver.entity.TagEntity;
import com.navis.insightserver.entity.TagTagEntity;
import com.navis.insightserver.entity.TranslationEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by darrell-shofstall on 8/9/17.
 */

@Service
@Transactional
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
        String mode;

        if(null != tagId) {
            tagEntity = validateTag(tagId);
            mode = "UPDATE";

            List<TranslationEntity> nameEntities = new ArrayList(tagEntity.getI18NStringByNameId().getTranslationsById());
            TranslationEntity nameEntity = nameEntities.stream().filter(e -> e.getLocale().equals(locale)).findFirst().orElse(null);

            nameId = (!tagDTO.getName().equals(nameEntity.getLocalizedString()) ? translationRepository.createTranslation(tagDTO.getName()) : tagEntity.getI18NStringByNameId().getId());
            tagEntity.setI18NStringByNameId(i18nStringRepository.findOne(nameId));

        } else {
            mode = "INSERT";
            nameId = translationRepository.createTranslation(tagDTO.getName());
            tagEntity = convertToEntity(owner, tagDTO, locale);
            tagEntity.setI18NStringByNameId(i18nStringRepository.findOne(nameId));
            tagEntity.setMarketSegment(false);
            tagEntity.setQuestion(false);
            tagEntity.setSurvey(false);
            tagEntity.setSurveyType(false);
            tagEntity.setDeleted(false);
        }

        tagEntity = tagRepository.save(tagEntity);

        if("INSERT".equals(mode)) {
            insertTagTag(tagDTO, tagEntity, owner);
        }

        return tagEntity.getId();
    }

    private void insertTagTag(TagDTO tagDTO, TagEntity tagEntity, UUID owner) {
        Date now = new Date();
        TagEntity tagParentEntity;
        if(null != tagDTO.getParentTagId()) {
            tagParentEntity = validateTag(tagDTO.getParentTagId());
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
    }

    @Override
    public TagDTO getTag(UUID owner, Long id) {
        TagEntity tagEntity = validateTag(id);

        return convertToDto(tagEntity);
    }

    @Override
    public void deleteTag(UUID owner, Long id) {
        TagEntity tagEntity = validateTag(id);
        tagEntity.setDeleted(true);
        tagRepository.save(tagEntity);
    }

    private List<TagDTO> buildTagsDTO(UUID propertyId) {
        List<TagEntity> list = tagRepository.findByOwner(propertyId);

        List<TagDTO> listDto = list.stream().map(item -> convertToDto(item)).collect(Collectors.toList());

        return listDto;
    }

    private List<TagDTO> buildDepartmentTagsDTO(UUID propertyId, Long departmentTagId) {
        List<TagTagEntity> list = tagRepository.getDepartmentTagsByOwner(propertyId, departmentTagId);

        List<TagDTO> listDto = list.stream().filter(item -> !item.getTagByTagId().getDeleted()).map(item -> convertToDto(item)).collect(Collectors.toList());

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

    private TagEntity validateTag(Long id) {

        if (!tagRepository.exists(id)) {

            throw new ResourceNotFoundExceptionDTO(id.toString(), "tag.id.invalid");
        } else {
            return tagRepository.findOne(id);
        }
    }
}
