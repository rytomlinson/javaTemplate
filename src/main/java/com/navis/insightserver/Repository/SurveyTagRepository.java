package com.navis.insightserver.Repository;

import com.navis.insightserver.entity.SurveyTagEntity;
import com.navis.insightserver.entity.TagTagEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by darrell-shofstall on 9/29/17.
 */

public interface SurveyTagRepository extends CrudRepository<SurveyTagEntity, Long> {

}
