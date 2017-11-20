package com.navis.insightserver.dto;

import com.navis.insightserver.entity.EmailEntity;
import com.navis.insightserver.entity.SurveyEntity;
import com.navis.insightserver.entity.SurveyReportRecipientsEntity;
import com.navis.insightserver.entity.TranslationEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class SurveyAlertDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

//    @NotNull(message = "user.settings.stat.event.type.notnull")
    private Long surveyId;
    private String surveyName;
    private List<EmailDTO> recipients;

    public SurveyAlertDTO() {
    }

    public SurveyAlertDTO(SurveyEntity surveyEntity, Long reportTypeId, String locale) {
        super();
        List<TranslationEntity> displayTitleEntities;
        TranslationEntity displayTitleEntity;
        List<SurveyReportRecipientsEntity> emailEntities;
        List<SurveyReportRecipientsEntity> reportTypeEmailEntities;

        this.surveyId = surveyEntity.getId();
        displayTitleEntities = new ArrayList(surveyEntity.getI18NStringByDisplayTitleId().getTranslationsById());
        displayTitleEntity = displayTitleEntities.stream().filter(e -> e.getLocale().equals(locale)).findFirst().orElse(null);

        emailEntities = new ArrayList(surveyEntity.getSurveyReportRecipientssById());

        reportTypeEmailEntities = emailEntities.stream().filter(e -> e.getReportTypeByReportTypeId().getId() == reportTypeId).collect(Collectors.toList());

        this.surveyName = (null != displayTitleEntity) ? displayTitleEntity.getLocalizedString() : null;

        List<EmailDTO> listDto = reportTypeEmailEntities.stream().map(item -> convertToDto(item)).collect(Collectors.toList());
        this.recipients = listDto;
    }

    private EmailDTO convertToDto(SurveyReportRecipientsEntity surveyReportRecipientsEntity) {
        EmailDTO emailDTO = new EmailDTO(surveyReportRecipientsEntity);
        return emailDTO;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public List<EmailDTO> getRecipients() {
        return recipients;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public void setRecipients(List<EmailDTO> recipients) {
        this.recipients = recipients;
    }
}
