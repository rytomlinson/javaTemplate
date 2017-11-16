package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.SurveyEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 9/29/17.
 */

public interface ISurveysRepository extends CrudRepository<SurveyEntity, Long> {

    List<SurveyEntity> findByOwner(UUID owner);
    List<SurveyEntity> findByOwnerAndDeletedFalse(UUID owner);
}
