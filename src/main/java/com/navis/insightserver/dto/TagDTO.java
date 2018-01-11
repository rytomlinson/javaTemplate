package com.navis.insightserver.dto;

import com.navis.insightserver.entity.SurveyTagEntity;
import com.navis.insightserver.entity.TagEntity;
import com.navis.insightserver.entity.TagTagEntity;
import com.navis.insightserver.entity.TranslationEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class TagDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

//    @NotNull(message = "tag.id.notnull")
    private Long id;
    private String name;
    private Integer minimumValue;
    private Integer maximumValue;
    private Long parentTagId;
    @Valid
    private List<TagDTO> tags = new ArrayList<>();

    public TagDTO() {
    }

    public TagDTO(TagEntity tagEntity, String locale) {
        super();
       buildDto(tagEntity, null, locale);
    }

    public TagDTO(TagEntity tagEntity, Long parentTagId, String locale) {
        super();
        buildDto(tagEntity, parentTagId, locale);
    }

    public TagDTO(TagTagEntity tagTagEntity, String locale) {
        super();
        TagEntity tagEntity = tagTagEntity.getTagByTagId();
        List<TranslationEntity> translationEntities = (List<TranslationEntity>) tagEntity.getI18NStringByNameId().getTranslationsById();
        this.id = tagEntity.getId();
        this.name = super.returnTranslationForLocale(translationEntities, locale);
        this.minimumValue = (null != tagEntity.getMinimumValue()) ? tagEntity.getMinimumValue() : null;
        this.maximumValue = (null != tagEntity.getMaximumValue()) ? tagEntity.getMaximumValue() : null;

        List<TagTagEntity> tagEntityList
                = (null != tagEntity.getTagTagsById_0())
                ? new ArrayList(tagEntity.getTagTagsById_0())
                : null;

        List<TagDTO> tagDTOList = (null != tagEntityList)
                ? tagEntityList.stream().filter(item -> !item.getTagByTagId().getDeleted()).map(item -> convertToDto(item, locale)).collect(Collectors.toList())
                : null;

        this.tags = tagDTOList;
    }

    public TagDTO(SurveyTagEntity surveyTagEntity, String locale) {
        super();
        TagEntity tagEntity = surveyTagEntity.getTagByTagId();
        List<TranslationEntity> translationEntities = (List<TranslationEntity>) tagEntity.getI18NStringByNameId().getTranslationsById();

        this.id = tagEntity.getId();
        this.name = super.returnTranslationForLocale(translationEntities, locale);
        this.minimumValue = (null != tagEntity.getMinimumValue()) ? tagEntity.getMinimumValue() : null;
        this.maximumValue = (null != tagEntity.getMaximumValue()) ? tagEntity.getMaximumValue() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(Integer minimumValue) {
        this.minimumValue = minimumValue;
    }

    public Integer getMaximumValue() {
        return maximumValue;
    }

    public void setMaximumValue(Integer maximumValue) {
        this.maximumValue = maximumValue;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    public Long getParentTagId() {
        return parentTagId;
    }

    public void setParentTagId(Long parentTagId) {
        this.parentTagId = parentTagId;
    }

    private TagDTO convertToDto(TagTagEntity tagTagEntity, String locale) {
       TagEntity tagEntity = tagTagEntity.getTagByTagId();
       Long parentTagId = tagTagEntity.getTagByParentTagId().getId();
        TagDTO tagDTO = new TagDTO(tagEntity, parentTagId, locale);

        return tagDTO;
    }

    private void buildDto(TagEntity tagEntity, Long parentTagId, String locale ) {

        List<TranslationEntity> translationEntities = (List<TranslationEntity>) tagEntity.getI18NStringByNameId().getTranslationsById();
        this.id = tagEntity.getId();
        this.name = super.returnTranslationForLocale(translationEntities, locale);
        this.minimumValue = (null != tagEntity.getMinimumValue()) ? tagEntity.getMinimumValue() : null;
        this.maximumValue = (null != tagEntity.getMaximumValue()) ? tagEntity.getMaximumValue() : null;
        this.parentTagId = parentTagId;

        List<TagTagEntity> tagEntityList
                = (null != tagEntity.getTagTagsById_0())
                ? new ArrayList(tagEntity.getTagTagsById_0())
                : null;

        List<TagDTO> tagDTOList = (null != tagEntityList)
                ? tagEntityList.stream().filter(item -> !item.getTagByTagId().getDeleted()).map(item -> convertToDto(item, locale)).collect(Collectors.toList())
                : null;

        this.tags = tagDTOList;
    }
}
