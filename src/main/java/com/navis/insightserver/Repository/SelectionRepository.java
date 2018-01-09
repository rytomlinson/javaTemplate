package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.SelectionEntity;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by darrell-shofstall on 9/29/17.
 */

public interface SelectionRepository extends CrudRepository<SelectionEntity, Long>, SelectionRepositoryCustom {

    SelectionEntity findBySelectionListBySelectionListId_IdAndId(Long selectionListId, Long itemId);
}
