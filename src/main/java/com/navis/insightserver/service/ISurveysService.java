package com.navis.insightserver.service;

import com.navis.insightserver.dto.ReachSurveysDTO;
import com.navis.insightserver.dto.SurveyDTO;

import java.util.List;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 8/9/17.
 */
public interface ISurveysService {

    List<SurveyDTO> getSurveys(UUID owner, String locale, Boolean includeDeleted);

    ReachSurveysDTO getReachSurveys(String accountId, String locale, Boolean includeDeleted);

    SurveyDTO getSurveyById(UUID owner, String locale, Long surveyId);

    Long upsertSurvey(UUID propertyId, SurveyDTO surveyDTO, String locale);

    void deleteSurvey(UUID owner, Long id);

    void updateSurveyPublishStatus(UUID owner, Long id, Boolean status);

    String generateAnonymousSurveyLink(UUID owner, Long surveyId, String source, String surveyMode);
}
