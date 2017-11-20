package com.navis.insightserver.service;

import com.navis.insightserver.Repository.IReportFrequencyTypeRepository;
import com.navis.insightserver.Repository.IReportTypeRepository;
import com.navis.insightserver.Repository.ISurveysRepository;
import com.navis.insightserver.dto.ReportFrequencyTypeDTO;
import com.navis.insightserver.dto.ReportTypeDTO;
import com.navis.insightserver.dto.SurveyAlertDTO;
import com.navis.insightserver.entity.ReportFrequencyTypeEntity;
import com.navis.insightserver.entity.ReportTypeEntity;
import com.navis.insightserver.entity.SurveyEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by darrell-shofstall on 8/9/17.
 */

@Service
public class AlertsService implements IAlertsService {
    private static final Logger log = LoggerFactory.getLogger(AlertsService.class);

    @Autowired
    private IReportTypeRepository reportTypeRepository;

    @Autowired
    private ISurveysRepository surveysRepository;

    @Autowired
    private IReportFrequencyTypeRepository reportFrequencyTypeRepository;


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
        return buildSurveyAlertsDTO(owner, surveyId, reportTypeId, locale);
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
}
