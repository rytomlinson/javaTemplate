package com.navis.insightserver.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 * Created by darrell-shofstall on 11/20/17.
 */

public class SurveyReportRecipientsRepositoryImpl implements SurveyReportRecipientsRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long deleteSurveyReportRecipients(Long surveyId, Long reportTypeId) {

        Query query = entityManager.createQuery("delete from SurveyReportRecipientsEntity re " +
                "where re.surveyBySurveyId.id = :sid and re.reportTypeByReportTypeId.id = :rid");
        int deletedCount = query.setParameter("sid", surveyId).setParameter("rid", reportTypeId).executeUpdate();

        return Long.valueOf(deletedCount);
    }
}
