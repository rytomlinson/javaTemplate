package com.navis.insightserver.dto;

import com.navis.insightserver.entity.SurveyReportRecipientsEntity;
import com.navis.insightserver.entity.TagEntity;

import javax.validation.constraints.NotNull;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class EmailDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "survey.alert.email.notnull")
    private String email;

    public EmailDTO() {
    }

    public EmailDTO(String email) {
        this.email = email;
    }

    public EmailDTO(SurveyReportRecipientsEntity surveyReportRecipientsEntity) {
        super();
        this.email = surveyReportRecipientsEntity.getEmail();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
