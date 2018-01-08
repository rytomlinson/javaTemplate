package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.SurveyRequestEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by darrell-shofstall on 9/29/17.
 */

public interface SurveyRequestRepository extends CrudRepository<SurveyRequestEntity, Long> , SurveyRequestRepositoryCustom {

    SurveyRequestEntity findBySurveyBySurveyId_IdAndCrmStayIdAndAccountIdAndEmail(Long surveyId, Long stayId, String accountId, String email);
}
