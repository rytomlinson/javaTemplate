package com.navis.insightserver.dto;

import com.navis.insightserver.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class SurveyAlertDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

//    @NotNull(message = "user.settings.stat.event.type.notnull")
    private Long surveyId;
    private String surveyName;
    private Long reportTypeId;
    private String reportTypeName;
    private UUID propertyId;
    private List<EmailDTO> recipients;

    public SurveyAlertDTO() {
    }

    public SurveyAlertDTO(UUID owner, SurveyEntity surveyEntity, ReportTypeEntity reportTypeEntity, String locale) {
        super();
        List<TranslationEntity> displayTitleEntities;
        TranslationEntity displayTitleEntity;
        List<SurveyReportRecipientsEntity> emailEntities;
        List<SurveyReportRecipientsEntity> reportTypeEmailEntities;

        this.surveyId = surveyEntity.getId();
        displayTitleEntities = new ArrayList(surveyEntity.getI18NStringByDisplayTitleId().getTranslationsById());
        displayTitleEntity = displayTitleEntities.stream().filter(e -> e.getLocale().equals(locale)).findFirst().orElse(null);

        this.reportTypeId = reportTypeEntity.getId();
        this.reportTypeName = reportTypeEntity.getDescription();

        this.propertyId = owner;
        emailEntities = new ArrayList(surveyEntity.getSurveyReportRecipientssById());

        reportTypeEmailEntities = emailEntities.stream().filter(e -> e.getReportTypeByReportTypeId().getId() == this.reportTypeId).collect(Collectors.toList());

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

    public Long getReportTypeId() {
        return reportTypeId;
    }

    public String getReportTypeName() {
        return reportTypeName;
    }

    public UUID getPropertyId() {
        return propertyId;
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

    public void setReportTypeId(Long reportTypeId) {
        this.reportTypeId = reportTypeId;
    }

    public void setReportTypeName(String reportTypeName) {
        this.reportTypeName = reportTypeName;
    }

    public void setPropertyId(UUID propertyId) {
        this.propertyId = propertyId;
    }
}
