package com.navis.insightserver.dto;

import com.navis.insightserver.entity.SurveyReportRecipientsEntity;
import com.navis.insightserver.entity.TagEntity;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class EmailDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

//    @NotNull(message = "user.settings.stat.event.type.notnull")
    private Long id;
    private String email;

    public EmailDTO() {
    }

    public EmailDTO(String email) {
        this.email = email;
    }

    public EmailDTO(SurveyReportRecipientsEntity surveyReportRecipientsEntity) {
        super();
        this.id = surveyReportRecipientsEntity.getId();
        this.email = surveyReportRecipientsEntity.getEmail();
    }

    public Long getId()
    {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
