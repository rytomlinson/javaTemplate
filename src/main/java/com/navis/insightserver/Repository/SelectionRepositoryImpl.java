package com.navis.insightserver.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * Created by darrell-shofstall on 11/20/17.
 */

public class SelectionRepositoryImpl implements SelectionRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;



}
