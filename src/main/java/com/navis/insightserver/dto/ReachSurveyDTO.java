package com.navis.insightserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.navis.insightserver.entity.SurveyEntity;
import com.navis.insightserver.entity.TranslationEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class ReachSurveyDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String displayTitle;

    public ReachSurveyDTO() {
    }

    public ReachSurveyDTO(SurveyEntity surveyEntity, String locale) {
        super();

        this.id = surveyEntity.getId();
        List<TranslationEntity> displayTitleEntities = (List<TranslationEntity>) surveyEntity.getI18NStringByDisplayTitleId().getTranslationsById();
        this.displayTitle = super.returnTranslationForLocale(displayTitleEntities, locale);

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty(value = "localized_string")
    public String getDisplayTitle() {
        return displayTitle;
    }

    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
    }
}
