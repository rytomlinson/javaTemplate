package com.navis.insightserver.service;

import com.navis.insightserver.Repository.*;
import com.navis.insightserver.dto.ReachSurveyDTO;
import com.navis.insightserver.dto.ReachSurveysDTO;
import com.navis.insightserver.dto.ResourceNotFoundExceptionDTO;
import com.navis.insightserver.dto.SurveyDTO;
import com.navis.insightserver.entity.SurveyEntity;
import com.navis.insightserver.entity.SurveyRequestEntity;
import com.navis.insightserver.entity.SurveyTagEntity;
import com.navis.insightserver.entity.TagEntity;
import org.javatuples.Triplet;
import org.javatuples.Unit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by darrell-shofstall on 8/9/17.
 */
@Transactional
@Service
public class SurveysService implements ISurveysService {
    private static final Logger log = LoggerFactory.getLogger(SurveysService.class);

    @Value("${survey.taker.url}")
    private String surveyTakerUrl;

    @Value("${navis.properties.api.propertiesForAcctnbrEndpoint}")
    private String propertiesForAcctnbrEndpoint;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyRequestRepository surveyRequestRepository;

    @Autowired
    private SurveyRequestCompletionStatusTypesRepository surveyRequestCompletionStatusTypesRepository;

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
    public ReachSurveysDTO getReachSurveys(String accountId, String locale, Boolean includeDeleted) {
        log.debug("In getReachSurveys Service:");
        URI uri = URI.create(UriComponentsBuilder.fromUriString(propertiesForAcctnbrEndpoint).queryParam("acctnbr", accountId).toUriString());

        RestTemplate restTemplate = new RestTemplate();
        Object[] accountObject = restTemplate.getForObject(uri.toString(), Object[].class);
        LinkedHashMap entry = (LinkedHashMap) Arrays.asList(accountObject).get(0);
        String propertyOwner = (String) entry.get("property_uuid");
        UUID owner = UUID.fromString(propertyOwner);

        return buildReachSurveysDTO(owner, locale, includeDeleted);
    }

    @Override
    public SurveyDTO getSurveyById(UUID owner, String locale, Long surveyId) {
        log.debug("In getSurveyById Service:");
        return buildSurveyDTO(owner, locale, surveyId);
    }

    @Override
    public void deleteSurvey(UUID owner, Long id) {
        log.debug("In deleteSurveyById Service:");
        surveyRepository.deleteSurvey(owner, id);
    }

    @Override
    public void updateSurveyPublishStatus(UUID owner, Long id, Boolean status) {
        log.debug("In updateSurveyPublishStatus Service:");
        SurveyEntity surveyEntity = validateSurvey(id);

        validateSurveyQuestionCount(id);

        surveyEntity.setEnabled(status);
        surveyRepository.save(surveyEntity);
    }

    @Override
    public Long upsertSurvey(UUID owner, SurveyDTO surveyDTO, String locale) {
        log.debug("In upsertSurvey Service:");
        Date now = new Date();
        Long surveyId = surveyDTO.getId();
        Long displayTitleId;
        SurveyEntity surveyEntity;
        Long surveyTypeId = surveyDTO.getSurveyType().getId();

        if (null != surveyId) {
            surveyEntity = validateSurvey(surveyId);
        } else {
            displayTitleId = translationRepository.createTranslation(surveyDTO.getDisplayTitle());
            surveyEntity = convertToEntity(owner, surveyDTO, locale);
            surveyEntity.setI18NStringByDisplayTitleId(i18nStringRepository.findOne(displayTitleId));
        }

        surveyEntity = surveyRepository.save(surveyEntity);

        //TODO: refactor code
        SurveyTagEntity surveyTagEntity = new SurveyTagEntity();
        surveyTagEntity.setSurveyBySurveyId(surveyEntity);
        surveyTagEntity.setTagByTagId(validateSurveyType(surveyTypeId));
        surveyTagEntity.setCreatedAt(now);
        surveyTagEntity.setUpdatedAt(now);

        surveyTagRepository.save(surveyTagEntity);

        return surveyEntity.getId();
    }

    @Override
    public Unit<String> generateAnonymousSurveyLink(UUID owner, Long surveyId, String source, String surveyMode) {

        String responseKey = securityService.generateSurveyResponseKey(surveyId, 0L, source, surveyMode);
        StringBuilder builder = new StringBuilder();
        builder.append(surveyTakerUrl)
                .append("?id=")
                .append(responseKey);

        Unit<String> unit = new Unit<String>(builder.toString());
        return unit;
    }

