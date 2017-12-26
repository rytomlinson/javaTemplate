package com.navis.insightserver.service;

import com.navis.insightserver.Repository.SurveyRepository;
import com.navis.insightserver.dto.SurveyDTO;
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
public class SurveysService implements ISurveysService {
    private static final Logger log = LoggerFactory.getLogger(SurveysService.class);

    @Autowired
    private SurveyRepository surveyRepository;

    @Override
    public List<SurveyDTO> getSurveys(UUID owner, String locale, Boolean includeDeleted) {
        log.debug("In getSurveys Service:");
        return buildSurveysDTO(owner, locale, includeDeleted);
    }

    @Override
    public SurveyDTO getSurveyById(UUID owner, String locale, Long surveyId) {
        log.debug("In getSurveyById Service:");
        return buildSurveyDTO(owner, locale, surveyId);
    }

    private List<SurveyDTO> buildSurveysDTO(UUID owner, String locale, Boolean includeDeleted) {
        List<SurveyEntity> list = includeDeleted ? surveyRepository.findByOwner(owner) : surveyRepository.findByOwnerAndDeletedFalse(owner);

        List<SurveyDTO> listDto = list.stream().map(item -> convertToDto(item, locale)).collect(Collectors.toList());

        return listDto;
    }

    private SurveyDTO buildSurveyDTO(UUID owner, String locale, Long surveyId) {
        SurveyEntity surveyEntity = surveyRepository.findByOwnerAndId(owner, surveyId);

        SurveyDTO surveyDTO = convertToDto(surveyEntity, locale);

        return surveyDTO;
    }
    private SurveyDTO convertToDto(SurveyEntity surveyEntity, String locale) {
        SurveyDTO surveyDTO = new SurveyDTO(surveyEntity, locale);
        return surveyDTO;
    }
}
