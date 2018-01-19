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

    @Override
    public List<String> getQuestionTypes() {
        Query query = entityManager.createNativeQuery(" SELECT e.enumlabel FROM pg_enum e JOIN pg_type t on e.enumtypid = t.oid where t.typname = 'question_type'");

        List<String> resultList = query.getResultList();

        return resultList;
    }

    @Override
    public void deleteQuestion(UUID owner, Long id) {
        Query query = entityManager.createQuery(" update QuestionEntity q set q.deleted = true " +
                " where q.id = :id ")
                .setParameter("id", id);

        query.executeUpdate();
    }
}
