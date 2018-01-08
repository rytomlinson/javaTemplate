package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.SurveyRequestCompletionStatusTypesEntity;
import com.navis.insightserver.entity.SurveyRequestEntity;
import org.springframework.data.repository.Repository;

/**
 * Created by darrell-shofstall on 9/29/17.
 */

public interface SurveyRequestCompletionStatusTypesRepository extends Repository<SurveyRequestCompletionStatusTypesEntity, Integer> {

    SurveyRequestCompletionStatusTypesEntity findByCode(String code);
}
