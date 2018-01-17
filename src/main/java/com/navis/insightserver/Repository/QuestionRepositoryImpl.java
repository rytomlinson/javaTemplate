package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.QuestionEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;


/**
 * Created by darrell-shofstall on 11/20/17.
 */

public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> getQuestionsByOwner(List<UUID> owners) {

//        Query query = entityManager.createQuery("select q from QuestionEntity q");
//                List<QuestionEntity> resultList = query.getResultList();

        Query query = entityManager.createNativeQuery("select q.id, cast(q.owner as varchar), q.display_title_id, q.short_label_id, q.is_library from question q " +
                " where q.owner in(:owners) and q.deleted = false and q.type != 'range-group-member'")
                .setParameter("owners", owners);
        List<Object[]> resultList =  query.getResultList();

        return (List<Object[]>) resultList;
    }
}
