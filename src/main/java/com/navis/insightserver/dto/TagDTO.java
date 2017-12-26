package com.navis.insightserver.dto;

import com.navis.insightserver.entity.TagEntity;
import com.navis.insightserver.entity.TagTagEntity;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class TagDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

//    @NotNull(message = "user.settings.stat.event.type.notnull")
    private Long id;
    private String type;
    private String name;
    private Integer minimumValue;
    private Integer maximumValue;
    private List<TagDTO> tags = new ArrayList<>();

    public TagDTO() {
    }

    public TagDTO(TagEntity tagEntity) {

        super();
        this.id = tagEntity.getId();
        this.type = tagEntity.getType();
        this.name = tagEntity.getI18NStringByNameId().getTranslationsById().iterator().next().getLocalizedString();
        this.minimumValue = (null != tagEntity.getMinimumValue()) ? tagEntity.getMinimumValue() : null;
        this.maximumValue = (null != tagEntity.getMaximumValue()) ? tagEntity.getMaximumValue() : null;
    }

    public TagDTO(TagTagEntity tagTagEntity) {
        super();
        TagEntity tagEntity = tagTagEntity.getTagByTagId();
        this.id = tagEntity.getId();
        this.type = tagEntity.getType();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    private TagDTO convertToDto(TagTagEntity tagTagEntity) {
       TagEntity tagEntity = tagTagEntity.getTagByTagId();
        TagDTO tagDTO = new TagDTO(tagEntity);

        return tagDTO;
    }
}
