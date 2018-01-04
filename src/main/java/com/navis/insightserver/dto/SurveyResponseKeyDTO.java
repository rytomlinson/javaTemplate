package com.navis.insightserver.dto;


/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class SurveyResponseKeyDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private Long surveyId;
    private Long requestId;
    private String source;
    private String surveyMode;

    public SurveyResponseKeyDTO() {
    }

    public SurveyResponseKeyDTO(Long surveyId, Long requestId, String source, String surveyMode) {
        this.surveyId = surveyId;
        this.requestId = requestId;
        this.source = source;
        this.surveyMode = surveyMode;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSurveyMode() {
        return surveyMode;
    }

    public void setSurveyMode(String surveyMode) {
        this.surveyMode = surveyMode;
    }
}
