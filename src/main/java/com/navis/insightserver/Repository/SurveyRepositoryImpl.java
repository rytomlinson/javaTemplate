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
}
