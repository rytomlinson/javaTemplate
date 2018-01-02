package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.SurveyEntity;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 9/29/17.
 */

public interface SurveyRepository extends CrudRepository<SurveyEntity, Long> , SurveyRepositoryCustom {

    List<SurveyEntity> findByOwner(UUID owner);
    List<SurveyEntity> findByOwnerAndDeletedFalse(UUID owner);
    SurveyEntity findByOwnerAndId(UUID owner, Long id);
}
