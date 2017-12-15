package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.SurveyReportRecipientsEntity;
import com.navis.insightserver.entity.SurveyReportSchedulerEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by darrell-shofstall on 11/20/17.
 */
public interface SurveyReportSchedulerRepository
        extends CrudRepository<SurveyReportSchedulerEntity, Long> {

    SurveyReportSchedulerEntity findBySurveyBySurveyId_IdAndReportTypeByReportTypeId_Id(Long surveyId, Long ReportType);
}
