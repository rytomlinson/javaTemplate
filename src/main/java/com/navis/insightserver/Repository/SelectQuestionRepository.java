package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.SelectQuestionEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by darrell-shofstall on 9/29/17.
 */

public interface SelectQuestionRepository extends CrudRepository<SelectQuestionEntity, Long>, SelectQuestionRepositoryCustom {

}
