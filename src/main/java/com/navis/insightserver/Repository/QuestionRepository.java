package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.QuestionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 9/29/17.
 */

public interface QuestionRepository extends CrudRepository<QuestionEntity, Long>, QuestionRepositoryCustom {
   List<QuestionEntity> findByOwnerInAndDeletedFalseAndTemplateTrueAndQuestionBySourceId_IdIsNull(List<UUID> owners);
}