    @Override
    public String generateReachSurveyLink(Long surveyId, String email, Long stayId, String accountId, String surveyMode) {
        log.debug("In generateReachSurveyLink Service:");
        URI uri = URI.create(UriComponentsBuilder.fromUriString(propertiesForAcctnbrEndpoint).queryParam("acctnbr", accountId).toUriString());

        RestTemplate restTemplate = new RestTemplate();
        Object[] accountObject = restTemplate.getForObject(uri.toString(), Object[].class);
        LinkedHashMap entry = (LinkedHashMap) Arrays.asList(accountObject).get(0);
        String propertyOwner = (String) entry.get("property_uuid");
        UUID owner = UUID.fromString(propertyOwner);

        surveyMode = (null != surveyMode) ? surveyMode : SecurityService.surveyModeNormal;

        SurveyEntity surveyEntity = validateSurvey(surveyId);
        SurveyRequestEntity surveyRequestEntity;

        surveyRequestEntity = surveyRequestRepository
                .findBySurveyBySurveyId_IdAndCrmStayIdAndAccountIdAndEmail(surveyId, stayId, accountId, email);

        surveyRequestEntity = (null != surveyRequestEntity) ? surveyRequestEntity : createSurveyRequest(surveyId, stayId, accountId, email);

        String responseKey = securityService.generateSurveyResponseKey(surveyId, surveyRequestEntity.getId(), email, surveyMode);
        StringBuilder builder = new StringBuilder();
        builder.append(surveyTakerUrl)
                .append("?id=")
                .append(responseKey);

        return builder.toString();
    }

    private SurveyRequestEntity createSurveyRequest(Long surveyId, Long stayId, String accountId, String email) {

        Date now = new Date();
        SurveyRequestEntity surveyRequestEntity = new SurveyRequestEntity();
        surveyRequestEntity.setCreatedAt(now);
        surveyRequestEntity.setUpdatedAt(now);
        surveyRequestEntity.setSurveyBySurveyId(validateSurvey(surveyId));
        surveyRequestEntity.setCrmStayId(stayId);
        surveyRequestEntity.setAccountId(accountId);
        surveyRequestEntity.setEmail(email);
        surveyRequestEntity.setSurveyRequestCompletionStatusTypesByCompletionStatusType(surveyRequestCompletionStatusTypesRepository.findByCode("IN_PROCESS"));

        return surveyRequestRepository.save(surveyRequestEntity);
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

    private ReachSurveysDTO buildReachSurveysDTO(UUID owner, String locale, Boolean includeDeleted) {
        List<SurveyEntity> list = includeDeleted ? surveyRepository.findByOwner(owner) : surveyRepository.findByOwnerAndDeletedFalse(owner);

        List<ReachSurveyDTO> listDto = list.stream().map(item -> convertToReachSurveyDto(item, locale)).collect(Collectors.toList());

        ReachSurveysDTO reachSurveysDTO = new ReachSurveysDTO();
        reachSurveysDTO.setSurveyList(listDto);
        return reachSurveysDTO;
    }

    private SurveyDTO buildSurveyDTO(UUID owner, String locale, Long surveyId) {
        SurveyEntity surveyEntity = surveyRepository.findByOwnerAndId(owner, surveyId);

        SurveyDTO surveyDTO = convertToDto(surveyEntity, locale);

        return surveyDTO;
    }

    private SurveyDTO convertToDto(SurveyEntity surveyEntity, String locale) {
        Long questionCount = surveyRepository.getCurrentSurveyQuestionCount(surveyEntity.getId());

        List<Object[]> surveyCompletionCounts = surveyRepository.getSurveyCompletionCounts(surveyEntity.getId());

        SurveyDTO surveyDTO = new SurveyDTO(surveyEntity, questionCount, surveyCompletionCounts, locale);
        return surveyDTO;
    }

    private ReachSurveyDTO convertToReachSurveyDto(SurveyEntity surveyEntity, String locale) {
        ReachSurveyDTO surveyDTO = new ReachSurveyDTO(surveyEntity, locale);
        return surveyDTO;
    }

    private SurveyEntity validateSurvey(Long id) {

        if (!surveyRepository.exists(id)) {

            throw new ResourceNotFoundExceptionDTO(id.toString(), "survey.id.invalid");
        } else {
            return surveyRepository.findOne(id);
        }
    }

    private void validateSurveyQuestionCount(Long id) {

        if (0 == surveyRepository.getCurrentSurveyQuestionCount(id)) {

            throw new ResourceNotFoundExceptionDTO("", "survey.question.count.invalid");
        }
    }

    private TagEntity validateSurveyType(Long id) {

        TagEntity tagEntity = tagRepository.findOne(id);
        if (null == tagEntity) {
            throw new ResourceNotFoundExceptionDTO(id.toString(), "survey.type.id.invalid");
        }
        return tagEntity;
    }
}
