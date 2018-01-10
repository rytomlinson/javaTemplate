package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.SurveyEntity;
import org.javatuples.Triplet;

import java.util.List;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 11/20/17.
 */
public interface SurveyRepositoryCustom {

   SurveyEntity returnSurvey(UUID owner, Long id);
   void deleteSurvey(UUID owner, Long id);
   Long getCurrentSurveyQuestionCount(Long surveyId);
   List<Object[]> getSurveyCompletionCounts(Long surveyId);
}
