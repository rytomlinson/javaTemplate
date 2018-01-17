package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.QuestionEntity;
import com.navis.insightserver.entity.TagEntity;
import com.navis.insightserver.entity.TagTagEntity;

import java.util.List;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 11/20/17.
 */
public interface QuestionRepositoryCustom {

    List<Object[]> getQuestionsByOwner(List<UUID> owners);
}
