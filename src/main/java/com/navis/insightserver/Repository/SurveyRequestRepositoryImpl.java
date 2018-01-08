package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.SurveyEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.UUID;


/**
 * Created by darrell-shofstall on 11/20/17.
 */


public class SurveyRequestRepositoryImpl implements SurveyRequestRepositoryCustom {
    private static final Logger log = LoggerFactory.getLogger(SurveyRequestRepositoryImpl.class);
    @PersistenceContext
    private EntityManager entityManager;

}
