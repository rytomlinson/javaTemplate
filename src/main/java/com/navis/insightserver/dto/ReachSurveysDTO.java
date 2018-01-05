package com.navis.insightserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class ReachSurveysDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private List<ReachSurveyDTO> surveyList = new ArrayList<>();

    public ReachSurveysDTO() {
    }

    @JsonProperty(value = "survey-list")
    public List<ReachSurveyDTO> getSurveyList() {
        return surveyList;
    }

    public void setSurveyList(List<ReachSurveyDTO> surveyList) {
        this.surveyList = surveyList;
    }
}
