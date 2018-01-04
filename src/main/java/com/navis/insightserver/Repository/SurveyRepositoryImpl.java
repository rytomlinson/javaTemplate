package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.SurveyEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.UUID;


/**
 * Created by darrell-shofstall on 11/20/17.
 */


public class SurveyRepositoryImpl implements SurveyRepositoryCustom {
    private static final Logger log = LoggerFactory.getLogger(SurveyRepositoryImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SurveyEntity returnSurvey(UUID owner, Long id) {


        Query query = entityManager.createQuery("select s from SurveyEntity s " +
        " where s.id = :id ")
                .setParameter("id", id);

         Object testing = query.getSingleResult();

         return null;

    }

    @Override
    public void deleteSurvey(UUID owner, Long id) {
      Query query = entityManager.createQuery(" update SurveyEntity s set s.deleted = true " +
       " where s.id = :id ")
              .setParameter("id", id);

      query.executeUpdate();
    }

    @Override
    public Long getCurrentSurveyQuestionCount(Long id) {
        Query query = entityManager.createQuery(" select count(sq.id) from SurveyQuestionEntity sq " +
                " where sq.surveyBySurveyId.id = :id " )
                .setParameter("id", id);

        return (Long) query.getSingleResult();
    }
}
