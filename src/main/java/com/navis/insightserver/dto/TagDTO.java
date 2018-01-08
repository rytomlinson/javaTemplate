package com.navis.insightserver.dto;

import com.navis.insightserver.entity.SurveyTagEntity;
import com.navis.insightserver.entity.TagEntity;
import com.navis.insightserver.entity.TagTagEntity;

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

    public TagDTO(TagEntity tagEntity) {
        super();
       buildDto(tagEntity, null);
    }

    public TagDTO(TagEntity tagEntity, Long parentTagId) {
        super();
        buildDto(tagEntity, parentTagId);
    }

    public TagDTO(TagTagEntity tagTagEntity) {
        super();
        TagEntity tagEntity = tagTagEntity.getTagByTagId();
        this.id = tagEntity.getId();
        this.name = tagEntity.getI18NStringByNameId().getTranslationsById().iterator().next().getLocalizedString();
        this.minimumValue = (null != tagEntity.getMinimumValue()) ? tagEntity.getMinimumValue() : null;
        this.maximumValue = (null != tagEntity.getMaximumValue()) ? tagEntity.getMaximumValue() : null;

        List<TagTagEntity> tagEntityList
                = (null != tagEntity.getTagTagsById_0())
                ? new ArrayList(tagEntity.getTagTagsById_0())
                : null;

        List<TagDTO> tagDTOList = (null != tagEntityList)
                ? tagEntityList.stream().map(item -> convertToDto(item)).collect(Collectors.toList())
                : null;

        this.tags = tagDTOList;
    }

    public TagDTO(SurveyTagEntity surveyTagEntity) {
        super();
        TagEntity tagEntity = surveyTagEntity.getTagByTagId();

        this.id = tagEntity.getId();
        this.name = tagEntity.getI18NStringByNameId().getTranslationsById().iterator().next().getLocalizedString();
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

    private TagDTO convertToDto(TagTagEntity tagTagEntity) {
       TagEntity tagEntity = tagTagEntity.getTagByTagId();
       Long parentTagId = tagTagEntity.getTagByParentTagId().getId();
        TagDTO tagDTO = new TagDTO(tagEntity, parentTagId);

        return tagDTO;
    }

    private void buildDto(TagEntity tagEntity, Long parentTagId ) {
        this.id = tagEntity.getId();
        this.name = tagEntity.getI18NStringByNameId().getTranslationsById().iterator().next().getLocalizedString();
        this.minimumValue = (null != tagEntity.getMinimumValue()) ? tagEntity.getMinimumValue() : null;
        this.maximumValue = (null != tagEntity.getMaximumValue()) ? tagEntity.getMaximumValue() : null;
        this.parentTagId = parentTagId;

        List<TagTagEntity> tagEntityList
                = (null != tagEntity.getTagTagsById_0())
                ? new ArrayList(tagEntity.getTagTagsById_0())
                : null;

        List<TagDTO> tagDTOList = (null != tagEntityList)
                ? tagEntityList.stream().map(item -> convertToDto(item)).collect(Collectors.toList())
                : null;

        this.tags = tagDTOList;
    }
}
