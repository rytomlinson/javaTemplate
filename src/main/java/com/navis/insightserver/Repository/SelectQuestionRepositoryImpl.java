package com.navis.insightserver.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * Created by darrell-shofstall on 11/20/17.
 */

public class SelectQuestionRepositoryImpl implements SelectQuestionRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;



}
