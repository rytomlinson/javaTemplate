package com.navis.insightserver.service;

import com.navis.insightserver.Repository.*;
import com.navis.insightserver.dto.*;
import com.navis.insightserver.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by darrell-shofstall on 8/9/17.
 */
@Transactional
@Service
public class AlertsService implements IAlertsService {
    private static final Logger log = LoggerFactory.getLogger(AlertsService.class);

    @Autowired
    private IReportTypeRepository reportTypeRepository;

    @Autowired
    private ISurveysRepository surveysRepository;

    @Autowired
    private IReportFrequencyTypeRepository reportFrequencyTypeRepository;

    @Autowired
    private SurveyReportRecipientsRepository SurveyReportRecipientsRepository;

    @Override
    public List<ReportTypeDTO> getReportTypes() {
        log.debug("In getReportTypes Service:");
        return buildReportTypesDTO();
    }

    @Override
    public List<ReportFrequencyTypeDTO> getReportFrequencyTypes() {
        log.debug("In getReportFrequencyTypes Service:");
        return buildReportFrequencyTypesDTO();
    }

    @Override
    public SurveyAlertDTO getSurveyAlerts(UUID owner, Long surveyId, Long reportTypeId, String locale) {
        log.debug("In getSurveyAlerts Service:");
        return buildSurveyAlertsDTO(owner, surveyId, reportTypeId, locale);
    }

    @Override
    public void addSurveyReportRecipients(SurveyAlertDTO surveyAlertDTO) {
        log.debug("In addSurveyReportRecipients Service:");
        List<EmailDTO> emailDTOList = surveyAlertDTO.getRecipients();

        //Validate current SurveyAlertDTO payload
        validateSurveyAlertsPayload(surveyAlertDTO);

        // Delete existing SurveyReportRecipientsEntity rows
        deleteSurveyReportRecipients(surveyAlertDTO.getSurveyId(), surveyAlertDTO.getReportTypeId());

        // Save SurveyReportRecipientsEntity rows
        for(EmailDTO emailDTO : emailDTOList) {
            SurveyReportRecipientsEntity surveyReportRecipientsEntity
                    = convertToEntity(surveyAlertDTO.getSurveyId(), surveyAlertDTO.getReportTypeId(), emailDTO);
            SurveyReportRecipientsRepository.save(surveyReportRecipientsEntity);
        }
    }

    @Override
    public Long deleteSurveyReportRecipients(Long surveyId, Long reportTypeId) {
        log.debug("In deleteSurveyReportRecipientsBySurveyBySurveyId Service:");
        return SurveyReportRecipientsRepository.deleteSurveyReportRecipients(surveyId, reportTypeId);
    }

    private List<ReportTypeDTO> buildReportTypesDTO() {
        List<ReportTypeEntity> list = reportTypeRepository.findAll();

        List<ReportTypeDTO> listDto = list.stream().map(item -> convertToDto(item)).collect(Collectors.toList());

        return listDto;
    }

    private List<ReportFrequencyTypeDTO> buildReportFrequencyTypesDTO() {
        List<ReportFrequencyTypeEntity> list = reportFrequencyTypeRepository.findAll();

        List<ReportFrequencyTypeDTO> listDto = list.stream().map(item -> convertToDto(item)).collect(Collectors.toList());

        return listDto;
    }

    private SurveyAlertDTO buildSurveyAlertsDTO(UUID owner, Long surveyId, Long reportTypeId, String locale) {
        SurveyEntity surveyEntity = surveysRepository.findByOwnerAndId(owner, surveyId);
        ReportTypeEntity reportTypeEntity = reportTypeRepository.findById(reportTypeId);

        return convertToDto(owner, surveyEntity, reportTypeEntity, locale);
    }

    private ReportTypeDTO convertToDto(ReportTypeEntity reportTypeEntity) {
        ReportTypeDTO reportTypeDTO = new ReportTypeDTO(reportTypeEntity);
        return reportTypeDTO;
    }

    private ReportFrequencyTypeDTO convertToDto(ReportFrequencyTypeEntity reportFrequencyTypeEntity) {
        ReportFrequencyTypeDTO reportFrequencyTypeDTO = new ReportFrequencyTypeDTO(reportFrequencyTypeEntity);
        return reportFrequencyTypeDTO;
    }

    private SurveyAlertDTO convertToDto(UUID owner, SurveyEntity surveyEntity, ReportTypeEntity reportTypeEntity,String locale) {
        SurveyAlertDTO surveyAlertDTO = new SurveyAlertDTO(owner, surveyEntity, reportTypeEntity, locale);
        return surveyAlertDTO;
    }

    private SurveyReportRecipientsEntity convertToEntity(Long surveyId, Long reportTypeId, EmailDTO emailDTO) {
        Date now = new Date();
        SurveyReportRecipientsEntity surveyReportRecipientsEntity = new SurveyReportRecipientsEntity();
        surveyReportRecipientsEntity.setSurveyBySurveyId(surveysRepository.findOne(surveyId));
        surveyReportRecipientsEntity.setReportTypeByReportTypeId(reportTypeRepository.findOne(reportTypeId));
        surveyReportRecipientsEntity.setEmail(emailDTO.getEmail());
        surveyReportRecipientsEntity.setCreatedAt(now);
        surveyReportRecipientsEntity.setUpdatedAt(now);
        return surveyReportRecipientsEntity;
    }

    private void validateSurveyAlertsPayload(SurveyAlertDTO surveyAlertDTO) {

        if (!reportTypeRepository.exists(surveyAlertDTO.getReportTypeId())) {

            throw new ResourceNotFoundExceptionDTO(surveyAlertDTO.getReportTypeId().toString(), "survey.alert.report.type.id.invalid");
        }

        if (!reportTypeRepository.exists(surveyAlertDTO.getSurveyId())) {

            throw new ResourceNotFoundExceptionDTO(surveyAlertDTO.getSurveyId().toString(), "survey.alert.survey.id.invalid");
        }
    }
}
