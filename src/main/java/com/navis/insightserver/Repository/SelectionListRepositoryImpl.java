package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.TagEntity;
import com.navis.insightserver.entity.TagTagEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;


/**
 * Created by darrell-shofstall on 11/20/17.
 */

public class SelectionListRepositoryImpl implements SelectionListRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;



}
