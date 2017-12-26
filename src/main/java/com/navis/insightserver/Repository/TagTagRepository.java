package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.TagEntity;
import com.navis.insightserver.entity.TagTagEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 9/29/17.
 */

public interface TagTagRepository extends CrudRepository<TagTagEntity, Long> {

    List<TagTagEntity> findByOwner(UUID propertyId);
}
