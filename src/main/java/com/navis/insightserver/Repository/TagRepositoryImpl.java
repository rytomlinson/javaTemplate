package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.SurveyEntity;
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

public class TagRepositoryImpl implements TagRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<TagTagEntity> getDepartmentTagsByOwner(UUID owner, Long departmentTagId) {
        Query query = entityManager.createQuery(" select tt from TagTagEntity tt " +
        " where tt.owner = :owner " +
        "and tt.tagByParentTagId.id = :departmentTagId ")
                .setParameter("owner", owner )
                .setParameter("departmentTagId", departmentTagId);
        return query.getResultList();
    }

    @Override
    public TagEntity getDepartmentTag(UUID owner) {
        Query query = entityManager.createQuery("select t from TagEntity t " +
                " join t.i18NStringByNameId.translationsById trn " +
                " where trn.localizedString = 'department' " +
                " and t.owner = :owner " )
                .setParameter("owner", owner);

        return (TagEntity) query.getSingleResult();
    }
}
