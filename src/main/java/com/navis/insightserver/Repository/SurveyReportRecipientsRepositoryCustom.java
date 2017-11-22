package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.SurveyReportRecipientsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by darrell-shofstall on 11/20/17.
 */
public interface SurveyReportRecipientsRepositoryCustom {

    Long deleteSurveyReportRecipients(Long surveyId, Long reportTypeId);
}
