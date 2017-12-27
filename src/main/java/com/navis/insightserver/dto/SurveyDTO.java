package com.navis.insightserver.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.navis.insightserver.converter.CustomDateDeserializer;
import com.navis.insightserver.converter.CustomDateSerializer;
import com.navis.insightserver.entity.SurveyEntity;
import com.navis.insightserver.entity.SurveyTagEntity;
import com.navis.insightserver.entity.TranslationEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class SurveyDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    @NotNull(message = "survey.display.title.notnull")
    private String displayTitle;
    private String description;
    private Boolean enabled;
    private Date launchDate;
    @NotNull(message = "survey.type.notnull")
    @Valid
    private TagDTO surveyType;

    public SurveyDTO() {
    }

    public SurveyDTO(SurveyEntity surveyEntity, String locale) {
        super();
        List<TranslationEntity> displayTitleEntities;
        TranslationEntity displayTitleEntity;
        List<TranslationEntity> descriptionEntities;
        TranslationEntity descriptionEntity;

        this.id = surveyEntity.getId();
        displayTitleEntities = new ArrayList(surveyEntity.getI18NStringByDisplayTitleId().getTranslationsById());
        displayTitleEntity = displayTitleEntities.stream().filter(e -> e.getLocale().equals(locale)).findFirst().orElse(null);
        descriptionEntities =
                (null != surveyEntity.getI18NStringByDescriptionId())
                        ? new ArrayList(surveyEntity.getI18NStringByDescriptionId().getTranslationsById())
                        : null;
        descriptionEntity = (null != descriptionEntities)
                ? descriptionEntities.stream().filter(e -> e.getLocale().equals(locale)).findFirst().orElse(null)
                : null;

        this.displayTitle = (null != displayTitleEntity) ? displayTitleEntity.getLocalizedString() : null;
        this.description = (null != descriptionEntity) ? descriptionEntity.getLocalizedString() : null;
        this.enabled = surveyEntity.getEnabled();
        this.setLaunchDate(surveyEntity.getLaunchDate());
        this.surveyType = (null != surveyEntity.getSurveyTagsById() && !surveyEntity.getSurveyTagsById().isEmpty())
        ? convertToDto(surveyEntity.getSurveyTagsById().iterator().next()) : null;

    }

    private TagDTO convertToDto(SurveyTagEntity surveyTagEntity) {
        TagDTO tagDTO = new TagDTO(surveyTagEntity);

        return tagDTO;
    }

    public Long getId() {
        return id;
    }

    public String getDisplayTitle() {
        return displayTitle;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getLaunchDate() {
        return launchDate;
    }

    @JsonDeserialize(using = CustomDateDeserializer.class)
    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public TagDTO getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(TagDTO surveyType) {
        this.surveyType = surveyType;
    }
}
