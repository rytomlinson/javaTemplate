package com.navis.insightserver.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.navis.insightserver.converter.CustomDateDeserializer;
import com.navis.insightserver.converter.CustomDateSerializer;
import com.navis.insightserver.entity.SurveyEntity;
import com.navis.insightserver.entity.SurveyTagEntity;
import com.navis.insightserver.entity.TranslationEntity;
import org.hibernate.validator.constraints.NotEmpty;
import org.javatuples.Triplet;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
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
    private Long questionCount;
    private Long inProgressCount;
    private Long completedCount;
    @NotNull(message = "survey.type.notnull")
    @Valid
    private TagDTO surveyType;

    public SurveyDTO() {
    }

    public SurveyDTO(SurveyEntity surveyEntity, Long questionCount, List<Object[]> completionCounts, String locale) {
        super();
        this.id = surveyEntity.getId();
        this.questionCount = questionCount;
        List<TranslationEntity> displayTitleEntities = (List<TranslationEntity>) surveyEntity.getI18NStringByDisplayTitleId().getTranslationsById();
        List<TranslationEntity> descriptionEntities =
                (null != surveyEntity.getI18NStringByDescriptionId())
                        ? (List<TranslationEntity>) surveyEntity.getI18NStringByDescriptionId().getTranslationsById()
                : null;

        this.displayTitle = super.returnTranslationForLocale(displayTitleEntities, locale);
        this.description = super.returnTranslationForLocale(descriptionEntities, locale);
        this.enabled = surveyEntity.getEnabled();
        this.setLaunchDate(surveyEntity.getLaunchDate());
        this.surveyType = (null != surveyEntity.getSurveyTagsById() && !surveyEntity.getSurveyTagsById().isEmpty())
        ? convertToDto(surveyEntity.getSurveyTagsById().iterator().next(), locale) : null;

        extractCounts(completionCounts);
    }

    //TODO: refactor code
    private void extractCounts(List<Object[]> completionCounts) {

        BigInteger completedCountBigInt = null;
        BigInteger inProgressCountBigInt = null;

        for (Object[] completionCountNew : completionCounts) {
            completedCountBigInt = ("completed".equals(completionCountNew[1])) ? (BigInteger) completionCountNew[2] : completedCountBigInt;
            inProgressCountBigInt = ("in-progress".equals(completionCountNew[1])) ? (BigInteger) completionCountNew[2] : completedCountBigInt;
        }

        this.completedCount = (null != completedCountBigInt) ? completedCountBigInt.longValue() : null;
        this.inProgressCount = (null != inProgressCountBigInt) ? inProgressCountBigInt.longValue() : null;
    }

    private TagDTO convertToDto(SurveyTagEntity surveyTagEntity, String locale) {
        TagDTO tagDTO = new TagDTO(surveyTagEntity, locale);

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

    public Long getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Long questionCount) {
        this.questionCount = questionCount;
    }

    public Long getInProgressCount() {
        return inProgressCount;
    }

    public void setInProgressCount(Long inProgressCount) {
        this.inProgressCount = inProgressCount;
    }

    public Long getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(Long completedCount) {
        this.completedCount = completedCount;
    }
}
