package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.SurveyEntity;
import com.navis.insightserver.entity.TranslationEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 9/29/17.
 */

public interface TranslationRepository extends CrudRepository<TranslationEntity, Long> , TranslationRepositoryCustom {
}
