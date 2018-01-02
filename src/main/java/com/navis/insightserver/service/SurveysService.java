package com.navis.insightserver.service;

import com.navis.insightserver.Repository.*;
import com.navis.insightserver.dto.SurveyDTO;
import com.navis.insightserver.entity.SurveyEntity;
import com.navis.insightserver.entity.SurveyTagEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by darrell-shofstall on 8/9/17.
 */
@Transactional
@Service
public class SurveysService implements ISurveysService {
    private static final Logger log = LoggerFactory.getLogger(SurveysService.class);

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private I18nStringRepository i18nStringRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private SurveyTagRepository surveyTagRepository;

    @Autowired
    private TranslationRepository translationRepository;

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

    @Override
    public Long upsertSurvey(UUID owner, SurveyDTO surveyDTO, String locale) {
        log.debug("In upsertSurvey Service:");
        Date now = new Date();
        Long surveyId = surveyDTO.getId();
        Long displayTitleId;
        SurveyEntity surveyEntity;
        Long surveyTypeId = surveyDTO.getSurveyType().getId();

        if(null != surveyId) {
            surveyEntity = surveyRepository.findOne(surveyId);
        } else {
            displayTitleId = translationRepository.createTranslation(surveyDTO.getDisplayTitle());
            surveyEntity = convertToEntity(owner, surveyDTO, locale);
            surveyEntity.setI18NStringByDisplayTitleId(i18nStringRepository.findOne(displayTitleId));
        }

        surveyEntity = surveyRepository.save(surveyEntity);

        //TODO: refactor code
        SurveyTagEntity surveyTagEntity = new SurveyTagEntity();
        surveyTagEntity.setSurveyBySurveyId(surveyEntity);
        surveyTagEntity.setTagByTagId(tagRepository.findOne(surveyTypeId));
        surveyTagEntity.setCreatedAt(now);
        surveyTagEntity.setUpdatedAt(now);

        surveyTagRepository.save(surveyTagEntity);

        return surveyEntity.getId();
    }

    private SurveyEntity convertToEntity(UUID owner, SurveyDTO surveyDTO, String locale) {
     Date now = new Date();
      SurveyEntity surveyEntity = new SurveyEntity();
      surveyEntity.setOwner(owner);
      surveyEntity.setCreatedAt(now);
      surveyEntity.setUpdatedAt(now);
      surveyEntity.setDeleted(false);

      return surveyEntity;
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
