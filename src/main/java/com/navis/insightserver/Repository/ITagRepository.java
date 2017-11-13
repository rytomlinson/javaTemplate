package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.TagEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by darrell-shofstall on 9/29/17.
 */

public interface ITagRepository extends CrudRepository<TagEntity, Long> {

    List<TagEntity> findAll();
}
