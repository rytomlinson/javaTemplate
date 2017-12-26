package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.TagEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 9/29/17.
 */

public interface TagRepository extends CrudRepository<TagEntity, Long>, TagRepositoryCustom {

    List<TagEntity> findByOwner(UUID propertyId);
    List<TagEntity> findByOwnerAndSurveyTypeTrue(UUID owner);
}
