package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.SurveyEntity;

import java.util.UUID;

/**
 * Created by darrell-shofstall on 11/20/17.
 */
public interface SurveyRepositoryCustom {

   SurveyEntity returnSurvey(UUID owner, Long id);
}
