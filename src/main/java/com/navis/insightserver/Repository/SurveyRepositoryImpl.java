package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.SurveyEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.UUID;


/**
 * Created by darrell-shofstall on 11/20/17.
 */

public class SurveyRepositoryImpl implements SurveyRepositoryCustom {

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
}
