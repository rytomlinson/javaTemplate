package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.SurveyReportRecipientsEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by darrell-shofstall on 11/20/17.
 */
public interface ISurveyReportRecipientsRepository extends CrudRepository<SurveyReportRecipientsEntity, Long> {

    SurveyReportRecipientsEntity save(SurveyReportRecipientsEntity entity);
//    Long deleteByBlaBlaBla();
}
