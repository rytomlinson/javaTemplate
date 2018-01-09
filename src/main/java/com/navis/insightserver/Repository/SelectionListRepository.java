package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.SelectionListEntity;
import com.navis.insightserver.entity.TagEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 9/29/17.
 */

public interface SelectionListRepository extends CrudRepository<SelectionListEntity, Long>, SelectionListRepositoryCustom {

    List<SelectionListEntity> findByOwnerInAndDeletedFalse(List<UUID> owners);
    SelectionListEntity findByOwnerAndId(UUID owner, Long id);
}
